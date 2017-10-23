package com.pipudding.peccancy.controller;

import java.util.List;

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
	
	@RequestMapping("/postevent")  
    public String postEvent() {  
		weixinService.clearLastNotCommitImages("hua");
        return "event_info";  
    }
	
	@RequestMapping("/recordinfo")  
    public String recordInfo() {  
        return "personal_info";  
    }
	
	@RequestMapping("/getrecordinfo")  
    public String getRecordInfo(Model model) {
		String customerId = "hua";
		
		CustomerInfoType customerInfo = weixinService.getCustomer(customerId);
		
		model.addAttribute("name", customerInfo.getName());
		model.addAttribute("tel", customerInfo.getTel());
		model.addAttribute("identify", customerInfo.getIdentify());

        return "personal_info";  
    }
	
	@RequestMapping("/eventlist") 
    public String eventList(Model model) {  
		String customerId = "hua";
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
