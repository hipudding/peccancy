package com.pipudding.peccancy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pipudding.peccancy.service.WeixinService;
import com.pipudding.peccancy.utils.EventResultType;
import com.pipudding.peccancy.utils.ResultType;
import com.pipudding.peccancy.utils.UserInfoType;
import com.pipudding.peccancy.utils.UserLoginInfoType;

@RestController
@RequestMapping("/manage") 
public class ManagermentRestController {
	
	@Autowired
	WeixinService weixinService;
	
	@RequestMapping(value = "/next", method = RequestMethod.POST)
    public ResultType eventToNextStage(@RequestBody EventResultType eventResult) {
		ResultType ret = new ResultType();
		String userId = "lujie";
		weixinService.pushEventToNext(userId,eventResult);
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
    public ResultType createUser(@RequestBody UserInfoType userInfo) {
		ResultType ret = new ResultType();
		
		weixinService.addUser(userInfo);
		
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/resetPassword/{userId}", method = RequestMethod.POST)
    public ResultType resetPassword(@PathVariable String userId) {
		ResultType ret = new ResultType();
		
		weixinService.resetUserPassword(userId);
		
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public ResultType login(@RequestBody UserLoginInfoType userLoginInfo) {
		ResultType ret = new ResultType();
		ret.setResultCode("fail");
		ret.setResultText("");
		
		if(weixinService.login(userLoginInfo) == false)
		{
			return ret;
		}
		
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }

}
