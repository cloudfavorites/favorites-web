package com.favorites.service;

import com.favorites.domain.Config;

public interface ConfigService {

	Config saveConfig (Long userId, String favoritesId);

	void updateConfig (Long id, String type, String defaultFavorites);
}
