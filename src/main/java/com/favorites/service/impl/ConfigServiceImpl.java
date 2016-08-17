package com.favorites.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favorites.domain.Config;
import com.favorites.domain.ConfigRepository;
import com.favorites.service.ConfigService;
import com.favorites.utils.DateUtils;

@Service("configService")
public class ConfigServiceImpl implements ConfigService{
	
	@Autowired
	private ConfigRepository configRepository;
	
	/**
	 * 保存属性设置
	 * @param userId
	 * @param favoritesId
	 * @return
	 */
	public Config saveConfig(Long userId,String favoritesId){
		Config config = new Config();
		config.setUserId(userId);
		config.setDefaultModel("simple");
		config.setDefaultFavorties(favoritesId);
		config.setDefaultCollectType("public");
		config.setCreateTime(DateUtils.getCurrentTime());
		config.setLastModifyTime(DateUtils.getCurrentTime());
		configRepository.save(config);
		return config;
	}

}
