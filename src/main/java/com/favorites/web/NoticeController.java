package com.favorites.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.favorites.domain.NoticeRepository;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;

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
	
	@RequestMapping(value = "/updateAtMeNoticeReaded", method = RequestMethod.POST)
	public Response updateAtMeNoticeReaded() {
		logger.info("updateAtMeNoticeReaded begin");
		try {
			noticeRepository.updateReadedByUserId("read", getUserId());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("updateUserName failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	

}
