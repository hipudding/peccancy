package com.pipudding.peccancy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pipudding.peccancy.service.WeixinService;
import com.pipudding.peccancy.utils.CustomerInfoType;

@Controller
public class WeixinController {
	
	@Autowired
	WeixinService weixinService;
	
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
    public String eventList() {  
        return "event_list";  
    }
	
	@RequestMapping("/eventshow")  
    public String eventShow() {  
        return "event_show";  
    }
	
}
