package com.favorites.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectRepository;
import com.favorites.utils.HtmlUtil;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{
	
	@Autowired
	private CollectRepository collectRepository;

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String hello(Locale locale, Model model) {
		model.addAttribute("greeting", "Hello  neo!");
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);        
		String formattedDate = dateFormat.format(date);
		model.addAttribute("currentTime", formattedDate);
		return "hello";
	}
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public String home(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
	        @RequestParam(value = "size", defaultValue = "15") Integer size) {
		Sort sort = new Sort(Direction.DESC, "id");
	    Pageable pageable = new PageRequest(page, size, sort);
	    Page<Collect> collects=null;
	    if(getUserId()!=0){
	    	 collects=collectRepository.findByUserId(getUserId(),pageable);
	    }else{
	    	 collects=collectRepository.findAll(pageable);
	    }
		model.addAttribute("collects", collects);
		return "home";
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
	
	@RequestMapping(value="/collect",method=RequestMethod.GET)
	public String collect(Model model,Collect collect) {
		if(getUser()==null){
			return "login";
		}
		model.addAttribute("webLogo", HtmlUtil.getLogo(collect.getUrl()));
		return "collect";
	}
}