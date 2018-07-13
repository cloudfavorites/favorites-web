package com.favorites.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favorites.domain.Collect;
import com.favorites.domain.Comment;
import com.favorites.domain.User;
import com.favorites.domain.result.Response;
import com.favorites.repository.CollectRepository;
import com.favorites.repository.CommentRepository;
import com.favorites.repository.UserRepository;
import com.favorites.service.NoticeService;
import com.favorites.utils.DateUtils;
import com.favorites.utils.StringUtil;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController{
	
	@Autowired
	private  CommentRepository CommentRepository;
	@Autowired
	private UserRepository userRepository;
	@Resource
	private NoticeService noticeService;
	@Autowired
	private CollectRepository colloectRepository;
	
	
	/**
	 * @author neo
	 * @date 2016年8月26日
	 * @param comment
	 * @return
	 */
	@RequestMapping(value="/add")
	public Response add(Comment comment) {
		User user = null;
		if (comment.getContent().indexOf("@") > -1) {
			List<String> atUsers = StringUtil.getAtUser(comment.getContent());
			if(atUsers!=null && atUsers.size()>0){
				user = userRepository.findByUserName(atUsers.get(0));
				if (null != user) {
					comment.setReplyUserId(user.getId());
				} else {
					logger.info("为找到匹配：" + atUsers.get(0) + "的用户.");
				}
				String content=comment.getContent().substring(0,comment.getContent().indexOf("@"));
				if(StringUtils.isBlank(content)){
					content=comment.getContent().substring(comment.getContent().indexOf("@")+user.getUserName().length()+1,comment.getContent().length());
				}
				comment.setContent(content);
			}
		}
		comment.setUserId(getUserId());
		comment.setCreateTime(DateUtils.getCurrentTime());
		CommentRepository.save(comment);
		if(null != user){
			// 保存消息通知(回复)
			noticeService.saveNotice(String.valueOf(comment.getCollectId()), "comment", user.getId(), String.valueOf(comment.getId()));
		}else{
			// 保存消息通知（直接评论）
			Collect collect = colloectRepository.findById((long)comment.getCollectId());
			if(null != collect){
				noticeService.saveNotice(String.valueOf(comment.getCollectId()), "comment", collect.getUserId(), String.valueOf(comment.getId()));
			}
		}
		
		return result();
	}
	
	
	/**
	 * @author neo
	 * @date 2016年8月26日
	 * @param collectId
	 * @return
	 */
	@RequestMapping(value="/list/{collectId}")
	public List<Comment> list(@PathVariable("collectId") long collectId) {
		List<Comment> comments= CommentRepository.findByCollectIdOrderByIdDesc(collectId);
		return convertComment(comments);
	}
	
	
	/**
	 * @author neo
	 * @date 2016年8月26日
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	public Response delete(@PathVariable("id") long id) {
		CommentRepository.deleteById(id);
		return result();
	}

	
	/**
	 * 转化时间和用户名
	 * @author neo
	 * @date 2016年8月26日
	 * @param comments
	 * @return
	 */
	private List<Comment> convertComment(List<Comment> comments) {
		for (Comment comment : comments) {
			User user = userRepository.findById((long)comment.getUserId());
			comment.setCommentTime(DateUtils.getTimeFormatText(comment.getCreateTime()));
			comment.setUserName(user.getUserName());
			comment.setProfilePicture(user.getProfilePicture());
			if(comment.getReplyUserId()!=null && comment.getReplyUserId()!=0){
				 User replyUser = userRepository.findById((long)comment.getReplyUserId());
				 comment.setReplyUserName(replyUser.getUserName());
			}
		}
		return comments;
	}
	
}
