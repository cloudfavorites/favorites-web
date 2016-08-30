package com.favorites.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectRepository;
import com.favorites.domain.CollectSummary;
import com.favorites.domain.CollectView;
import com.favorites.domain.CommentRepository;
import com.favorites.domain.Favorites;
import com.favorites.domain.FavoritesRepository;
import com.favorites.domain.FollowRepository;
import com.favorites.domain.Praise;
import com.favorites.domain.PraiseRepository;
import com.favorites.domain.User;
import com.favorites.domain.UserRepository;
import com.favorites.service.CollectService;
import com.favorites.service.FavoritesService;
import com.favorites.service.NoticeService;
import com.favorites.utils.DateUtils;
import com.favorites.utils.HtmlUtil;
import com.favorites.utils.StringUtil;

@Service("collectService")
public class CollectServiceImpl implements CollectService {
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private CollectRepository collectRepository;
	@Autowired
	private FavoritesRepository favoritesRepository;
	@Autowired
	private FavoritesService favoritesService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private PraiseRepository praiseRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private FollowRepository followRepository;

	/**
	 * 展示收藏列表
	 * @author neo
	 * @date 2016年8月24日
	 * @param type
	 * @param userId
	 * @param pageable
	 * @return
	 */
	@Override
	public List<CollectSummary> getCollects(String type, Long userId, Pageable pageable,Long favoritesId) {
		// TODO Auto-generated method stub
		Page<CollectView> views = null;
		if ("my".equals(type)) {
			List<Long> userIds=followRepository.findMyFollowIdByUserId(userId);
			if(userIds==null || userIds.size()==0){
				views = collectRepository.findViewByUserId(userId, pageable);
			}else{
				views = collectRepository.findViewByUserIdAndFollows(userId, userIds, pageable);
			}
		}else if("myself".equals(type)){
			views = collectRepository.findViewByUserId(userId, pageable);
		} else if ("explore".equals(type)) {
			views = collectRepository.findExploreView(userId,pageable);
		} else if("others".equals(type)){
			views = collectRepository.findViewByUserIdAndType(userId, pageable, "public");
		} else if("otherpublic".equals(type)){
			views = collectRepository.findViewByUserIdAndTypeAndFavoritesId(userId, pageable, "public", favoritesId);
		}else {
			views = collectRepository.findViewByFavoritesId(Long.parseLong(type), pageable);
		}
		return convertCollect(views);
	}

	
	/**
	 * @author neo
	 * @date 2016年8月25日
	 * @param key
	 * @param userId
	 * @return
	 */
	@Override
	public List<CollectSummary> searchMy(Long userId,String key,Pageable pageable) {
		// TODO Auto-generated method stub
		Page<CollectView> views = collectRepository.searchMyByKey(userId, key,pageable);
		return convertCollect(views);
	}
	
	
	/**
	 * @author neo
	 * @date 2016年8月26日
	 * @param userId
	 * @param key
	 * @param pageable
	 * @return
	 */
	@Override
	public List<CollectSummary> searchOther(Long userId,String key,Pageable pageable) {
		// TODO Auto-generated method stub
		Page<CollectView> views = collectRepository.searchOtherByKey(userId, key, pageable);
		return convertCollect(views);
	}

	/**
	 * @author neo
	 * @date 2016年8月11日
	 * @param collects
	 * @return
	 */
	private List<CollectSummary> convertCollect(Page<CollectView> views) {
		List<CollectSummary> summarys=new ArrayList<CollectSummary>();
		for (CollectView view : views) {
			CollectSummary summary=new CollectSummary(view);
			summary.setPraiseCount(praiseRepository.countByCollectId(view.getId()));
			summary.setCommentCount(commentRepository.countByCollectId(view.getId()));
			Praise praise=praiseRepository.findByUserIdAndCollectId(view.getUserId(), view.getId());
			if(praise!=null){
				summary.setPraise(true);
			}else{
				summary.setPraise(false);
			}
			summary.setCollectTime(DateUtils.getTimeFormatText(view.getLastModifyTime()));
			summarys.add(summary);
		}
		return summarys;
	}

	/**
	 * 收藏文章
	 * @param collect
	 * @param userId
	 */
	@Transactional
	public void saveCollect(Collect collect) {
		updatefavorites(collect);
		collect.setCreateTime(DateUtils.getCurrentTime());
		collect.setLastModifyTime(DateUtils.getCurrentTime());
		collect.setIsDelete("no");
		if (StringUtils.isBlank(collect.getType())) {
			collect.setType("public");
		}
		if(StringUtils.isBlank(collect.getDescription())){
			collect.setDescription(collect.getTitle());
		}
		collectRepository.save(collect);
		noticeFriends(collect);
	}
	
	/**
	 *  修改文章
	 * @author neo
	 * @date 2016年8月24日
	 * @param newCollect
	 */
	@Transactional
	public void updateCollect(Collect newCollect) {
		Collect collect=collectRepository.findOne(newCollect.getId());
		if(collect.getFavoritesId()!=newCollect.getFavoritesId()){
			favoritesRepository.reduceCountById(collect.getFavoritesId(), DateUtils.getCurrentTime());
		}
		collect.setFavoritesId(newCollect.getFavoritesId());
		collect.setNewFavorites(newCollect.getNewFavorites());
		updatefavorites(collect);
		collect.setTitle(newCollect.getTitle());
		collect.setDescription(newCollect.getDescription());
		collect.setLogoUrl(newCollect.getLogoUrl());
		collect.setRemark(newCollect.getRemark());
		if (StringUtils.isBlank(newCollect.getType())) {
			collect.setType("public");
		}
		collect.setLastModifyTime(DateUtils.getCurrentTime());
		collectRepository.save(collect);
		noticeFriends(collect);
	}
	
