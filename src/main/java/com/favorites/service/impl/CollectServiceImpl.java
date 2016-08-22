package com.favorites.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectRepository;
import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.domain.Notice;
import com.favorites.domain.User;
import com.favorites.domain.UserRepository;
import com.favorites.service.CollectService;
import com.favorites.service.FavoritesService;
import com.favorites.service.NoticeService;
import com.favorites.utils.DateUtils;
import com.favorites.utils.StringUtil;

@Service("collectService")
public class CollectServiceImpl implements CollectService {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private CollectRepository collectRepository;

	@Autowired
	private FavoritesRepository favoritesRepository;
	@Resource
	private FavoritesService favoritesService;
	@Autowired
	private UserRepository userRepository;
	@Resource
	private NoticeService noticeService;

	@Override
	public Page<Collect> getCollects(String type, Long userId, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Collect> collects = null;
		if ("my".equals(type)) {
			collects = collectRepository.findByUserId(userId, pageable);
		} else if ("explore".equals(type)) {
			collects = collectRepository.findAll(pageable);
		} else {
			collects = collectRepository.findByFavoritesId(
					Long.parseLong(type), pageable);
		}
		return convertCollect(collects);
	}

	/**
	 * @author neo
	 * @date 2016年8月11日
	 * @param collects
	 * @return
	 */
	private Page<Collect> convertCollect(Page<Collect> collects) {
		for (Collect collect : collects) {
			collect.setCollectTime(DateUtils.getTimeFormatText(collect
					.getLastModifyTime()));
		}
		return collects;
	}

	/**
	 * 收藏文章
	 * @param collect
	 * @param userId
	 */
	public void saveCollect(Collect collect, Long userId) {
		collect.setUserId(userId);
		collect.setCreateTime(DateUtils.getCurrentTime());
		collect.setLastModifyTime(DateUtils.getCurrentTime());
		collect.setIsDelete("no");
		if (StringUtils.isNotBlank(collect.getNewFavorites())) {
			Favorites favorites = favoritesRepository.findByUserIdAndName(userId, collect.getNewFavorites());
			if (null == favorites) {
				favorites = favoritesService.saveFavorites(userId, 1l,collect.getNewFavorites());
			} else {
				favoritesRepository.updateCountById(favorites.getId(),DateUtils.getCurrentTime());
			}
			collect.setFavoritesId(favorites.getId());
		} else {
			favoritesRepository.updateCountById(collect.getFavoritesId(),DateUtils.getCurrentTime());
		}
		if (StringUtils.isBlank(collect.getType())) {
			collect.setType("public");
		}
		if(StringUtils.isBlank(collect.getDescription())){
			collect.setDescription(collect.getTitle());
		}
		collectRepository.save(collect);
		if (StringUtils.isNotBlank(collect.getRemark())&& collect.getRemark().indexOf("@") > -1) {
			List<String> atUsers = StringUtil.getAtUser(collect.getRemark());
			for (String str : atUsers) {
				logger.info("用户名：" + str);
				User user = userRepository.findByUserName(str);
				if (null != user) {
					// 保存消息通知
					noticeService.saveNotice(String.valueOf(collect.getId()),"at", userId, null);
				} else {
					logger.info("为找到匹配：" + str + "的用户.");
				}
			}
		}
	}
	
	/**
	 * 验证是否重复收藏
	 * @param collect
	 * @param userId
	 * @return
	 */
	public boolean checkCollect(Collect collect,Long userId){
		if(StringUtils.isNotBlank(collect.getNewFavorites())){
			// url+favoritesId+userId
			Favorites favorites = favoritesRepository.findByUserIdAndName(userId, collect.getNewFavorites());
			if(null == favorites){
				return true;
			}else{
				List<Collect> list = collectRepository.findByFavoritesIdAndUrlAndUserId(favorites.getId(), collect.getUrl(), userId);
				if(null != list && list.size() > 0){
					return false;
				}else{
					return true;
				}
			}
		}else{
			List<Collect> list = collectRepository.findByFavoritesIdAndUrlAndUserId(collect.getFavoritesId(), collect.getUrl(), userId);
			if(null != list && list.size() > 0){
				return false;
			}else{
				return true;
			}
		}
	}

}
