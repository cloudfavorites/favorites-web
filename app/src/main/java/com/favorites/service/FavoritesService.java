package com.favorites.service;

import com.favorites.domain.Collect;
import com.favorites.domain.Favorites;

public interface FavoritesService {
	Favorites saveFavorites (Long userId, String name);

	Favorites saveFavorites (Collect collect);

	void countFavorites (Long id);

}
