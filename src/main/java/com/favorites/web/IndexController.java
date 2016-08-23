package com.favorites.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.favorites.comm.Const;
import com.favorites.domain.Collect;
import com.favorites.domain.CollectRepository;
import com.favorites.domain.CollectSummary;
import com.favorites.domain.Config;
import com.favorites.domain.ConfigRepository;
import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.domain.FollowRepository;
import com.favorites.domain.User;
import com.favorites.domain.UserRepository;
import com.favorites.service.CollectService;
import com.favorites.utils.HtmlUtil;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{
	
	@Autowired
	private FavoritesRepository favoritesRepository;
	@Autowired
	private ConfigRepository configRepository;
	@Autowired
	private FollowRepository followRepository;
	@Autowired
	private CollectRepository collectRepository;
	@Autowired
	private UserRepository userRepository;
	@Resource
	private CollectService collectService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index(Model model) {
		//return "forward:/standard/my"; 
		long size= collectRepository.countByUserId(getUserId());
		Config config = configRepository.findByUserId(getUserId());
		Favorites favorites = favoritesRepository.findOne(Long.parseLong(config.getDefaultFavorties()));
		model.addAttribute("config",config);
		model.addAttribute("favorites",favorites);
		model.addAttribute("size",size);
		model.addAttribute("user",getUser());
		logger.info("collect size="+size+" userID="+getUserId());
		return "home";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String regist() {
		return "register";
	}
	
	@RequestMapping(value="/tool")
	public String tool() {
		return "tool";
	}
	
	@RequestMapping(value="/mobile")
	public String mobile() {
		return "mobile";
	}
	
	@RequestMapping(value="/import")
	public String importm() {
		return "import";
	}
	
	@RequestMapping(value="/newFavorites")
	public String newFavorites(){
		return "newfavorites";
	}
	
	@RequestMapping(value="/collect",method=RequestMethod.GET)
	public String collect(Model model,Collect collect) {
		List<Favorites> favoritesList = favoritesRepository.findByUserId(getUserId());
		Config config = configRepository.findByUserId(getUserId());
		List<String> followList = followRepository.findByUserId(getUserId());
		logger.info("model：" + config.getDefaultModel());
		logger.info("logoUrl:" + HtmlUtil.getImge(collect.getUrl()));
		model.addAttribute("logoUrl", HtmlUtil.getImge(collect.getUrl()));
		model.addAttribute("favoritesList",favoritesList);
		model.addAttribute("configObj", config);
		model.addAttribute("followList",followList);
		return "collect";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout() {
		getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
		getSession().removeAttribute(Const.LAST_REFERER);
		return "login";
	}

	@RequestMapping(value="/forgotPassword",method=RequestMethod.GET)
	public String forgotPassword() {
		return "forgotpassword";
	}
	
	@RequestMapping(value="/newPassword",method=RequestMethod.GET)
	public String newPassword(String email) {
		return "newpassword";
	}
	
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