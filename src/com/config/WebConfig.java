package com.config;


import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.utils.IsLoginIntercepter;
//@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	/**
	 * 视图解析
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver()
	{
				InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	
				resolver.setViewClass(JstlView.class);
				resolver.setPrefix("/jsp/");
				resolver.setSuffix(".jsp");
	
				return resolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
//		registry.addInterceptor(new IsLoginIntercepter()).addPathPatterns("/*").excludePathPatterns("/managerLogin");
	}
	@Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(1024*1024*10);
        return resolver;
    }
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	/**
	 * 	静态资源处理
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
				configurer.enable();
	}
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//			registry.addResourceHandler("/js/**").addResourceLocations("/js/");
//	} 
	
}
