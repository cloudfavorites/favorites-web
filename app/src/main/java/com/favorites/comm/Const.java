package com.favorites.comm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Const {
	
	public static String BASE_PATH;
	
	public static String LOGIN_SESSION_KEY = "Favorites_user";
	
	public static String PASSWORD_KEY = "@#$%^&*()OPG#$%^&*(HG";

	public static String DES3_KEY = "9964DYByKL967c3308imytCB";
	
	public static String default_logo="img/logo.jpg";
	
	public static String userAgent="Mozilla";
	
	public static String default_Profile=BASE_PATH+"/img/logo.jpg";
	
	public static String LAST_REFERER = "LAST_REFERER";

	public static int COOKIE_TIMEOUT= 30*24*60*60;

	
	  @Autowired(required = true)
	  public void setBasePath(@Value("${favorites.base.path}")String basePath) {
		  Const.BASE_PATH = basePath;
	  }
	
	
}
