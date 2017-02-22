package com.favorites.schedule;


import com.favorites.cache.CacheService;
import com.favorites.comm.Const;
import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Collect;
import com.favorites.domain.UrlLibrary;
import com.favorites.domain.enums.IsDelete;
import com.favorites.repository.CollectRepository;
import com.favorites.repository.FavoritesRepository;
import com.favorites.repository.UrlLibraryRepository;
import com.favorites.repository.UserRepository;
import com.favorites.utils.DateUtils;
import com.favorites.utils.HtmlUtil;
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
	@Autowired
	private UrlLibraryRepository urlLibraryRepository;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private UserRepository userRepository;
	
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

	@Scheduled(cron="11 1 1 * * ?")
	@LoggerManage(description="获取图片logoUrl定时")
    public void getImageLogoUrl(){
		List<UrlLibrary> urlLibraryList = urlLibraryRepository.findByCountLessThanAndLogoUrl(10, Const.BASE_PATH+"img/logo.jpg");
		logger.info("集合长度：" + urlLibraryList.size());
		for(UrlLibrary urlLibrary : urlLibraryList){
			try {
				String logoUrl = HtmlUtil.getImge(urlLibrary.getUrl());
				// 刷新缓存
				boolean result = cacheService.refreshOne(urlLibrary.getUrl(),logoUrl);
				if(result){
					collectRespository.updateLogoUrlByUrl(logoUrl,DateUtils.getCurrentTime(),urlLibrary.getUrl());
					urlLibraryRepository.updateLogoUrlById(urlLibrary.getId(),logoUrl);
				}else{
					urlLibraryRepository.increaseCountById(urlLibrary.getId());
				}
			}catch (Exception e){
				logger.error("获取图片异常：",e);
			}
		}
	}

	/**
	 * 回收站定时
	 */
	@Scheduled(cron="0 50 15 * * ?")
	@LoggerManage(description="自动清除不能访问文章定时")
	public void clearInvalidCollect() {
		//查询设置自动清除文章的用户
		List<Long> userList = userRepository.findAutoClearCollectUsers();
		logger.info("设置自动清除无效文章用户集合长度：" + userList.size());
		for(Long userId : userList){
			try {
				//查询用户文章列表
				List<Collect> collectList = collectRespository.findByUserIdAndIsDelete(userId,IsDelete.NO);
				for(Collect collect:collectList){
					//判断链接是否有效
					boolean bl = HtmlUtil.isConnect(collect.getUrl());
					//链接无效则删除
					if(bl==false){
						collectRespository.delete(collect.getId());
						logger.info("用户ID:"+collect.getUserId()+",文章链接:"+collect.getUrl()+"无效，已被删除");
					}
				}
			}catch (Exception e){
				logger.error("自动清除不能访问文章定时任务异常：",e);
			}
		}
	}

}
