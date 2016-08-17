package com.favorites.web;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.favorites.comm.Const;
import com.favorites.domain.Collect;
import com.favorites.domain.CollectRepository;
import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.domain.NoticeRepository;
import com.favorites.domain.User;
import com.favorites.domain.UserRepository;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.service.CollectService;
import com.favorites.service.ConfigService;
import com.favorites.service.FavoritesService;
import com.favorites.utils.DateUtils;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CollectRepository collectRepository;
	@Autowired
	private FavoritesRepository favoritesRepository;
	@Resource
	private ConfigService configService;
	@Autowired
	private NoticeRepository noticeRepository;
	@Resource
	private FavoritesService favoritesService;
	@Resource
	private CollectService collectService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response login(User user) {
		logger.info("login begin, param is " + user);
		try {
			User loginUser = userRepository.findByUserNameOrEmail(
					user.getUserName(), user.getUserName());
			if (loginUser == null || !loginUser.getPassWord().equals(getPwd(user.getPassWord()))) {
				return result(ExceptionMsg.LoginNameOrPassWordError);
			}
			getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUser);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("login failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public Response create(User user) {
		logger.info("create user begin, param is " + user);
		try {
			User registUser = userRepository.findByEmail(user.getEmail());
			if (null != registUser) {
				return result(ExceptionMsg.EmailUsed);
			}
			User userNameUser = userRepository.findByUserName(user.getUserName());
			if (null != userNameUser) {
				return result(ExceptionMsg.UserNameUsed);
			}
			user.setPassWord(getPwd(user.getPassWord()));
			user.setCreateTime(DateUtils.getCurrentTime());
			user.setLastModifyTime(DateUtils.getCurrentTime());
			userRepository.save(user);
			// 添加默认收藏夹
			Favorites favorites = favoritesService.saveFavorites(user.getId(),0l, "未读列表");
			// 添加默认属性设置
			configService.saveConfig(user.getId(),String.valueOf(favorites.getId()));
			getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("create user failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}

	@RequestMapping(value = "/collect", method = RequestMethod.POST)
	public Response login(Collect collect) {
		logger.info("collect begin, param is " + collect);
		try {
			collectService.saveCollect(collect, getUserId());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("collect failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}

	@RequestMapping(value = "/getFavorites", method = RequestMethod.POST)
	public List<Favorites> getFavorites() {
		logger.info("getFavorites begin");
		List<Favorites> favorites = null;
		try {
			favorites = favoritesRepository.findByUserId(getUserId());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getFavorites failed, ", e);
		}
		logger.info("getFavorites end favorites ==" + favorites);
		return favorites;
	}

	@RequestMapping("/uid")
	String uid(HttpSession session) {
		UUID uid = (UUID) session.getAttribute("uid");
		if (uid == null) {
			uid = UUID.randomUUID();
		}
		session.setAttribute("uid", uid);
		return session.getId();
	}

}