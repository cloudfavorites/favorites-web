package com.favorites.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class URLUtil {
	
    private static Logger logger = Logger.getLogger(URLUtil.class);
    
    private static String filePath="E:\\workspaces\\temp\\favorites\\bin\\static\\file\\logo\\";
    
    private static String localPath="http://localhost:8080/file/logo/";

    
	public static synchronized boolean isConnect(String urlStr) {  
        int counts = 0;  
        if (urlStr == null || urlStr.length() <= 0) {                         
            return false;                   
        }  
        while (counts < 3) {  
            try {  
            	URL url = new URL(urlStr);  
                HttpURLConnection   con = (HttpURLConnection) url.openConnection();  
                int state = con.getResponseCode();  
                if (state == 200) {  
                   return true;
                }  
                break;  
            }catch (Exception ex) {  
                counts++;   
                continue;  
            }  
        }  
        return false;  
    }  
	
	public static String getDomainUrl(String urlStr){
		String domainUrl=urlStr;
		try {
		     URL url = new URL(urlStr);
		     domainUrl=url.getProtocol()+"://"+url.getAuthority();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDomainUrl is erro,url :"+urlStr, e);
		}
		return domainUrl;
	}
	
	
	public static String getHost(String urlStr){
		String host=urlStr;
		try {
		     URL url = new URL(urlStr);
		     host=url.getHost();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getHost is erro,url :"+urlStr, e);
		}
		return host;
	}
	
	
	/**
	 *  下载文件夹到本地
	 * @param imgsrc 图片地址
	 * @param filepath 存储图片地址
	 * 
	 * */
	public static String downImgs(String imgsrc){
		String filename=getHost(imgsrc)+".png";
		try {
			filename=getHost(imgsrc)+"."+imgsrc.substring(imgsrc.lastIndexOf("/")+1);
			File files = new File(filePath);
			if(!files.exists()){
				files.mkdirs();
			}
			URL url = new URL(imgsrc);
			HttpURLConnection uc=(HttpURLConnection) url.openConnection();
			InputStream is=uc.getInputStream();
			File file=new File(filePath+filename);
			if(file.exists()){
				return localPath+filename;
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
		return localPath+filename;
	}
	
	public static void main(String[] args) {
		System.out.println(downImgs("http://common.cnblogs.com/favicon.ico"));
	}

}
