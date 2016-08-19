package com.favorites.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.favorites.comm.Const;

public class HtmlUtil {
	
    private static Logger logger = Logger.getLogger(HtmlUtil.class);
	/**
	 * @param url
	 * @return
	 */
	public static String getImge(String url){
		String logo="";
		logo=getPageImg(url);
		if(StringUtils.isBlank(logo)){
			logo=Const.default_logo;
		}
		return logo;
	}
	
	/**
	 * @param url
	 * @return
	 */
	public static String getPageImg(String url){
		String imgUrl="";
		Document doc;
		try {
			doc = Jsoup.connect(url).userAgent(Const.userAgent).get();
			Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
			if(images !=null){
				imgUrl=images.first().attr("src");
			}
			if(StringUtils.isNotBlank(imgUrl) ){
				if(imgUrl.startsWith("//")){
					imgUrl = "http:" + imgUrl;
				}else if(!imgUrl.startsWith("http") && !imgUrl.startsWith("/")){
					imgUrl=URLUtil.getDomainUrl(url) + "/" + imgUrl;
				}else if(!imgUrl.startsWith("http")){
					imgUrl=URLUtil.getDomainUrl(url)+imgUrl;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getPageImg失败,url:"+url,e);
		}
		return imgUrl;
	}
	
	public static Map<String, String> getCollectFromUrl(String url){
		Map<String, String> result = new HashMap<String, String>();
		try {
			result.put("url", url);
			Document doc = Jsoup.connect(url).userAgent(Const.userAgent).get();
			String title = doc.title();
			if(StringUtils.isNotBlank(title)){
				result.put("title", title);
			}
			String charset = doc.charset().name();
			if(StringUtils.isNoneBlank(charset)){
				result.put("charset", charset);
			}
			Elements metas = doc.head().select("meta");  
			 for (Element meta : metas) {  
		            String content = meta.attr("content");  
		            if ("description".equalsIgnoreCase(meta.attr("name"))) {  
		                result.put("description", content);
		            }  
		        }
			 	result.put("logoUrl", getImge(url));
		} catch (Exception e) {
			logger.error("文章解析出错：",e);
		} 
		return result;
	}
	
	public static List<String> importHtml(InputStream in){
		List<String> urlList = new ArrayList<>();
		try {
			Document doc = Jsoup.parse(in, "UTF-8", "");
			Elements metas = doc.select("a");  
			for (Element meta : metas) {  
	            String url = meta.attr("href");
	            if(url.startsWith("http")){
	            	urlList.add(url);
	            }
	        }
		} catch (Exception e) {
			logger.error("解析html 文件异常：",e);
		}
		return urlList;
	}
	
	public static void main(String[] args) {
//		System.out.println(getCollectFromUrl("http://www.iteye.com/"));
//		File file = new File("C:\\Users\\DingYS\\Desktop\\bookmarks_16_8_18.html");
		System.out.println(getPageImg("https://github.com/knightliao/disconf/wiki"));
	}

}
