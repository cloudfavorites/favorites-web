package com.favorites.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlUtil {
	
    private static Logger logger = Logger.getLogger(HtmlUtil.class);
    private static String userAgent="Mozilla";
    
	/**
	 * get title from website
	 * @param url
	 * @return
	 */
	public static String getTitle(String url){
		String title="";
		Document doc;
		try {
			doc = Jsoup.connect(url).userAgent(userAgent).get();
			title = doc.title();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取title失败,url:"+url,e);
		}
		return title;
	}
	
	
	/**
	 * get logo from website
	 * @param url
	 * @return
	 */
	public static String getLogo(String url){
		String logo="";
		logo=getPageLogo(url);
		if(StringUtils.isBlank(logo)){
			logo=getPageLogo(URLUtil.getDomainUrl(url));
		}
		if(StringUtils.isBlank(logo)){
			logo=getPageImg(url);
		}
		
		if(StringUtils.isNotBlank(logo)){
			logo=URLUtil.downImgs(logo);
		}else if(StringUtils.isBlank(logo)){
			logo="http://localhost:8080/file/logo.jpg";
		}
		return logo;
	}
	
	
	/**
	 * get logo from page
	 * @param url
	 * @return
	 */
	public static String getPageLogo(String url){
		String logo="";
		Document doc;
		try {
			doc = Jsoup.connect(url).userAgent(userAgent).get();
			Element element = doc.head().select("link[rel=shortcut icon]").first();
			if(element==null){
				element = doc.head().select("link[rel=icon]").first();
			}
			if(element!=null){
				logo=element.attr("href");
			}
			
			
			if(StringUtils.isNotBlank(logo)){
				if(URLUtil.isConnect("http:"+logo) && !logo.startsWith("http")){
					logo="http:"+logo;
				}else if(URLUtil.isConnect(url+logo)){
					logo=URLUtil.getDomainUrl(url)+logo;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取logo失败,url:"+url,e);
		}
		return logo;
	}
	
	/**
	 * @param url
	 * @return
	 */
	public static String getPageImg(String url){
		String logo="";
		Document doc;
		try {
			doc = Jsoup.connect(url).userAgent(userAgent).get();
			Elements elements = doc.select("a");
			for(Element element:elements){
				logo=element.attr("src");
				if(StringUtils.isNotBlank(logo)){
					break;
				}
			}
			if(StringUtils.isNotBlank(logo)){
				if(URLUtil.isConnect("http:"+logo) && !logo.startsWith("http")){
					logo="http:"+logo;
				}else if(URLUtil.isConnect(url+logo)){
					logo=URLUtil.getDomainUrl(url)+logo;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getPageImg失败,url:"+url,e);
		}
		return logo;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getPageLogo("http://insights.thoughtworkers.org/"));
	}

}
