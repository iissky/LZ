package com.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class BasePathListener implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String basePath = sce.getServletContext().getInitParameter("basePath");
		 sce.getServletContext().setAttribute("basePath", basePath);
	}
}
