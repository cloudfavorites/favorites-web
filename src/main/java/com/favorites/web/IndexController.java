package com.favorites.web;

import com.favorites.comm.Const;
import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Collect;
import com.favorites.domain.Config;
import com.favorites.domain.Favorites;
import com.favorites.domain.enums.IsDelete;
import com.favorites.repository.*;
import com.favorites.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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