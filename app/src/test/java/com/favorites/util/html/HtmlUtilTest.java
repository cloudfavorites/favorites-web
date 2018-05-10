package com.favorites.util.html;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.favorites.utils.HtmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.favorites.comm.Const;
import com.favorites.schedule.ScheduledTasks;
import com.favorites.utils.URLUtil;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HtmlUtilTest {

	public static Logger logger =  LoggerFactory.getLogger(HtmlUtilTest.class);
   @Autowired
	ScheduledTasks tasks;

	   
   @Test
   public void test() throws Exception {
//	   String imgsrc="http://insights.thoughtworkers.org/wp-content/uploads/2016/08/%E5%A4%B4%E5%9B%BE-1.jpg";
//	   downImage(imgsrc);
//	   String url="https://support.microsoft.com/zh-cn/products/windows#!/zh-cn/products/windows?os=windows-7";
//	   boolean b = HtmlUtil.isConnect(url);
//	   System.out.println("========================="+b);

   }
   
   
   public static void downImage(String imgsrc){
		String filename=URLUtil.getHost(imgsrc)+".png";
		try {
			filename=URLUtil.getHost(imgsrc)+"."+imgsrc.substring(imgsrc.lastIndexOf("/")+1);
			File files = new File("");
			if(!files.exists()){
				files.mkdirs();
			}
			URL url = new URL(imgsrc);
			HttpURLConnection uc=(HttpURLConnection) url.openConnection();
			InputStream is=uc.getInputStream();
			File file=new File(""+filename);
			if(file.exists()){
				System.out.println(""+filename); ;
			}
			FileOutputStream fos=new FileOutputStream(file);
			int line=-1;
			while((line=is.read())!=-1){
				fos.write(line);
			}
			is.close();
			fos.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("downImgs is erro,imgsrc :"+imgsrc, e);
		}
		System.out.println(""+filename);
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
			doc = Jsoup.connect(url).userAgent(Const.userAgent).get();
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
	 * get title from website
	 * @param url
	 * @return
	 */
	public static String getTitle(String url){
		String title="";
		Document doc;
		try {
			doc = Jsoup.connect(url).userAgent(Const.userAgent).get();
			title = doc.title();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取title失败,url:"+url,e);
		}
		return title;
	}
   

}