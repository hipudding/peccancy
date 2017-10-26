package com.pipudding.peccancy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pipudding.peccancy.service.ConfigService;
import com.pipudding.peccancy.service.EventService;
import com.pipudding.peccancy.service.UserService;
import com.pipudding.peccancy.type.EventListType;
import com.pipudding.peccancy.type.EventShowType;
import com.pipudding.peccancy.type.EventType;
import com.pipudding.peccancy.type.UserInfoType;

@Controller
public class ManagementController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ConfigService configService;
	
	@RequestMapping(value = "/manage/login",method=RequestMethod.GET)  
    public String login() {
        return "login"; 
    }
	
	@RequestMapping(value = "/manage/event_detail/{eventId}",method=RequestMethod.GET)  
    public String eventDetail(@PathVariable String eventId, Model model) { 
		EventShowType eventShow = eventService.getEvent(eventId);
		model.addAttribute("event", eventShow);
        return "event_detail";  
    }
	
	@RequestMapping(value = "/manage/event_table",method=RequestMethod.GET)  
    public String eventTable(Model model) { 
		List<EventListType> eventList = eventService.getEventList();
		model.addAttribute("eventList", eventList);
        return "event_table";  
    }
	
	@RequestMapping(value = "/admin/type_management",method=RequestMethod.GET)  
    public String typeManagerment(Model model) {
		List<EventType> eventType = eventService.getEventTypes();
		model.addAttribute("eventTypes", eventType);
        return "type_management";  
    }
	
	@RequestMapping(value = "/admin/flow_management",method=RequestMethod.GET)  
    public String flowManagement(Model model) { 
		List<String> flowInfos = configService.getFlowInfo();
		model.addAttribute("flows", flowInfos);
        return "flow_management";  
    }
	
	@RequestMapping(value = "/admin/user_table",method=RequestMethod.GET)  
    public String userTable(Model model) {
		List<UserInfoType> userList = userService.getUser();
		model.addAttribute("userList", userList);
        return "user_table";  
    }
	
	
}
