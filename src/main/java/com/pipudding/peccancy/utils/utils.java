package com.pipudding.peccancy.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class utils {
	
	
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
	
	
}
