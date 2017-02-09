package com.favorites.web;

import com.favorites.comm.Const;
import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.*;
import com.favorites.domain.enums.IsDelete;
import com.favorites.domain.view.CollectSummary;
import com.favorites.repository.*;
import com.favorites.service.CollectService;
import com.favorites.service.LookAroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
	@Resource
	private CollectService collectService;
	@Autowired
	private NoticeRepository noticeRepository;

	/**
	 * 随便看看  added by chenzhimin
	 */
	@Autowired
	private LookAroundService lookAroundService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	@LoggerManage(description="首页")
	public String index(){
		return "index";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	@LoggerManage(description="登陆后首页")
	public String home(Model model) {
		long size= collectRepository.countByUserIdAndIsDelete(getUserId(),IsDelete.NO);
		Config config = configRepository.findByUserId(getUserId());
		Favorites favorites = favoritesRepository.findOne(Long.parseLong(config.getDefaultFavorties()));
		List<String> followList = followRepository.findByUserId(getUserId());
		model.addAttribute("config",config);
		model.addAttribute("favorites",favorites);
		model.addAttribute("size",size);
		model.addAttribute("followList",followList);
		model.addAttribute("user",getUser());
		model.addAttribute("newAtMeCount",noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "at", "unread"));
		model.addAttribute("newCommentMeCount",noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "comment", "unread"));
		model.addAttribute("newPraiseMeCount",noticeRepository.countPraiseByUserIdAndReaded(getUserId(), "unread"));
		logger.info("collect size="+size+" userID="+getUserId());
		return "home";
	}

	/**
	 * 随便看看 标准模式显示  added by chenzhimin
	 * @return
	 */
	@RequestMapping(value="/lookAround/standard/{category}",method=RequestMethod.GET)
	@LoggerManage(description="随便看看页面")
	public String lookAroundStandard(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
							 @RequestParam(value = "size", defaultValue = "15") Integer size,
							 @PathVariable("category") String category) {

		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(page, size, sort);
		model.addAttribute("category", category);
		model.addAttribute("type", "lookAround");
		Favorites favorites = new Favorites();
		List<CollectSummary> collects = null;
		List<CollectSummary> fivecollects = lookAroundService.scrollFiveCollect();
		List<UserIsFollow> fiveUsers = lookAroundService.queryFiveUser(this.getUserId());

		collects =lookAroundService.queryCollectExplore(pageable,getUserId(),category);
		model.addAttribute("fiveCollects", fivecollects);
		model.addAttribute("fiveUsers", fiveUsers);
		model.addAttribute("collects", collects);
		model.addAttribute("favorites", favorites);
		model.addAttribute("userId", getUserId());
		model.addAttribute("size", collects.size());
		return "lookAround/standard";
	}

	/**
	 * 随便看看 简单模式显示  added by chenzhimin
	 * @return
	 */
	@RequestMapping(value="/lookAround/simple/{category}",method=RequestMethod.GET)
	@LoggerManage(description="随便看看页面")
	public String lookAroundSimple(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
									 @RequestParam(value = "size", defaultValue = "20") Integer size,
									 @PathVariable("category") String category) {

		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(page, size, sort);
		model.addAttribute("category", category);
		model.addAttribute("type", "explore");
		Favorites favorites = new Favorites();
		List<CollectSummary> collects = null;
		List<CollectSummary> fivecollects = lookAroundService.scrollFiveCollect();
		List<UserIsFollow> fiveUsers = lookAroundService.queryFiveUser(this.getUserId());

		collects =lookAroundService.queryCollectExplore(pageable,getUserId(),category);
		model.addAttribute("fiveCollects", fivecollects);
		model.addAttribute("fiveUsers", fiveUsers);
		model.addAttribute("collects", collects);
		model.addAttribute("favorites", favorites);
		model.addAttribute("userId", getUserId());
		model.addAttribute("size", collects.size());
		return "lookAround/simple";
	}

	@RequestMapping(value="/login",method=RequestMethod.GET)
	@LoggerManage(description="登陆页面")
	public String login() {

		return "login";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	@LoggerManage(description="注册页面")
	public String regist() {
		return "register";
	}
	
	@RequestMapping(value="/tool")
	@LoggerManage(description="工具页面")
	public String tool(Model model) {
	  String path="javascript:(function()%7Bvar%20description;var%20desString=%22%22;var%20metas=document.getElementsByTagName('meta');for(var%20x=0,y=metas.length;x%3Cy;x++)%7Bif(metas%5Bx%5D.name.toLowerCase()==%22description%22)%7Bdescription=metas%5Bx%5D;%7D%7Dif(description)%7BdesString=%22&amp;description=%22+encodeURIComponent(description.content);%7Dvar%20win=window.open(%22"
              	+Const.BASE_PATH
              	+"collect?from=webtool&url=%22+encodeURIComponent(document.URL)+desString+%22&title=%22+encodeURIComponent(document.title)+%22&charset=%22+document.charset,'_blank');win.focus();%7D)();";
	  model.addAttribute("path",path);
		return "tool";
	}
	
	@RequestMapping(value="/mobile")
	@LoggerManage(description="移动客户端页面")
	public String mobile() {
		return "mobile";
	}
	
	@RequestMapping(value="/import")
	@LoggerManage(description="收藏夹导入页面")
	public String importm() {
		return "favorites/import";
	}
	
	@RequestMapping(value="/newFavorites")
	@LoggerManage(description="新建收藏夹页面")
	public String newFavorites(){
		return "favorites/newfavorites";
	}
	
	@RequestMapping(value="/collect",method=RequestMethod.GET)
	@LoggerManage(description="收藏页面")
	public String collect(Model model,Collect collect) {
		List<Favorites> favoritesList = favoritesRepository.findByUserId(getUserId());
		Config config = configRepository.findByUserId(getUserId());
		List<String> followList = followRepository.findByUserId(getUserId());
		logger.info("model：" + config.getDefaultModel());
		model.addAttribute("favoritesList",favoritesList);
		model.addAttribute("configObj", config);
		model.addAttribute("followList",followList);
		return "collect";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	@LoggerManage(description="登出")
	public String logout(HttpServletResponse response) {
		getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
		getSession().removeAttribute(Const.LAST_REFERER);
		Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "index";
	}

	@RequestMapping(value="/forgotPassword",method=RequestMethod.GET)
	@LoggerManage(description="忘记密码页面")
	public String forgotPassword() {
		return "user/forgotpassword";
	}
	
	@RequestMapping(value="/newPassword",method=RequestMethod.GET)
	public String newPassword(String email) {
		return "user/newpassword";
	}

	@RequestMapping(value="/uploadHeadPortrait")
	@LoggerManage(description="上传你头像页面")
	public String uploadHeadPortrait(){
		return "user/uploadheadportrait";
	}
	
	@RequestMapping(value="/export")
	@LoggerManage(description="收藏夹导出页面")
	public String export(Model model){
		List<Favorites> favoritesList = favoritesRepository.findByUserId(getUserId());
		model.addAttribute("favoritesList",favoritesList);
		return "favorites/export";
	}

	@RequestMapping(value="/uploadBackground")
	@LoggerManage(description="上传背景页面")
	public String uploadBackground(){
		return "user/uploadbackground";
	}
	
}