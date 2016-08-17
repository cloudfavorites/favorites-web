package com.favorites.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.utils.DateUtils;

@RestController
@RequestMapping("/favorites")
public class FavoritesController extends BaseController{
	
	@Autowired
	private FavoritesRepository favoritesRepository;
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Response addFavorites(String name){
		if(StringUtils.isNotBlank(name)){
			Favorites favorites = favoritesRepository.findByUserIdAndName(getUserId(), name);
			if(null != favorites){
				logger.info("收藏夹名称已被创建");
				return result(ExceptionMsg.FavoritesNameUsed);
			}else{
				try {
					favorites = new Favorites();
					favorites.setCount(0l);
					favorites.setName(name);
					favorites.setUserId(getUserId());
					favorites.setCreateTime(DateUtils.getCurrentTime());
					favorites.setLastModifyTime(DateUtils.getCurrentTime());
					favoritesRepository.save(favorites);
				} catch (Exception e) {
					logger.error("异常：",e);
					return result(ExceptionMsg.FAILED);
				}
			}
		}else{
			logger.info("收藏夹名称为空");
			return result(ExceptionMsg.FavoritesNameIsNull);
		}
		return result();
	}

}
