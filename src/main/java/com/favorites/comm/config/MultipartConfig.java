package com.favorites.comm.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
*@ClassName: MultipartConfig
*@Description: 
*@author YY 
*@date 2016年8月26日  下午2:39:37
*@version 1.0
*/

@Configuration
public class MultipartConfig {
	
	@Bean  
	public MultipartConfigElement multipartConfigElement() {  
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("50MB");
		factory.setMaxRequestSize("50MB");  
		return factory.createMultipartConfig();  
	} 

}
