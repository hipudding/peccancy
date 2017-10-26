package com.pipudding.peccancy.interceotor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pipudding.peccancy.utils.TokenHelper;

public class WeixinInterceptor implements HandlerInterceptor{
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		
		HttpSession session = request.getSession(true);
		Object customerId = session.getAttribute("customerId");
		
		if(customerId != null)
			return true;
		
		String code = request.getParameter("code");
		if(customerId == null&&code == null)
		{
			String url = request.getRequestURL().toString();
			String encodeUrl = URLEncoder.encode(url, "UTF-8");
			response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxcab1dccd3a31b5eb&redirect_uri="+encodeUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
			return false;
		}
		
		String openid = TokenHelper.getCustomerId(code);
		
		if(openid == null || code.length() == 0)
			return false;
		
		session.setAttribute("customerId", openid);
		response.sendRedirect(request.getRequestURL().toString());
		return false;
		
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
