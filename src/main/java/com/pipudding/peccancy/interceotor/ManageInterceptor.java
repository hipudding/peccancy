package com.pipudding.peccancy.interceotor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ManageInterceptor implements HandlerInterceptor{
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		
		HttpSession session = request.getSession(true);
		Object userId = session.getAttribute("userId");
		

		if(userId == null)
		{
			String serverAddr = request.getRequestURL().toString();
			serverAddr = serverAddr.substring(0,serverAddr.indexOf('/', 8));
			response.sendRedirect(serverAddr+"/manage/login");
			return false;
		}
		
		return true;
		
    }

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
