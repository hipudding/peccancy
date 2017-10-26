package com.pipudding.peccancy.service;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pipudding.peccancy.type.JsapiType;
import com.pipudding.peccancy.utils.TokenHelper;
import com.pipudding.peccancy.utils.utils;

@Service
@Transactional
public class WeixinService {
	
	public JsapiType getJsapi(String url)
	{
		String noncestr = utils.getnonceStr();
		String timestamp = String.valueOf(System.currentTimeMillis()/1000); 
		String access_token = TokenHelper.getToken();
		String jsapi_ticket = TokenHelper.getApi(access_token);
		
		String signature = utils.getSignature(noncestr, jsapi_ticket, timestamp, url);
		JsapiType api = new JsapiType();
		api.setNoncestr(noncestr);
		api.setTimestamp(timestamp);
		api.setJsapi_ticket(signature);
		api.setAppid("wxcab1dccd3a31b5eb");
		
		return api;
	}
	
	public String linkWeixin(String myToken,String timestamp,String nonce)
	{
		String[] params = new String[] {myToken,timestamp,nonce};
		Arrays.sort(params);
		String rawString = String.join("", params);
		String encryptString = DigestUtils.sha1Hex(rawString);
		return encryptString;
	}
	
}
