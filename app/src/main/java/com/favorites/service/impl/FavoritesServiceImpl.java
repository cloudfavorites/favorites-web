package com.favorites.service.impl;

import com.favorites.domain.Collect;
import com.favorites.domain.enums.CollectType;
import com.favorites.domain.enums.IsDelete;
import com.favorites.repository.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favorites.domain.Favorites;
import com.favorites.repository.FavoritesRepository;
import com.favorites.service.FavoritesService;
import com.favorites.utils.DateUtils;

@Service("favoritesService")
public class FavoritesServiceImpl implements FavoritesService{
	
	@Autowired
	private FavoritesRepository favoritesRepository;
	@Autowired
	private CollectRepository collectRepository;

	@Override
	public Favorites saveFavorites(Long userId, String name) {
		Favorites favorites = new Favorites();
		favorites.setName(name);
		favorites.setUserId(userId);
		favorites.setCount(0l);
		favorites.setPublicCount(10l);
		favorites.setCreateTime(DateUtils.getCurrentTime());
		favorites.setLastModifyTime(DateUtils.getCurrentTime());
		favoritesRepository.save(favorites);
		return  favorites;
	}

	/**
	 * 保存
	 * @return
	 */
	public Favorites saveFavorites(Collect collect){
		Favorites favorites = new Favorites();
		favorites.setName(collect.getNewFavorites());
		favorites.setUserId(collect.getUserId());
		favorites.setCount(1l);
		if(CollectType.PUBLIC.name().equals(collect.getType())){
			favorites.setPublicCount(1l);
		}else {
			favorites.setPublicCount(10l);
		}
		favorites.setCreateTime(DateUtils.getCurrentTime());
		favorites.setLastModifyTime(DateUtils.getCurrentTime());
		favoritesRepository.save(favorites);
		return favorites;
	}


	public void countFavorites(Long id){
		Favorites favorite=favoritesRepository.findOne(id);
		favorite.setCount(collectRepository.countByFavoritesIdAndIsDelete(id, IsDelete.NO));
		favorite.setPublicCount(collectRepository.countByFavoritesIdAndTypeAndIsDelete(id, CollectType.PUBLIC, IsDelete.NO));
		favorite.setLastModifyTime(DateUtils.getCurrentTime());
		favoritesRepository.save(favorite);
	}
}
