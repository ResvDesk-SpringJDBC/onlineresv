package com.telappoint.onlineresv.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author Murali
 * This interceptor check the session validity before method invocation (other than login) and forward to login page if session expired.
 */

public class SessionInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler)  throws Exception {{		
		HandlerMethod method = (HandlerMethod) handler;		
		if(!method.getMethod().getName().equals("getLoginPage") && !method.getMethod().getName().equals("sessionExpired")){
			HttpSession session = request.getSession(false);
			if(session == null) {
				response.sendRedirect(request.getContextPath()+"/session-expired.html");				
				return false;
			}
		}		
		return true;
	}
	}
}
