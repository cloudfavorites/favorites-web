package com.favorites.web;

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

@Controller
@RequestMapping("/")
public class HomeController extends BaseController{
	
	@Autowired
	private CollectRepository collectRepository;
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public String index(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
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
		model.addAttribute("user", getUser());
		logger.info("user info :"+getUser());
		return "home";
	}
	
}