	/**
	 * 验证是否重复收藏
	 * @param collect
	 * @param userId
	 * @return
	 */
	public boolean checkCollect(Collect collect){
		if(StringUtils.isNotBlank(collect.getNewFavorites())){
			// url+favoritesId+userId
			Favorites favorites = favoritesRepository.findByUserIdAndName(collect.getUserId(), collect.getNewFavorites());
			if(null == favorites){
				return true;
			}else{
				List<Collect> list = collectRepository.findByFavoritesIdAndUrlAndUserId(favorites.getId(), collect.getUrl(), collect.getUserId());
				if(null != list && list.size() > 0){
					return false;
				}else{
					return true;
				}
			}
		}else{
			List<Collect> list = collectRepository.findByFavoritesIdAndUrlAndUserId(collect.getFavoritesId(), collect.getUrl(), collect.getUserId());
			if(null != list && list.size() > 0){
				return false;
			}else{
				return true;
			}
		}
	}
	
	/**
	 * 导入收藏文章
	 */
	public void importHtml(Map<String, String> map,Long favoritesId,Long userId){
		for(Map.Entry<String, String> entry : map.entrySet()){
			List<Collect> list = collectRepository.findByFavoritesIdAndUrlAndUserId(favoritesId, entry.getKey(), userId);
			if(null != list && list.size() > 0){
				logger.info("收藏夹：" + favoritesId + "中已经存在：" + entry.getKey() + "这个文章，不在进行导入操作");
				continue;
			}
			try {
				Map<String, String> result = HtmlUtil.getCollectFromUrl(entry.getKey());
				Collect collect = new Collect();
				collect.setCharset(result.get("charset"));
				if(StringUtils.isBlank(result.get("title"))){
					collect.setTitle(entry.getValue());
				}else{
					collect.setTitle(result.get("title"));
				}
				if(StringUtils.isBlank(result.get("description"))){
					collect.setDescription(collect.getTitle());
				}else{
					collect.setDescription(result.get("description"));
				}
				collect.setFavoritesId(favoritesId);
				collect.setIsDelete("no");
				collect.setLogoUrl(result.get("logoUrl"));
				collect.setType("private");
				collect.setUrl(entry.getKey());
				collect.setUserId(userId);
				collect.setCreateTime(DateUtils.getCurrentTime());
				collect.setLastModifyTime(DateUtils.getCurrentTime());
				collectRepository.save(collect);
				favoritesRepository.increaseCountById(favoritesId, DateUtils.getCurrentTime());
			} catch (Exception e) {
				logger.error("导入存储异常：",e);
			}
		}
		
	}
	
	/**
	 * 导出到html文件
	 * @param favoritesId
	 */
	public StringBuilder exportToHtml(Long favoritesId){
		try {
			Favorites favorites = favoritesRepository.findOne(favoritesId);
			StringBuilder sb = new StringBuilder();
			List<Collect> collects = collectRepository.findByFavoritesId(favoritesId);
			StringBuilder sbc = new StringBuilder();
			for(Collect collect : collects){
				sbc.append("<DT><A HREF=\""+collect.getUrl()+"\" TARGET=\"_blank\">"+collect.getTitle()+"</A></DT>");
			}
			sb.append("<DL><P></P><DT><H3>"+favorites.getName()+"</H3><DL><P></P>"+sbc+"</DL></DT></DL>");
			return sb;
		} catch (Exception e) {
			logger.error("异常：",e);
		}
		return null;
	}

	
	/**
	 * 更新收藏夹
	 * @author neo
	 * @date 2016年8月24日
	 * @param collect
	 */
	private void  updatefavorites(Collect collect){
		if (StringUtils.isNotBlank(collect.getNewFavorites())) {
			Favorites favorites = favoritesRepository.findByUserIdAndName(collect.getUserId(), collect.getNewFavorites());
			if (null == favorites) {
				favorites = favoritesService.saveFavorites(collect.getUserId(), 1l,collect.getNewFavorites());
			} else {
				favoritesRepository.increaseCountById(favorites.getId(),DateUtils.getCurrentTime());
			}
			collect.setFavoritesId(favorites.getId());
		} else {
			favoritesRepository.increaseCountById(collect.getFavoritesId(),DateUtils.getCurrentTime());
		}
	}
	
	
	
	/**
	 * @通知好友
	 * @author neo
	 * @date 2016年8月24日
	 * @param collect
	 */
	private void noticeFriends(Collect collect){
		if (StringUtils.isNotBlank(collect.getRemark())&& collect.getRemark().indexOf("@") > -1) {
			List<String> atUsers = StringUtil.getAtUser(collect.getRemark());
			for (String str : atUsers) {
				logger.info("用户名：" + str);
				User user = userRepository.findByUserName(str);
				if (null != user) {
					// 保存消息通知
					noticeService.saveNotice(String.valueOf(collect.getId()),"at", collect.getUserId(), null);
				} else {
					logger.info("为找到匹配：" + str + "的用户.");
				}
			}
		}
	}
}
