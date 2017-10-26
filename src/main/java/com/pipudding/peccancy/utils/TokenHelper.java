package com.pipudding.peccancy.utils;

import java.sql.Timestamp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class TokenHelper {
	static Timestamp lastTokenTime = null;
	static Timestamp lastApiTime = null;
	static String token = null;
	static String jsapi = null;
	
	static long validTime = 7200;
	
	static final String appId = "wxcab1dccd3a31b5eb";
	
	static final String appSecret = "536cf5ea9aa8c0117b1919eee28cefa7";
	
	static public String getToken()
	{
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		long tokenLastTime = validTime;
		if(lastTokenTime != null)
		{
			tokenLastTime = (currentTimestamp.getTime() - lastTokenTime.getTime())/1000;
		}
		if(token == null || tokenLastTime >= validTime)
			token = requestToken();
		return token;
	}
	
	static public String getApi(String access_token)
	{
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		long apiLastTime = validTime;
		if(lastApiTime != null)
		{
			apiLastTime = (currentTimestamp.getTime() - lastApiTime.getTime())/1000;
		}
		if(jsapi == null || apiLastTime >= validTime)
			jsapi = getjsToken(access_token);
		return jsapi;
	}
	
	static private String requestToken()
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//HttpGet httpGet = new HttpGet(url+"?grant_type=client_credential&appid="+appId+"&secret="+appSecret);
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret);
		HttpResponse response = null;  
	    try{
	        response = httpClient.execute(httpGet);
	    }catch (Exception e) {} 
	    //TODO 添加统一log管理
	    String curentToken = null;
	    try{
	        HttpEntity entity = response.getEntity();
	        String result=EntityUtils.toString(entity,"UTF-8");
	        JSONObject jsonResult = JSONObject.parseObject(result);
	        curentToken = jsonResult.getString("access_token");
	    }catch (Exception e) {} 
	    
	    return curentToken;
	}
	
	public static String getCustomerId(String code)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code");
		HttpResponse response = null;  
		try{
			response = httpClient.execute(httpGet);
		}catch (Exception e) {} 
		//TODO 添加统一log管理
		String customerId = null;
		try{
			HttpEntity entity = response.getEntity();
			String result=EntityUtils.toString(entity,"UTF-8");
			JSONObject jsonResult = JSONObject.parseObject(result);
			customerId = jsonResult.getString("openid");
		}catch (Exception e) {} 
    
		return customerId;
	}
	
	public static String getjsToken(String accessToken)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi");
		HttpResponse response = null;  
		try{
			response = httpClient.execute(httpGet);
		}catch (Exception e) {} 
		//TODO 添加统一log管理
		String ticket = null;
		try{
			HttpEntity entity = response.getEntity();
			String result=EntityUtils.toString(entity,"UTF-8");
			JSONObject jsonResult = JSONObject.parseObject(result);
			ticket = jsonResult.getString("ticket");
		}catch (Exception e) {} 
    
		return ticket;
	}

}
