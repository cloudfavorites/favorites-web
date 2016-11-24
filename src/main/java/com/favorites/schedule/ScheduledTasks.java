package com.favorites.schedule;


import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Collect;
import com.favorites.domain.enums.IsDelete;
import com.favorites.repository.CollectRepository;
import com.favorites.repository.FavoritesRepository;
import com.favorites.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
	@LoggerManage(description="回收站定时")
    public void autoRecovery() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DAY_OF_YEAR,-30);
		Long date = ca.getTime().getTime();
		List<Long> favoritesId = favoritesRespository.findIdByName("未读列表");
		List<Collect> collectList = collectRespository.findByCreateTimeLessThanAndIsDeleteAndFavoritesIdIn(date, IsDelete.NO,favoritesId);
		for(Collect collect : collectList){
			try {
				logger.info("文章id:" + collect.getId());
				collectRespository.modifyIsDeleteById(IsDelete.YES, DateUtils.getCurrentTime(), collect.getId());
				favoritesRespository.reduceCountById(collect.getFavoritesId(), DateUtils.getCurrentTime());
			} catch (Exception e) {
				logger.error("回收站定时任务异常：",e);
			}
		}
    }

}
