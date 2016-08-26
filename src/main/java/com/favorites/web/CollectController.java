package com.favorites.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectRepository;
import com.favorites.domain.CollectSummary;
import com.favorites.domain.Praise;
import com.favorites.domain.PraiseRepository;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.service.CollectService;
import com.favorites.service.FavoritesService;
import com.favorites.utils.DateUtils;
import com.favorites.utils.HtmlUtil;

@RestController
@RequestMapping("/collect")
public class CollectController extends BaseController{
	@Autowired
	private CollectRepository collectRepository;
	@Resource
	private FavoritesService favoritesService;
	@Resource
	private CollectService collectService;
	@Autowired
	private PraiseRepository praiseRepository;
	
	/**
	 * 文章收集
	 * @param collect
	 * @return
	 */
	@RequestMapping(value = "/collect", method = RequestMethod.POST)
	public Response collect(Collect collect) {
		logger.info("collect begin, param is " + collect);
		try {
			collect.setUserId(getUserId());
			if(collectService.checkCollect(collect)){
				if(collect.getId()==null){
					collectService.saveCollect(collect);
				}else{
					collectService.updateCollect(collect);
				}
			}else{
				return result(ExceptionMsg.CollectExist);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("collect failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	
	
	/**
	 * @author neo
	 * @date 2016年8月25日
	 * @param page
	 * @param size
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/standard/{type}")
	public List<CollectSummary> standard(@RequestParam(value = "page", defaultValue = "0") Integer page,
	        @RequestParam(value = "size", defaultValue = "6") Integer size,@PathVariable("type") String type) {
		Sort sort = new Sort(Direction.DESC, "id");
	    Pageable pageable = new PageRequest(page, size, sort);
	    List<CollectSummary> collects=collectService.getCollects(type,getUserId(), pageable);
		return collects;
	}
	
	
	/**
	 * @author neo
	 * @date 2016年8月25日
	 * @param page
	 * @param size
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/simple/{type}")
	public List<CollectSummary> simple(@RequestParam(value = "page", defaultValue = "0") Integer page,
	        @RequestParam(value = "size", defaultValue = "20") Integer size,@PathVariable("type") String type) {
		Sort sort = new Sort(Direction.DESC, "id");
	    Pageable pageable = new PageRequest(page, size, sort);
	    List<CollectSummary> collects=collectService.getCollects(type,getUserId(), pageable);
		return collects;
	}
	
	/**
	 * @author neo
	 * @date 2016年8月24日
	 * @param id
	 * @param type
	 */
	@RequestMapping(value="/changePrivacy/{id}/{type}")
	public Response changePrivacy(@PathVariable("id") long id,@PathVariable("type") String type) {
		collectRepository.modifyById(type, id);
		return result();
	}
	
	/**
	 * like and unlike
	 * @author neo
	 * @date 2016年8月24日
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/like/{id}")
	public Response like(@PathVariable("id") long id) {
		Praise praise=praiseRepository.findByUserIdAndCollectId(getUserId(), id);
		if(praise==null){
			Praise newPraise=new Praise();
			newPraise.setUserId(getUserId());
			newPraise.setCollectId(id);
			newPraise.setCreateTime(DateUtils.getCurrentTime());
			praiseRepository.save(newPraise);
		}else{
			praiseRepository.delete(praise.getId());
		}
		return result();
		
	}
	
	
	/**
	 * @author neo
	 * @date 2016年8月24日
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	public Response delete(@PathVariable("id") long id) {
		collectRepository.deleteById(id);
		return result();
	}
	
	
	/**
	 * @author neo
	 * @date 2016年8月24日
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/detail/{id}")
	public Collect detail(@PathVariable("id") long id) {
		Collect collect=collectRepository.findOne(id);
		return collect;
	}
	

	/**
	 * 导入收藏夹
	 * @param path
	 */
	@RequestMapping("/import")
	public void importCollect(@RequestParam("htmlFile") MultipartFile htmlFile,Long favoritesId){
		logger.info("path:" + htmlFile.getOriginalFilename());
		if(null == favoritesId){
			logger.info("获取导入收藏夹ID失败："+ favoritesId);
			return;
		}
		try {
			Map<String, String> map = HtmlUtil.importHtml(htmlFile.getInputStream());
			if(null == map || map.isEmpty()){
				logger.info("未获取到url连接");
				return ;
			}
			collectService.importHtml(map, favoritesId, getUserId());
		} catch (Exception e) {
			logger.error("导入html异常:",e);
		}
	}
	
	/**
	 * 导出收藏夹
	 * @param name
	 * @return
	 */
	@RequestMapping("/export")
	public void export(String favoritesId,HttpServletResponse response){
		logger.info("favoritesId:" + favoritesId);
		if(StringUtils.isNotBlank(favoritesId)){
			try {
				String[] ids = favoritesId.split(",");
				String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String fileName= "favorites_" + date + ".html";
				StringBuilder sb = new StringBuilder();
				for(String id : ids){
					try {
						sb = sb.append(collectService.exportToHtml(Long.parseLong(id)));
					} catch (Exception e) {
						logger.error("异常：",e);
					}
				}
				sb = HtmlUtil.exportHtml("云收藏夹", sb);
				response.setCharacterEncoding("UTF-8");  
				response.setHeader("Content-disposition","attachment; filename=" + fileName);
				response.getWriter().print(sb);
			} catch (Exception e) {
				logger.error("异常：",e);
			}
		}
	}
}