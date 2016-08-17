package com.favorites.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favorites.domain.Notice;
import com.favorites.domain.NoticeRepository;
import com.favorites.service.NoticeService;
import com.favorites.utils.DateUtils;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeRepository noticeRepository;
	
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

}
