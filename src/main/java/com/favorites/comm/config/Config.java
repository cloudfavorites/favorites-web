package com.favorites.comm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config{
	
	@Value("${favorites.file.save.path}")
	public static String basePath;
	@Value("${favorites.file.save.path}")
	private String savePath;
	@Value("${favorites.file.access.url}")
	private String accessUrl;
	
	public static String getBasePath() {
		return basePath;
	}
	public static void setBasePath(String basePath) {
		Config.basePath = basePath;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getAccessUrl() {
		return accessUrl;
	}
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
	
	

}