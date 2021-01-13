package com.endProject.footballClubApplication;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
	// change path where to search for static content
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	String PATH = "file:///C:/Users/taken305/Downloads/JAVA_SPRING_BOOT/footballClubApplication/uploads/";
        registry
        .addResourceHandler("/resources/**","/uploads/**")
        .addResourceLocations("/resources/",PATH);
    }
}
