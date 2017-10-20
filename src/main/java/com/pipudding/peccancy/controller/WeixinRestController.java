package com.pipudding.peccancy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pipudding.peccancy.service.WeixinService;
import com.pipudding.peccancy.utils.ResultType;

@RestController
public class WeixinRestController {
	@Value("${img_path}")
	String imgPath;
	
	@Value("${my_token}")
	String myToken;
	
	@Autowired
	WeixinService weixinService;
	
	
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
	
	@RequestMapping(value = "/imgupload", method = RequestMethod.POST)
	public ResultType imageUpload(@RequestParam(value = "fileVal") MultipartFile file) throws IllegalStateException, IOException {	
		String resultText = "";
		if(file == null)
			resultText = "file is null";
		
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileName = UUID.randomUUID().toString() + suffix;
		
		if(weixinService.saveFile(file, imgPath,fileName) == false)
			resultText = "save file fail";

		//TODO 参数需要校验
		ResultType ret = new ResultType();
		
		if(!resultText.equals(""))
		{
			ret.setResultCode("fail");
			ret.setResultText(resultText);
		}
		else
		{
			ret.setResultCode("success");
			ret.setResultText(fileName);
		}
		
		return ret;
    }
	
	@RequestMapping(value = "/imgdelete", method = RequestMethod.POST)
	public ResultType imageDelete(@RequestParam(value = "ID") String fileName) throws IllegalStateException, IOException {
		ResultType ret = new ResultType();
		weixinService.deleteImage(imgPath,fileName);
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
   

}
