package com.pipudding.peccancy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pipudding.peccancy.service.WeixinService;
import com.pipudding.peccancy.utils.CustomerInfoType;
import com.pipudding.peccancy.utils.EventListType;
import com.pipudding.peccancy.utils.EventShowType;

@Controller
@RequestMapping("/weixin") 
public class WeixinController {
	
	@Autowired
	WeixinService weixinService;
	
	@RequestMapping(value = "/postevent",method=RequestMethod.GET)  
    public String postEvent() {  
		weixinService.clearLastNotCommitImages("hua");
        return "event_info";  
    }
	
	@RequestMapping(value = "/recordinfo",method=RequestMethod.GET)  
    public String recordInfo() {  
        return "personal_info";  
    }
	
	@RequestMapping(value = "/getrecordinfo",method=RequestMethod.GET)  
    public String getRecordInfo(Model model,HttpServletRequest request) {
		String customerId = request.getSession(true).getAttribute("customerId").toString();
		
		CustomerInfoType customerInfo = weixinService.getCustomer(customerId);
		
		model.addAttribute("name", customerInfo.getName());
		model.addAttribute("tel", customerInfo.getTel());
		model.addAttribute("identify", customerInfo.getIdentify());

        return "personal_info";  
    }
	
	@RequestMapping(value = "/eventlist",method=RequestMethod.GET) 
    public String eventList(Model model,HttpServletRequest request) {  
		String customerId = request.getSession(true).getAttribute("customerId").toString();
		List<EventListType> events = weixinService.getEventList(customerId);
		model.addAttribute("eventList",events);
		
        return "event_list";  
    }
	@RequestMapping(	value="/eventshow/{eventId}",method=RequestMethod.GET)  
    public String eventShow(@PathVariable String eventId,Model model) {
		
		EventShowType eventShow = weixinService.getEvent(eventId);
		model.addAttribute("event", eventShow);
		
        return "event_show";  
    }
	
}
