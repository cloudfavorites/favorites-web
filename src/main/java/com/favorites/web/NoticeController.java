package com.favorites.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.favorites.domain.Comment;
import com.favorites.domain.Notice;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.domain.result.ResponseData;
import com.favorites.repository.CommentRepository;
import com.favorites.repository.NoticeRepository;
import com.favorites.utils.DateUtils;

/**
*@ClassName: NoticeController
*@Description: 
*@author YY 
*@date 2016年8月31日  上午9:59:47
*@version 1.0
*/

@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController{
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	/**
	 * 更新消息为已读
	 * @return
	 */
	@RequestMapping(value = "/updateNoticeReaded", method = RequestMethod.POST)
	public ResponseData updateAtMeNoticeReaded(String type) {
		logger.info("updateNoticeReaded begin");
		try {
			int counts = noticeRepository.updateReadedByUserId("read", getUserId(), type);
			return new ResponseData(ExceptionMsg.SUCCESS, counts);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("updateNoticeReaded failed, ", e);
			return new ResponseData(ExceptionMsg.FAILED);
		}
	}
	
	/**
	 * 回复
	 * @param comment
	 * @return
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public Response reply(Comment comment) {
		logger.info("reply begin");
		try {
			comment.setUserId(getUserId());
			comment.setCreateTime(DateUtils.getCurrentTime());
			Comment saveCommon = commentRepository.save(comment);
			Notice notice = new Notice();
			notice.setCollectId(comment.getCollectId().toString());
			notice.setUserId(comment.getReplyUserId());
			notice.setType("comment");
			notice.setReaded("unread");
			notice.setOperId(saveCommon.getId().toString());
			notice.setCreateTime(DateUtils.getCurrentTime());
			noticeRepository.save(notice);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("reply failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}

}
