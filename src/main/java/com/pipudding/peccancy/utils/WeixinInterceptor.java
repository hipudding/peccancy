package com.pipudding.peccancy.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class WeixinInterceptor implements HandlerInterceptor{
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		
		HttpSession session = request.getSession(true);
		/*if(session.getAttribute("customerId") == null)
		{
			String url = request.getRequestURI();
			response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxcab1dccd3a31b5eb&redirect_uri="+url+"&response_type=code&scope=SCOPE&state=STATE#wechat_redirect");
			return false;
		}*/
		
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
