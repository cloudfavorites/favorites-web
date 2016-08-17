package com.favorites.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.service.FavoritesService;
import com.favorites.utils.DateUtils;

@RestController
@RequestMapping("/favorites")
public class FavoritesController extends BaseController{
	
	@Autowired
	private FavoritesRepository favoritesRepository;
	@Resource
	private FavoritesService favoritesService;
	
	/**
	 * 添加
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Response addFavorites(String name){
		if(StringUtils.isNotBlank(name)){
			Favorites favorites = favoritesRepository.findByUserIdAndName(getUserId(), name);
			if(null != favorites){
				logger.info("收藏夹名称已被创建");
				return result(ExceptionMsg.FavoritesNameUsed);
			}else{
				try {
					favoritesService.saveFavorites(getUserId(), 0l, name);
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
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Response updateFavorites(String favoritesName,Long favoritesId){
		logger.info("param favoritesName:" + favoritesName + "----favoritesId:" + favoritesId);
		if(StringUtils.isNotBlank(favoritesName)&& null != favoritesId){
			Favorites favorites = favoritesRepository.findByUserIdAndName(getUserId(), favoritesName);
			if(null != favorites){
				logger.info("收藏夹名称已被创建");
				return result(ExceptionMsg.FavoritesNameUsed);
			}else{
				try {
					favoritesRepository.updateNameById(favoritesId, DateUtils.getCurrentTime(), favoritesName);
				} catch (Exception e) {
					logger.error("修改收藏夹名称异常：",e);
				}
			}
		}else{
			logger.info("参数错误name:" + favoritesName +"----" + "id:" + favoritesId);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public Response delFavorites(Long id){
		logger.info("param id:" + id);
		if(null == id){
			return result(ExceptionMsg.FAILED);
		}
		try {
			favoritesRepository.delete(id);
		} catch (Exception e) {
			logger.error("删除异常：",e);
		}
		return result();
	}

}
