package com.pipudding.peccancy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pipudding.peccancy.service.EventService;
import com.pipudding.peccancy.service.UserService;
import com.pipudding.peccancy.type.CustomerInfoType;
import com.pipudding.peccancy.type.EventListType;
import com.pipudding.peccancy.type.EventShowType;

@Controller
@RequestMapping("/weixin") 
public class WeixinController {
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/postevent",method=RequestMethod.GET)  
    public String postEvent(HttpServletRequest request,Model model) {  
		String customerId = request.getSession(true).getAttribute("customerId").toString();
		
		boolean customerExist = userService.customerExist(customerId);
		
		model.addAttribute("registed", customerExist);
		eventService.clearLastNotCommitImages(customerId);
        return "event_info";  
    }
	
	@RequestMapping(value = "/recordinfo",method=RequestMethod.GET)  
    public String recordInfo() {  
        return "personal_info";  
    }
	
	@RequestMapping(value = "/getrecordinfo",method=RequestMethod.GET)  
    public String getRecordInfo(Model model,HttpServletRequest request) {
		String customerId = request.getSession(true).getAttribute("customerId").toString();
		
		CustomerInfoType customerInfo = userService.getCustomer(customerId);
		
		model.addAttribute("name", customerInfo.getName());
		model.addAttribute("tel", customerInfo.getTel());
		model.addAttribute("identify", customerInfo.getIdentify());

        return "personal_info";  
    }
	
	@RequestMapping(value = "/eventlist",method=RequestMethod.GET) 
    public String eventList(Model model,HttpServletRequest request) {  
		String customerId = request.getSession(true).getAttribute("customerId").toString();
		List<EventListType> events = eventService.getEventList(customerId);
		model.addAttribute("eventList",events);
		
        return "event_list";  
    }
	@RequestMapping(	value="/eventshow/{eventId}",method=RequestMethod.GET)  
    public String eventShow(@PathVariable String eventId,Model model) {
		
		EventShowType eventShow = eventService.getEvent(eventId);
		model.addAttribute("event", eventShow);
		
        return "event_show";  
    }
	
}
