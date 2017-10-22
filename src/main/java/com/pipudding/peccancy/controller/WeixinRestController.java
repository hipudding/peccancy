package com.pipudding.peccancy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pipudding.peccancy.service.WeixinService;
import com.pipudding.peccancy.utils.CustomerInfoType;
import com.pipudding.peccancy.utils.EvenInfoType;
import com.pipudding.peccancy.utils.EventType;
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
		ResultType ret = new ResultType();
		ret.setResultCode("fail");
		if(file == null)
		{
			ret.setResultText("file is null");
			return ret;
		}
		
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileName = UUID.randomUUID().toString() + suffix;
		
		if(weixinService.saveFile(file, imgPath,fileName) == false)
		{
			ret.setResultText("save file fail");
			return ret;
		}
		
		ret.setResultCode("success");
		ret.setResultText(fileName);
		
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
	
	@RequestMapping(value = "/postcustomerinfo", method = RequestMethod.POST)
	public ResultType postCustomerInfo(@RequestBody CustomerInfoType customerInfo) throws IllegalStateException, IOException {
		ResultType ret = new ResultType();
		String customerId = "hua";
		
		weixinService.saveCustomInfo(customerId, customerInfo);
		
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/getcustomerinfo", method = RequestMethod.POST)
	public CustomerInfoType getCustomerInfo(@RequestParam(value = "customerId") String customerId) throws IllegalStateException, IOException {
		CustomerInfoType customerInfo = weixinService.getCustomer(customerId);
		
		return customerInfo;
    }
	
	@RequestMapping(value = "/gettypes", method = RequestMethod.GET)
	public EventType[] getTypes() throws IllegalStateException, IOException {
		
		List<EventType> eventTypes = weixinService.getEventTypes();
		EventType[] eventTypeArray = new EventType[eventTypes.size()];
		eventTypes.toArray(eventTypeArray);
		return eventTypeArray;
    }
	
	@RequestMapping(value = "/posteventinfo", method = RequestMethod.POST)
	public ResultType postEventInfo(@RequestBody EvenInfoType eventInfo) throws IllegalStateException, IOException {
		ResultType ret = new ResultType();
		ret.setResultCode("fail");
		String customerId = "hua";
		String eventId = weixinService.createEvent(customerId,eventInfo.getType(),eventInfo.getText());
		if(eventId == "")
		{
			ret.setResultText("create event failed");
			return ret;
		}
		
		weixinService.setImageEventId(eventInfo.getImages(), eventId);
			
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
			
    }

}
