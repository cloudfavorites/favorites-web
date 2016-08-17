package com.favorites.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favorites.domain.CollectRepository;

@RestController
@RequestMapping("/collect")
public class CollectController extends BaseController{
	
	@Autowired
	private CollectRepository collectRepository;
	@RequestMapping(value="/changePrivacy/{id}/{type}")
	public String changePrivacy(Model model,@PathVariable("id") long id,@PathVariable("type") String type) {
		collectRepository.modifyById(type, id);
		logger.info("user info :"+getUser());
		return "home/standard";
	}
	
	

	
	
}