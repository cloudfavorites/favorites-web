package com.favorites.utils;

import com.favorites.comm.Const;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {

	public static Logger logger =  LoggerFactory.getLogger(HtmlUtil.class);
	/**
	 * @param url
	 * @return
	 */
	public static String getImge(String url){
		String logo="";
		logo=getPageImg(url);
		if(StringUtils.isBlank(logo) || logo.length()>300){
			logo=Const.BASE_PATH + Const.default_logo;
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
			for(Element image : images){
				imgUrl=image.attr("src");
				if(StringUtils.isNotBlank(imgUrl) ){
					if(imgUrl.startsWith("//")){
						imgUrl = "http:" + imgUrl;
					}else if(!imgUrl.startsWith("http") && !imgUrl.startsWith("/")){
						imgUrl=URLUtil.getDomainUrl(url) + "/" + imgUrl;
					}else if(!imgUrl.startsWith("http")){
						imgUrl=URLUtil.getDomainUrl(url)+imgUrl;
					}
				}
				// 判断图片大小
				String fileUrl = download(imgUrl);
				if(fileUrl!=null){
					File picture = new File(fileUrl);
					FileInputStream in = new FileInputStream(picture);
					BufferedImage sourceImg = ImageIO.read(in);
					String weight = String.format("%.1f",picture.length()/1024.0);
					int width = sourceImg.getWidth();
					int height = sourceImg.getHeight();
					// 删除临时文件
					if(picture.exists()){
						in.close();
						picture.delete();
					}
					if(Double.parseDouble(weight) <= 0 || width <=0 || height <= 0){
						logger.info("当前图片大小为0，继续获取图片链接");
						imgUrl="";
					}else{
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getPageImg  失败,url:"+url,e.getMessage());
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
			if(StringUtils.isBlank(charset)){
				Elements eles = doc.select("meta[http-equiv=Content-Type]");
				Iterator<Element> itor = eles.iterator();
				while (itor.hasNext()){
					charset = matchCharset(itor.next().toString().toUpperCase());
				}
			}
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
			 	//result.put("logoUrl", getImge(url));
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
		Map<String, String> map = new HashMap<String, String>();
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
			Map<String, List<Map>> resultMap = new HashMap<String, List<Map>>();
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
		Map<String, Map> favoritesMap = new HashMap<String, Map>();
		Map<String, String> urlMap = new HashMap<String, String>();
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
				mapList = new ArrayList<Map>();
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

	public static String matchCharset(String content) {
		Pattern p = Pattern.compile("(?<=charset=)(.+)(?=\")");
		Matcher m = p.matcher(content);
		if (m.find())
			return m.group();
		return null;
	}

	// 图片下载
	private static String download(String url) {
			try {
				String imageName = url.substring(url.lastIndexOf("/") + 1,
						url.length());

				URL uri = new URL(url);
				InputStream in = uri.openStream();
				String dirName = "static/temp/";
				File dirFile = new File(dirName);
				if(!dirFile.isDirectory()){
					dirFile.mkdir();
				}
				String fileName = dirName+imageName;
				File file = new File(dirFile,imageName);
				FileOutputStream fo = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int length = 0;
				while ((length = in.read(buf, 0, buf.length)) != -1) {
					fo.write(buf, 0, length);
				}
				in.close();
				fo.close();
				return fileName;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}

	/**
	 * 判断链接是否失效
	 * @param url
	 * @return
	 */
	public static boolean isConnect(String url){
		HttpURLConnection connection;
		int counts = 0;
		boolean flag = false;
		if (url == null || url.length() <= 0) {
			return flag;
		}
		while (counts < 5) {
			try {
				connection = (HttpURLConnection) new URL(url).openConnection();
				int state = connection.getResponseCode();
				if (state == 200) {
					flag = true;
				}
				break;
			} catch (Exception e) {
				counts++;
				continue;
			}
		}
		return flag;
	}

}
