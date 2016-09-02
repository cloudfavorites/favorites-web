package com.favorites.schedule;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectRepository;
import com.favorites.domain.FavoritesRepository;
import com.favorites.utils.DateUtils;
@Component
public class ScheduledTasks {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CollectRepository collectRespository;
	@Autowired
	private FavoritesRepository favoritesRespository;
	
	/**
	 * 回收站定时
	 */
	@Scheduled(cron="22 2 2 * * ?")
    public void autoRecovery() {
		Long date = new Date().getTime() - 20*24*60*60*1000;
		List<Long> favoritesId = favoritesRespository.findIdByName("未读列表");
		List<Collect> collectList = collectRespository.findByCreateTimeLessThanAndIsDeleteAndFavoritesIdIn(date, "no",favoritesId);
		for(Collect collect : collectList){
			try {
				logger.info("文章id:" + collect.getId());
				collectRespository.modifyIsDeleteById("yes", DateUtils.getCurrentTime(), collect.getId());
				favoritesRespository.reduceCountById(collect.getFavoritesId(), DateUtils.getCurrentTime());
			} catch (Exception e) {
				logger.error("回收站定时任务异常：",e);
			}
		}
    }

}
