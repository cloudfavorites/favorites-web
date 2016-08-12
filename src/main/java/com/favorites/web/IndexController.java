package com.favorites.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.favorites.domain.Collect;
import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.utils.HtmlUtil;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{
	
	@Autowired
	private FavoritesRepository favoritesRepository;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String hello(Locale locale, Model model) {
		model.addAttribute("greeting", "Hello  neo!");
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);        
		String formattedDate = dateFormat.format(date);
		model.addAttribute("currentTime", formattedDate);
		return "hello";
	}
	
	@RequestMapping(value="/about",method=RequestMethod.GET)
	public String about() {
		return "about";
	}
	
	@RequestMapping(value="/contact",method=RequestMethod.GET)
	public String contact() {
		return "contact";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String regist() {
		return "register";
	}
	
	@RequestMapping(value="/collect",method=RequestMethod.GET)
	public String collect(Model model,Collect collect) {
		if(getUser()==null){
			return "login";
		}
		List<Favorites> favoritesList = favoritesRepository.findByUserId(getUserId());
		logger.info("logoUrl:" + HtmlUtil.getImge(collect.getUrl()));
		model.addAttribute("logoUrl", HtmlUtil.getImge(collect.getUrl()));
		model.addAttribute("favoritesList",favoritesList);
		return "collect";
	}
}