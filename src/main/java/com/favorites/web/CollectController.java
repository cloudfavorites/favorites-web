package com.favorites.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectRepository;
import com.favorites.domain.CollectSummary;
import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.service.CollectService;
import com.favorites.service.FavoritesService;
import com.favorites.utils.HtmlUtil;

@RestController
@RequestMapping("/collect")
public class CollectController extends BaseController{
	
	@Autowired
	private CollectRepository collectRepository;
	@Resource
	private FavoritesService favoritesService;
	@Resource
	private CollectService collectService;
	@Autowired
	private FavoritesRepository favoritesRepository;
	
	@RequestMapping(value="/standard/{type}")
	public String standard(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
	        @RequestParam(value = "size", defaultValue = "6") Integer size,@PathVariable("type") String type) {
		Sort sort = new Sort(Direction.DESC, "id");
	    Pageable pageable = new PageRequest(page, size, sort);
	    List<CollectSummary> collects=collectService.getCollects(type,getUserId(), pageable);
		model.addAttribute("collects", collects);
		model.addAttribute("type", type);
		Favorites favorites = new Favorites();
		if(!"my".equals(type)&&!"explore".equals(type)){
			try {
				favorites = favoritesRepository.findOne(Long.parseLong(type));
			} catch (Exception e) {
				logger.error("获取收藏夹异常：",e);
			}
		}
		model.addAttribute("favorites", favorites);
		logger.info("user info :"+getUser());
		return "collect/standard";
	}
	
	
	@RequestMapping(value="/simple/{type}")
	public String simple(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
	        @RequestParam(value = "size", defaultValue = "20") Integer size,@PathVariable("type") String type) {
		Sort sort = new Sort(Direction.DESC, "id");
	    Pageable pageable = new PageRequest(page, size, sort);
	    List<CollectSummary> collects=collectService.getCollects(type,getUserId(), pageable);
		model.addAttribute("collects", collects);
		model.addAttribute("type", type);
		Favorites favorites = new Favorites();
		if(!"my".equals(type)&&!"explore".equals(type)){
			try {
				favorites = favoritesRepository.findOne(Long.parseLong(type));
			} catch (Exception e) {
				logger.error("获取收藏夹异常：",e);
			}
		}
		model.addAttribute("favorites", favorites);
		logger.info("user info :"+getUser());
		return "collect/simple";
	}
	
	@RequestMapping(value="/changePrivacy/{id}/{type}")
	public String changePrivacy(@PathVariable("id") long id,@PathVariable("type") String type) {
		int ss=collectRepository.modifyById(type, id);
		logger.info("user info :"+getUser());
		return "home/standard";
	}
	
	/**
	 * @author neo
	 * @date 2016年8月24日
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	public Response delete(@PathVariable("id") long id) {
		collectRepository.deleteById(id);
		return result();
	}
	
	
	/**
	 * @author neo
	 * @date 2016年8月24日
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/detail/{id}")
	public Collect detail(@PathVariable("id") long id) {
		Collect collect=collectRepository.findOne(id);
		return collect;
	}
	
	/**
	 * 文章收集
	 * @param collect
	 * @return
	 */
	@RequestMapping(value = "/collect", method = RequestMethod.POST)
	public Response login(Collect collect) {
		logger.info("collect begin, param is " + collect);
		try {
			collect.setUserId(getUserId());
			if(collectService.checkCollect(collect)){
				collectService.saveCollect(collect);
			}else{
				return result(ExceptionMsg.CollectExist);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("collect failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	
	/**
	 * 导入收藏夹
	 * @param path
	 */
	@RequestMapping("/import")
	public void importCollect(@RequestParam("htmlFile") MultipartFile htmlFile,String structure){
		logger.info("path:" + htmlFile.getOriginalFilename() + "----structure:" + structure);
		try {
			if(StringUtils.isNotBlank(structure)&& "yes".equals(structure)){
				// 按照目录结构导入
				Map<String, Map<String, String>> map = HtmlUtil.parseHtmlTwo(htmlFile.getInputStream());
				if(null == map || map.isEmpty()){
					logger.info("未获取到url连接");
					return ;
				}
				for (Entry<String, Map<String, String>> entry : map.entrySet()) {  
					  String favoritesName = entry.getKey();
					  Favorites favorites = favoritesRepository.findByUserIdAndName(getUserId(), "导入自浏览器");
						if(null == favorites){
							favorites = favoritesService.saveFavorites(getUserId(), 0l, favoritesName);
						}
						collectService.importHtml(entry.getValue(), favorites.getId(), getUserId());
				} 
			}else{
				Map<String, String> map = HtmlUtil.parseHtmlOne(htmlFile.getInputStream());
				if(null == map || map.isEmpty()){
					logger.info("未获取到url连接");
					return ;
				}
				// 全部导入到<导入自浏览器>收藏夹
				Favorites favorites = favoritesRepository.findByUserIdAndName(getUserId(), "导入自浏览器");
				if(null == favorites){
					favorites = favoritesService.saveFavorites(getUserId(), 0l, "导入自浏览器");
				}
				collectService.importHtml(map, favorites.getId(), getUserId());
			}
		} catch (Exception e) {
			logger.error("导入html异常:",e);
		}
	}
	
	/**
	 * 导出收藏夹
	 * @param name
	 * @return
	 */
	@RequestMapping("/export")
	public void export(String favoritesId,HttpServletResponse response){
		logger.info("favoritesId:" + favoritesId);
		if(StringUtils.isNotBlank(favoritesId)){
			try {
				String[] ids = favoritesId.split(",");
				String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String fileName= "favorites_" + date + ".html";
				StringBuilder sb = new StringBuilder();
				for(String id : ids){
					try {
						sb = sb.append(collectService.exportToHtml(Long.parseLong(id)));
					} catch (Exception e) {
						logger.error("异常：",e);
					}
				}
				sb = HtmlUtil.exportHtml("云收藏夹", sb);
				response.setCharacterEncoding("UTF-8");  
				response.setHeader("Content-disposition","attachment; filename=" + fileName);
				response.getWriter().print(sb);
			} catch (Exception e) {
				logger.error("异常：",e);
			}
		}
	}
}