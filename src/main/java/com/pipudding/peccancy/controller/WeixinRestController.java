package com.pipudding.peccancy.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pipudding.peccancy.service.EventService;
import com.pipudding.peccancy.service.UserService;
import com.pipudding.peccancy.service.WeixinService;
import com.pipudding.peccancy.type.CustomerInfoType;
import com.pipudding.peccancy.type.EvenInfoType;
import com.pipudding.peccancy.type.EventType;
import com.pipudding.peccancy.type.JsapiType;
import com.pipudding.peccancy.type.ResultType;

@RestController
public class WeixinRestController {
	@Value("${img_path}")
	String imgPath;
	
	@Value("${my_token}")
	String myToken;
	
	@Autowired
	WeixinService weixinService;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    String weixinIndentity(String echostr,String timestamp,String nonce,String signature) {
		//TODO 参数需要校验
		
		String encryptString = weixinService.linkWeixin(myToken, timestamp, nonce);
		
		if(encryptString.equals(signature))
			return echostr;
        return "";
    }
	
	@RequestMapping(value = "/weixin/imgupload", method = RequestMethod.POST)
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
		
		if(eventService.saveFile(file, imgPath,fileName) == false)
		{
			ret.setResultText("save file fail");
			return ret;
		}
		
		ret.setResultCode("success");
		ret.setResultText(fileName);
		
		return ret;
    }
	
	@RequestMapping(value = "/weixin/imgdelete", method = RequestMethod.POST)
	public ResultType imageDelete(@RequestParam(value = "ID") String fileName) throws IllegalStateException, IOException {
		ResultType ret = new ResultType();
		eventService.deleteImage(imgPath,fileName);
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/weixin/postcustomerinfo", method = RequestMethod.POST)
	public ResultType postCustomerInfo(@RequestBody CustomerInfoType customerInfo,HttpServletRequest request) throws IllegalStateException, IOException {
		ResultType ret = new ResultType();
		String customerId = request.getSession(true).getAttribute("customerId").toString();
		
		userService.saveCustomInfo(customerId, customerInfo);
		
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/weixin/getcustomerinfo", method = RequestMethod.POST)
	public CustomerInfoType getCustomerInfo(@RequestParam(value = "customerId") String customerId) throws IllegalStateException, IOException {
		CustomerInfoType customerInfo = userService.getCustomer(customerId);
		
		return customerInfo;
    }
	
	@RequestMapping(value = "/weixin/gettypes", method = RequestMethod.GET)
	public EventType[] getTypes() throws IllegalStateException, IOException {
		
		List<EventType> eventTypes = eventService.getEventTypes();
		EventType[] eventTypeArray = new EventType[eventTypes.size()];
		eventTypes.toArray(eventTypeArray);
		return eventTypeArray;
    }
	
	@RequestMapping(value = "/weixin/posteventinfo", method = RequestMethod.POST)
	public ResultType postEventInfo(@RequestBody EvenInfoType eventInfo,HttpServletRequest request) throws IllegalStateException, IOException {
		ResultType ret = new ResultType();
		ret.setResultCode("fail");
		String customerId = request.getSession(true).getAttribute("customerId").toString();
		String eventId = eventService.createEvent(customerId,eventInfo);
		if(eventId == "")
		{
			ret.setResultText("create event failed");
			return ret;
		}
		
		eventService.setImageEventId(eventInfo.getImages(), eventId);
			
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/weixin/jsapi", method = RequestMethod.GET)
	public JsapiType getJsapi(@RequestParam(value = "url") String url) {
		return weixinService.getJsapi(url);
    }

}
