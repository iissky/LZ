package com.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
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
