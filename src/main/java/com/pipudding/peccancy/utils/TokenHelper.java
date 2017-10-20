package com.pipudding.peccancy.utils;

import java.sql.Timestamp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;

import com.alibaba.fastjson.JSONObject;

public class TokenHelper {
	static Timestamp lastTokenTime = null;
	static String token = null;
	
	@Value("${token_expire}")
	static long validTime;
	
	@Value("${token_url}")
	static String url;
	
	@Value("${app_id}")
	static String appId;
	
	@Value("${app_secret}")
	static String appSecret;
	
	static public String getToken()
	{
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		long tokenLastTime = validTime;
		if(lastTokenTime != null)
		{
			tokenLastTime = (currentTimestamp.getTime() - lastTokenTime.getTime())/1000;
		}
		if(true||token == null || tokenLastTime >= validTime)
			token = requestToken();
		return token;
	}
	
	static private String requestToken()
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//HttpGet httpGet = new HttpGet(url+"?grant_type=client_credential&appid="+appId+"&secret="+appSecret);
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxcab1dccd3a31b5eb&secret=536cf5ea9aa8c0117b1919eee28cefa7");
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
	
	public static void main(String[] args) throws Exception {
		getToken();
    }
}
