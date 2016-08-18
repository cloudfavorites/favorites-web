package com.favorites.service;

import com.favorites.domain.Config;

public interface ConfigService {
	
	public Config saveConfig(Long userId,String favoritesId);

	public void updateConfig(Long id ,String type,String defaultFavorites);
}
