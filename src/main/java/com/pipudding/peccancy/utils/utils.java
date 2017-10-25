package com.pipudding.peccancy.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class utils {
	public static void getUserInfo(String openid) throws UnsupportedEncodingException
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String token = TokenHelper.getToken();
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openid+"&lang=zh_CN");
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
	    }catch (Exception e) {} 
	    
	}
	
	public static String getSignature(String noncestr,String ticket,String timestamp,String url)
	{
		String raw = "jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		String encryptString = DigestUtils.sha1Hex(raw);
		
        return encryptString;
	}
	
	private static int getRandom(int count) 
	{
	    return (int) Math.round(Math.random() * (count));
	}
	
	public static String getnonceStr()
	{
		int length = 16;
		StringBuffer sb = new StringBuffer();
		String source = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";  
	    int len = source.length();
	    for (int i = 0; i < length; i++) {
	        sb.append(source.charAt(getRandom(len-1)));
	    }
	    return sb.toString();
	}
	
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		getUserInfo("oxYX7s5DHumeyEqxEmuItKKew1Y4");
    }
	
}
