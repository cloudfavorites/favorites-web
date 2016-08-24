package com.favorites.web;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.favorites.domain.CollectRepository;
import com.favorites.domain.CollectSummary;
import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.domain.FollowRepository;
import com.favorites.domain.User;
import com.favorites.domain.UserRepository;
import com.favorites.service.CollectService;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController{
	
	@Autowired
	private CollectService collectService;
	@Autowired
	private FavoritesRepository favoritesRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CollectRepository collectRepository;
	@Autowired
	private FollowRepository followRepository;
	
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
	        @RequestParam(value = "size", defaultValue = "10") Integer size,@PathVariable("type") String type) {
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
	
	/**
	 * 个人首页
	 * @param model
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/user/{userId}")
	public String userPageShow(Model model,@PathVariable("userId") Long userId,@RequestParam(value = "page", defaultValue = "0") Integer page,
	        @RequestParam(value = "size", defaultValue = "15") Integer size){
		logger.info("userId:" + userId);
		User user = userRepository.findOne(userId);
		Long collectCount = 0l;
		Sort sort = new Sort(Direction.DESC, "id");
	    Pageable pageable = new PageRequest(page, size, sort);
	    List<CollectSummary> collects = null;
	    Integer isFollow = 0;
		if(getUserId().longValue() == userId.longValue()){
			model.addAttribute("myself","yes");
			collectCount = collectRepository.countByUserId(userId);
			collects =collectService.getCollects("my", userId, pageable);
		}else{
			model.addAttribute("myself","no");
			collectCount = collectRepository.countByUserIdAndType(userId, "public");
			collects =collectService.getCollects("others", userId, pageable);
			isFollow = followRepository.countByUserIdAndFollowIdAndStatus(getUserId(), String.valueOf(userId), "follow");
		}
		Integer follow = followRepository.countByUserIdAndStatus(userId, "follow");
		Integer followed = followRepository.countByFollowIdAndStatus(String.valueOf(userId), "follow");
		List<Favorites> favoritesList = favoritesRepository.findByUserId(userId);
		List<String> followUser = followRepository.findFollowUserByUserId(userId);
		List<String> followedUser = followRepository.findFollowedUserByFollowId(String.valueOf(userId));
		model.addAttribute("collectCount",collectCount);
		model.addAttribute("follow",follow);
		model.addAttribute("followed",followed);
		model.addAttribute("user",user);
		model.addAttribute("collects", collects);
		model.addAttribute("favoritesList",favoritesList);
		model.addAttribute("followUser",followUser);
		model.addAttribute("followedUser",followedUser);
		model.addAttribute("isFollow",isFollow);
		return "user";
	}

	
	
}