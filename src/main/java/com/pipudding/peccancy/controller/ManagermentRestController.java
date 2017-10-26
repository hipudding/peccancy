package com.pipudding.peccancy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pipudding.peccancy.service.ConfigService;
import com.pipudding.peccancy.service.EventService;
import com.pipudding.peccancy.service.UserService;
import com.pipudding.peccancy.service.WeixinService;
import com.pipudding.peccancy.type.EventResultType;
import com.pipudding.peccancy.type.EventTypeSubmit;
import com.pipudding.peccancy.type.ResultType;
import com.pipudding.peccancy.type.UserInfoType;
import com.pipudding.peccancy.type.UserLoginInfoType;

@RestController
public class ManagermentRestController {
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ConfigService configService;
	
	@RequestMapping(value = "/manage/next", method = RequestMethod.POST)
    public ResultType eventToNextStage(@RequestBody EventResultType eventResult) {
		ResultType ret = new ResultType();
		String userId = "lujie";
		eventService.pushEventToNext(userId,eventResult);
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/admin/createuser", method = RequestMethod.POST)
    public ResultType createUser(@RequestBody UserInfoType userInfo) {
		ResultType ret = new ResultType();
		
		userService.addUser(userInfo);
		
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/admin/resetPassword/{userId}", method = RequestMethod.POST)
    public ResultType resetPassword(@PathVariable String userId) {
		ResultType ret = new ResultType();
		
		userService.resetUserPassword(userId);
		
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	@RequestMapping(value = "/manage/dologin", method = RequestMethod.POST)
    public ResultType login(@RequestBody UserLoginInfoType userLoginInfo,HttpServletRequest request) {
		ResultType ret = new ResultType();
		ret.setResultCode("fail");
		ret.setResultText("");
		StringBuffer userId = new StringBuffer();
		StringBuffer userName = new StringBuffer();
		
		if(userService.login(userLoginInfo,userId,userName) == false)
		{
			return ret;
		}
		
		request.getSession(true).setAttribute("userId", userId.toString());
		request.getSession(true).setAttribute("userName", userName.toString());
		
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	@RequestMapping(value = "/admin/updateflowinfo", method = RequestMethod.POST)
    public ResultType updateFlowInfo(@RequestBody String[] flowNames) {
		ResultType ret = new ResultType();
		ret.setResultCode("fail");
		ret.setResultText("");
		
		configService.updateFlowInfo(flowNames);

		
		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }
	
	
	@RequestMapping(value = "/admin/updatetypeinfo", method = RequestMethod.POST)
    public ResultType updateFlowInfo(@RequestBody EventTypeSubmit[] types) {
		ResultType ret = new ResultType();
		ret.setResultCode("fail");
		ret.setResultText("");
		
		configService.updateTypeInfo(types);

		ret.setResultCode("success");
		ret.setResultText("");
		return ret;
    }

}
