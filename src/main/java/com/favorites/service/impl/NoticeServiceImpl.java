package com.favorites.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favorites.domain.CollectSummary;
import com.favorites.domain.CollectView;
import com.favorites.domain.CommentRepository;
import com.favorites.domain.CommentView;
import com.favorites.domain.Notice;
import com.favorites.domain.NoticeRepository;
import com.favorites.domain.PraiseRepository;
import com.favorites.service.NoticeService;
import com.favorites.utils.DateUtils;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PraiseRepository praiseRepository;
	
	/**
	 * 保存消息通知
	 * @param collectId
	 * @param type
	 * @param userId
	 * @param operId
	 */
	public void saveNotice(String collectId,String type,Long userId,String operId){
		Notice notice = new Notice();
		notice.setCollectId(collectId);
		notice.setReaded("unread");
		notice.setType(type);
		if(StringUtils.isNotBlank(operId)){
			notice.setOperId(operId);
		}
		notice.setUserId(userId);
		notice.setCreateTime(DateUtils.getCurrentTime());
		noticeRepository.save(notice);
	}

	/**
	 * 展示消息通知@我的
	 * @param type
	 * @param userId
	 * @param pageable
	 */
	@Override
	public List<CollectSummary> getNoticeCollects(String type, Long userId, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<CollectView> views = noticeRepository.findViewByUserIdAndType(userId, type, pageable);
		return convertCollect(views, type);
	}

	private List<CollectSummary> convertCollect(Page<CollectView> views, String type) {
		List<CollectSummary> summarys=new ArrayList<CollectSummary>();
		for (CollectView view : views) {
			CollectSummary summary=new CollectSummary(view);
			if("at".equals(type)){
				summary.setCollectTime(DateUtils.getTimeFormatText(view.getLastModifyTime())+" at了你");
			}else if("comment".equals(type)){
				CommentView comment = commentRepository.findReplyUser(Long.valueOf(view.getOperId()));
				summary.setUserId(comment.getUserId());
				summary.setUserName(comment.getUserName());
				summary.setProfilePicture(comment.getProfilePicture());
				summary.setRemark(comment.getContent());
				summary.setCollectTime(DateUtils.getTimeFormatText(comment.getCreateTime())+" 评论了你的收藏");
			}else if("praise".equals(type)){
				summary.setCollectTime(DateUtils.getTimeFormatText(view.getLastModifyTime())+" 赞了你的收藏");
				
			}		
			summarys.add(summary);
		}
		return summarys;
	}
}
