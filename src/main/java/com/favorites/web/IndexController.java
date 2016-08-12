package com.favorites.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.favorites.domain.Collect;
import com.favorites.domain.Config;
import com.favorites.domain.ConfigRepository;
import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.utils.HtmlUtil;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{
	
	@Autowired
	private FavoritesRepository favoritesRepository;
	@Autowired
	private ConfigRepository configRepository;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index() {
		return "tool";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String regist() {
		return "register";
	}
	
	@RequestMapping(value="/tool",method=RequestMethod.GET)
	public String tool() {
		return "tool";
	}
	
	@RequestMapping(value="/mobile",method=RequestMethod.GET)
	public String mobile() {
		return "mobile";
	}
	
	@RequestMapping(value="/import",method=RequestMethod.GET)
	public String importm() {
		return "import";
	}
	
	@RequestMapping(value="/collect",method=RequestMethod.GET)
	public String collect(Model model,Collect collect) {
		List<Favorites> favoritesList = favoritesRepository.findByUserId(getUserId());
		Config config = configRepository.findByUserId(getUserId());
		logger.info("model：" + config.getDefaultModel());
		logger.info("logoUrl:" + HtmlUtil.getImge(collect.getUrl()));
		model.addAttribute("logoUrl", HtmlUtil.getImge(collect.getUrl()));
		model.addAttribute("favoritesList",favoritesList);
		model.addAttribute("configObj", config);
		return "collect";
	}
	

}