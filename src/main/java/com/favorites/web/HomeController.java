package com.favorites.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.favorites.domain.Collect;
import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.service.CollectService;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController{
	
	@Autowired
	private CollectService collectService;
	@Autowired
	private FavoritesRepository favoritesRepository;
	
	@RequestMapping(value="/standard/{type}")
	public String standard(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
	        @RequestParam(value = "size", defaultValue = "15") Integer size,@PathVariable("type") String type) {
		Sort sort = new Sort(Direction.DESC, "id");
	    Pageable pageable = new PageRequest(page, size, sort);
	    Page<Collect> collects=collectService.getCollects(type,getUserId(), pageable);
		model.addAttribute("collects", collects);
		model.addAttribute("user", getUser());
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
	    Page<Collect> collects=collectService.getCollects(type,getUserId(), pageable);
		model.addAttribute("collects", collects);
		model.addAttribute("user", getUser());
		model.addAttribute("type", type);
		logger.info("user info :"+getUser());
		return "collect/simple";
	}
	
	

	
	
}