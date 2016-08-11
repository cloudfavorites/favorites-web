package com.favorites.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
			if(StringUtils.isNotBlank(imgUrl) && !imgUrl.startsWith("http")){
				imgUrl=URLUtil.getDomainUrl(url)+imgUrl;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getPageImg失败,url:"+url,e);
		}
		return imgUrl;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getPageImg("http://www.cnblogs.com/"));
	}

}
