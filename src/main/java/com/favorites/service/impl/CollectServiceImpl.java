package com.favorites.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectRepository;
import com.favorites.service.CollectService;
import com.favorites.utils.DateUtils;

@Service("collectService")
public class CollectServiceImpl implements CollectService{
	
    protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private CollectRepository collectRepository;

	@Override
	public Page<Collect> getCollects(String type,Long userId,Pageable pageable) {
		// TODO Auto-generated method stub
	    Page<Collect> collects=null;
	    if("my".equals(type)){
	    	 collects=collectRepository.findByUserId(userId,pageable);
	    }else if("unread".equals(type)){
	    	 collects=collectRepository.findByFavoritesId(1l, pageable);
	    }else if("explore".equals(type)){
	    	 collects=collectRepository.findAll(pageable);
	    }
		return convertCollect(collects);
	}
	
	
	/**
	 * @author neo
	 * @date 2016年8月11日
	 * @param collects
	 * @return
	 */
	private Page<Collect> convertCollect(Page<Collect> collects){
		for (Collect collect : collects) {
			collect.setCollectTime(DateUtils.getTimeFormatText(collect.getLastModifyTime()));
		}
		return collects;
	}
	

}
