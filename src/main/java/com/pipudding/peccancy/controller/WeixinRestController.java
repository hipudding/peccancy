package com.pipudding.peccancy.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

import com.pipudding.peccancy.service.WeixinService;
import com.pipudding.peccancy.utils.CustomerInfoType;
import com.pipudding.peccancy.utils.EvenInfoType;
import com.pipudding.peccancy.utils.EventType;
import com.pipudding.peccancy.utils.JsapiType;
import com.pipudding.peccancy.utils.ResultType;

@RestController
public class WeixinRestController {
	@Value("${img_path}")
	String imgPath;
	
	@Value("${my_token}")
	String myToken;
	
	@Autowired
	WeixinService weixinService;
	
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
	
	/*@RequestMapping(value = "/", method = RequestMethod.POST)
	String weixinMessage(HttpServletRequest request) throws IOException
	{
		InputStream inputStream = request.getInputStream();
		Scanner s = new Scanner(inputStream).useDelimiter("\\A");
		String result = s.hasNext() ? s.next() : "";
		return "123";
	}*/
	
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
		
		if(weixinService.saveFile(file, imgPath,fileName) == false)
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
		weixinService.deleteImage(imgPath,fileName);
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/weixin/postcustomerinfo", method = RequestMethod.POST)
	public ResultType postCustomerInfo(@RequestBody CustomerInfoType customerInfo,HttpServletRequest request) throws IllegalStateException, IOException {
		ResultType ret = new ResultType();
		String customerId = request.getSession(true).getAttribute("customerId").toString();
		
		weixinService.saveCustomInfo(customerId, customerInfo);
		
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/weixin/getcustomerinfo", method = RequestMethod.POST)
	public CustomerInfoType getCustomerInfo(@RequestParam(value = "customerId") String customerId) throws IllegalStateException, IOException {
		CustomerInfoType customerInfo = weixinService.getCustomer(customerId);
		
		return customerInfo;
    }
	
	@RequestMapping(value = "/weixin/gettypes", method = RequestMethod.GET)
	public EventType[] getTypes() throws IllegalStateException, IOException {
		
		List<EventType> eventTypes = weixinService.getEventTypes();
		EventType[] eventTypeArray = new EventType[eventTypes.size()];
		eventTypes.toArray(eventTypeArray);
		return eventTypeArray;
    }
	
	@RequestMapping(value = "/weixin/posteventinfo", method = RequestMethod.POST)
	public ResultType postEventInfo(@RequestBody EvenInfoType eventInfo,HttpServletRequest request) throws IllegalStateException, IOException {
		ResultType ret = new ResultType();
		ret.setResultCode("fail");
		String customerId = request.getSession(true).getAttribute("customerId").toString();
		String eventId = weixinService.createEvent(customerId,eventInfo);
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
	
	@RequestMapping(value = "/weixin/jsapi", method = RequestMethod.GET)
	public JsapiType getJsapi(@RequestParam(value = "url") String url) {
		return weixinService.getJsapi(url);
    }

}
