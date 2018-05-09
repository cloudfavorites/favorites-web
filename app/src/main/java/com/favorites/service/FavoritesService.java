package com.favorites.service;

import com.favorites.domain.Collect;
import com.favorites.domain.Favorites;

public interface FavoritesService {
	public Favorites saveFavorites(Long userId,String name);
	public Favorites saveFavorites(Collect collect);
	public void countFavorites(long id);

}
