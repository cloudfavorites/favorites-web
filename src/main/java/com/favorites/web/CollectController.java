package com.favorites.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectRepository;
import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.ResponseData;
import com.favorites.service.FavoritesService;
import com.favorites.utils.DateUtils;
import com.favorites.utils.HtmlUtil;

@RestController
@RequestMapping("/collect")
public class CollectController extends BaseController{
	
	@Autowired
	private CollectRepository collectRepository;
	@Autowired
	private FavoritesRepository favoritesRepository;
	@Resource
	private FavoritesService favoritesService;
	
	@RequestMapping(value="/changePrivacy/{id}/{type}")
	public String changePrivacy(Model model,@PathVariable("id") long id,@PathVariable("type") String type) {
		collectRepository.modifyById(type, id);
		logger.info("user info :"+getUser());
		return "home/standard";
	}
	
	/**
	 * 导入收藏夹
	 * @param path
	 */
	@RequestMapping("/import")
	public void importCollect(@RequestParam("htmlFile") MultipartFile htmlFile,Long favoritesId){
		logger.info("path:" + htmlFile.getOriginalFilename());
		if(null == favoritesId){
			logger.info("获取导入收藏夹ID失败："+ favoritesId);
			return;
		}
		try {
			List<String> urlList = HtmlUtil.importHtml(htmlFile.getInputStream());
			for(String url : urlList){
				try {
					Map<String, String> result = HtmlUtil.getCollectFromUrl(url);
					Collect collect = new Collect();
					collect.setCharset(result.get("charset"));
					if(StringUtils.isBlank(result.get("title"))){
						if(StringUtils.isNotBlank(result.get("description"))){
							collect.setTitle(result.get("description"));
						}else{
							continue;
						}
					}else{
						collect.setTitle(result.get("title"));
					}
					if(StringUtils.isBlank(result.get("description"))){
						collect.setDescription(collect.getTitle());
					}else{
						collect.setDescription(result.get("description"));
					}
					collect.setFavoritesId(favoritesId);
					collect.setIsDelete("no");
					collect.setLogoUrl(result.get("logoUrl"));
					collect.setType("private");
					collect.setUrl(url);
					collect.setUserId(getUserId());
					collect.setCreateTime(DateUtils.getCurrentTime());
					collect.setLastModifyTime(DateUtils.getCurrentTime());
					collectRepository.save(collect);
					favoritesRepository.updateCountById(favoritesId, DateUtils.getCurrentTime());
				} catch (Exception e) {
					logger.error("导入存储异常：",e);
				}
			}
		} catch (Exception e) {
			logger.error("导入html异常:",e);
		}
	}
	

	
	
}