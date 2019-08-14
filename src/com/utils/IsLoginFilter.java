package com.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pojo.LzManager;
import com.pojo.LzUserinfo;

@WebFilter("/jsp/*")
public class IsLoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		LzManager user =  (LzManager) req.getSession().getAttribute("user");
		String basePath = req.getServletContext().getInitParameter("basePath");
		if(user==null){
			resp.sendRedirect(basePath+"login.jsp");
		}
		arg2.doFilter(arg0, arg1);
	}

}
