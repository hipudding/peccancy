package com.pipudding.peccancy;

import java.io.UnsupportedEncodingException;

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
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		getUserInfo("oxYX7s5DHumeyEqxEmuItKKew1Y4");
    }
	
}
