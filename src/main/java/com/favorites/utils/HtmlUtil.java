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
	
	/**
	 * 一层，只输出url及对应的title或描述
	 * @param in
	 * @return
	 */
	public static Map<String, String> parseHtmlOne(InputStream in){
		Map<String, String> map = new HashMap<>();
		try {
			Document doc = Jsoup.parse(in, "UTF-8", "");
			Elements metas = doc.select("a");  
			for (Element meta : metas) {  
	            String url = meta.attr("href");
	            if(url.startsWith("http")){
	            	map.put(url, meta.text());
	            }
	        }
		} catch (Exception e) {
			logger.error("解析html 文件异常：",e);
		}
		return map;
	}
	
	/**
	 * 两层（文件夹<url+title或描述>）
	 * @param in
	 * @return
	 */
	public static Map<String, Map<String, String>> parseHtmlTwo(InputStream in){
		Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>();
		try {
			Document doc = Jsoup.parse(in, "UTF-8", "");  
			Elements metasdts = doc.select("dt");
			for(Element dt : metasdts){
				String favoritesName = "";
				Elements dtcs = dt.children();
				Map<String, String> map = new HashMap<String, String>();
				for(Element dt1 : dtcs){
					if("h3".equalsIgnoreCase(dt1.nodeName())){
						favoritesName = dt1.text();
					}else if("dl".equalsIgnoreCase(dt1.nodeName())){
						Elements dts = dt1.children();
						for(Element dt11 : dts){
							if("dt".equals(dt11.nodeName())){
								if("a".equals(dt11.child(0).nodeName())){
									String url = dt11.child(0).attr("href");
						            if(url.startsWith("http")){
						            	map.put(url, dt11.child(0).text());
						            }
								}
							}
						}
					}
				}
				if(StringUtils.isNotBlank(favoritesName) && map.size() > 0){
					resultMap.put(favoritesName, map);
				}
			}
		} catch (Exception e) {
			logger.error("解析html文件异常：",e);
		}
		return resultMap;
	}
	
	/**
	 * 按照文档结构输出(TODO)
	 */
	public static void importHtmlMore(File in){
		try {
			Document doc = Jsoup.parse(in, "UTF-8", "");  
			Elements bodys = doc.child(0).children();
			Map<String, List<Map>> resultMap = new HashMap<>();
			for(Element body : bodys){
				if("body".equalsIgnoreCase(body.nodeName())){
					Elements dls = body.children();
					for(Element dl : dls){
						if("dl".equalsIgnoreCase(dl.nodeName())){
							resultMap = parseElements(dl,resultMap);
							System.out.println("resultMap:" + resultMap);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("解析html文件异常：",e);
		}
		
	}
	
	public static Map<String, List<Map>> parseElements(Element element,Map<String, List<Map>> resultMap){
		Map<String, Map> favoritesMap = new HashMap<>();
		Map<String, String> urlMap = new HashMap<>();
		String favoritesName = "";
		Elements dts = element.children();
		for(Element dt : dts){
			if("dt".equalsIgnoreCase(dt.nodeName())){
				Elements dtas = dt.children();
				for(Element a : dtas){
					if("a".equalsIgnoreCase(a.nodeName())){
						String url = a.attr("href");
			            if(url.startsWith("http")){
			            	urlMap.put(url, a.text());
			            	favoritesName=a.parent().parent().parent().child(0).text();
			            }
					}else if("dl".equalsIgnoreCase(a.nodeName())){
						resultMap =  parseElements(a,resultMap);
					}
				}
				
			}
		}
		if(StringUtils.isNotBlank(favoritesName)){
			favoritesMap.put(favoritesName, urlMap);
		}
		List<Map> mapList = null;
		Element parment = element.parent().parent().parent().child(0);
		if("h3".equalsIgnoreCase(parment.nodeName())){
			String name = parment.text();
			if(resultMap.containsKey(name)){
				mapList = resultMap.get(name);
				mapList.add(favoritesMap);
			}else{
				mapList = new ArrayList<>();
				mapList.add(favoritesMap);
			}
			resultMap.put(name, mapList);
		}
		return resultMap;
	}
	
	public static StringBuilder exportHtml(String title,StringBuilder body){
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML>");
		sb.append("<HEAD>");
		sb.append("<TITLE>"+title+"</TITLE>"); 
		sb.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=utf-8\" />"); 
		sb.append("</HEAD>");
		sb.append("<BODY><H1>"+title+"</H1>");
		sb.append(body);
		sb.append("</BODY>");
		
		return sb;
	}
	
	public static void main(String[] args) {
//		System.out.println(getCollectFromUrl("http://www.iteye.com/"));
//		System.out.println(getPageImg("https://github.com/knightliao/disconf/wiki"));
		File file = new File("C:\\Users\\DingYS\\Desktop\\bookmarks_16_8_25.html");
		importHtmlMore(file);
	}

}
