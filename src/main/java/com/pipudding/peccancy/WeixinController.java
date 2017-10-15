package com.pipudding.peccancy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeixinController {
	
	@Value("${my_token}")
	String myToken;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    String weixinIndentity(String echostr,String timestamp,String nonce,String signature) {
		//TODO 参数需要校验
		String[] params = new String[] {myToken,timestamp,nonce};
		Arrays.sort(params);
		String rawString = String.join("", params);
		String encryptString = DigestUtils.sha1Hex(rawString);
		if(encryptString.equals(signature))
			return echostr;
        return "";
    }
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	String weixinMessage(HttpServletRequest request) throws IOException
	{
		InputStream inputStream = request.getInputStream();
		Scanner s = new Scanner(inputStream).useDelimiter("\\A");
		String result = s.hasNext() ? s.next() : "";
		return "123";
	}
	
}
