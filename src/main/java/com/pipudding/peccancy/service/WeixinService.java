package com.pipudding.peccancy.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pipudding.peccancy.dao.CustomerDao;
import com.pipudding.peccancy.dao.EventDao;
import com.pipudding.peccancy.dao.EventTypeDao;
import com.pipudding.peccancy.dao.FlowDao;
import com.pipudding.peccancy.dao.FlowHistoryDao;
import com.pipudding.peccancy.dao.ImageDao;
import com.pipudding.peccancy.entity.CustomerEntity;
import com.pipudding.peccancy.entity.EventEntity;
import com.pipudding.peccancy.entity.EventTypeEntity;
import com.pipudding.peccancy.entity.FlowEntity;
import com.pipudding.peccancy.entity.FlowHistoryEntity;
import com.pipudding.peccancy.entity.ImageEntity;
import com.pipudding.peccancy.utils.CustomerInfoType;
import com.pipudding.peccancy.utils.EventListType;
import com.pipudding.peccancy.utils.EventShowType;
import com.pipudding.peccancy.utils.EventType;
import com.pipudding.peccancy.utils.FlowHistoryType;

@Service
@Transactional
public class WeixinService {
	
	@Autowired
	ImageDao imageDao;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	EventTypeDao eventTypeDao;
	
	@Autowired
	EventDao eventDao;
	
	@Autowired
	FlowHistoryDao flowHistoryDao;
	
	@Autowired
	FlowDao flowDao;
	
	public boolean saveFile(MultipartFile file,String imgPath,String fileName)
	{		
		if(file == null)
		{
			return false;
		}
		
		File destFile = new File(imgPath + "/" + fileName);
		try {
			file.transferTo(destFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		ImageEntity image = new ImageEntity();
		image.setEventId("");
		image.setImgId(fileName);
		image.setCustomerId("hua");
		imageDao.save(image);
		
		return true;
	}
	
	public void clearLastNotCommitImages(String userId)
	{
		userId = "hua";
		String hql = "FROM img WHERE user_id = ?0 and event_id = ?1";
		List<ImageEntity> imageList = imageDao.findByHQL(hql, userId,"");
		for(ImageEntity image : imageList)
		{
			String fileName = image.getImgId();
			File deleteFile = new File(fileName);
			if(deleteFile.exists())
			{
				deleteFile.delete();
			}
		}
		hql = "DELETE FROM img WHERE user_id = ?0 and event_id = ?1";
		imageDao.executeHQL(hql, userId,"");
	}
	
	public void deleteImage(String imgPath, String fileName)
	{
		File deleteFile = new File(imgPath + "/" + fileName);
		if(deleteFile.exists())
			deleteFile.delete();
		ImageEntity image = imageDao.findById(fileName);
		if(image != null)
			imageDao.delete(image);
	}
	
	public CustomerInfoType getCustomer(String customerId)
	{
		CustomerEntity customer = customerDao.findById(customerId);
		CustomerInfoType customerInfo = new CustomerInfoType();
		if(customer != null)
		{
			customerInfo.setIdentify(customer.getCustomerIdentity());
			customerInfo.setName(customer.getCustomerName());
			customerInfo.setTel(customer.getCustomerTel());
		}
		
		return customerInfo;
		
	}
	
	public void saveCustomInfo(String customerId,CustomerInfoType customerInfo)
	{
		if(customerInfo == null)
			return;
		CustomerEntity customer = new CustomerEntity();
		customer.setCustomerId(customerId);
		customer.setCustomerName(customerInfo.getName());
		customer.setCustomerIdentity(customerInfo.getIdentify());
		customer.setCustomerTel(customerInfo.getTel());
		
		customerDao.saveOrUpdate(customer);
	}
	
	public List<EventType> getEventTypes()
	{
		String hql = "FROM event_type WHERE parent = ?0";
		List<EventTypeEntity> eventTypeEntitys = eventTypeDao.findByHQL(hql, "0");
		List<EventType> eventTypes = new ArrayList<EventType>();
		for(EventTypeEntity eventTypeEntity: eventTypeEntitys)
		{
			EventType eventType = new EventType();
			List<EventType> childrenTypes = new ArrayList<EventType>();
			eventType.setLabel(eventTypeEntity.getTypeDesc());
			eventType.setValue(eventTypeEntity.getTypeId());
			
			hql = "FROM event_type WHERE parent = ?0";
			List<EventTypeEntity> childrenEntitys = eventTypeDao.findByHQL(hql, eventType.getValue());
			
			for(EventTypeEntity childEntity: childrenEntitys)
			{
				EventType childType = new EventType();
				childType.setLabel(childEntity.getTypeDesc());
				childType.setValue(childEntity.getTypeId());
				childrenTypes.add(childType);
			}
			
			eventType.setChildren(childrenTypes);
			eventTypes.add(eventType);
			
		}
		return eventTypes;
	}
	
	public String createEvent(String customerId,String eventType,String information)
	{
		String eventId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		String hql = "FROM flow WHERE expired = ?0 ORDER BY flow_no";
		List<FlowEntity> currentFlows = flowDao.findByHQL(hql,0);
		if(currentFlows == null || currentFlows.size() == 0)
			return "";
		
		EventEntity event = new EventEntity();
		event.setDescription(information);
		event.setEventId(eventId);
		event.setEventType(eventType);
		event.setFlowGroup(currentFlows.get(0).getFlowGroup());
		event.setFlowNo(0);
		event.setCommitor(customerId);
		event.setStars(0);
		event.setFinish(0);
		eventDao.save(event);
		
		for(FlowEntity flow:currentFlows)
		{
			FlowHistoryEntity flowHistory = new FlowHistoryEntity();
			flowHistory.setEventId(eventId);
			flowHistory.setFlow_no(flow.getFlowNo());
			flowHistoryDao.save(flowHistory);
		}
		
		return eventId;
	}
	
	public void setImageEventId(String[]imageFileNames,String eventId)
	{
		for(String imageFileName:imageFileNames)
		{
			ImageEntity image = imageDao.findById(imageFileName);
			if(image != null)
			{
				image.setEventId(eventId);
				imageDao.update(image);
			}
		}
	}
	
	public List<EventListType> getEventList(String customerId)
	{
		String hql = "FROM event WHERE commitor = ?0 ORDER BY event_id DESC";
		List<EventEntity> eventEntitys = eventDao.findByHQL(hql, customerId);
		List<EventListType> events = new ArrayList<EventListType>();
		for(EventEntity eventEntity:eventEntitys)
		{
			EventListType event = new EventListType();
			event.setEventId(eventEntity.getEventId());
			event.setEventType(eventEntity.getEventType());
			event.setIcon(eventEntity.getFinish() == 0?"waiting":"success");
			events.add(event);
		}
		return events;
	}
	
	public EventShowType getEvent(String eventId)
	{
		String hql = "FROM img WHERE event_id = ?0";
		List<ImageEntity> images = imageDao.findByHQL(hql, eventId);
		
		hql = "FROM flow_history WHERE event_id = ?0";
		List<FlowHistoryEntity> flows = flowHistoryDao.findByHQL(hql, eventId);
		
		EventEntity event = eventDao.findById(eventId);
		
		EventShowType eventShow = new EventShowType();
		eventShow.setText(event.getDescription());
		eventShow.setResult(event.getResult()==null?"":event.getResult());
		
		List<String> imagesShow = new ArrayList<String>();
		for(ImageEntity image:images)
		{
			imagesShow.add(image.getImgId());
		}
		eventShow.setImages(imagesShow);
		
		List<FlowHistoryType> flowsShow = new ArrayList<FlowHistoryType>();
		int flowNo = event.getFlowNo();
		String flowGroup = event.getFlowGroup();
		for(FlowHistoryEntity flow:flows)
		{
			hql = "FROM flow WHERE flow_group = ?0 and flow_no = ?1";
			String description = "";
			List<FlowEntity> flowEntities = flowDao.findByHQL(hql, flowGroup,flow.getFlow_no());
			if(flowEntities != null && flowEntities.size() != 0)
				description = flowEntities.get(0).getFlowDesc();
			FlowHistoryType flowHistory = new FlowHistoryType();
			flowHistory.setFlowDesc(description);
			flowHistory.setIcon(flow.getFlow_no() <= flowNo?"success":"waiting");
			flowsShow.add(flowHistory);
		}
		eventShow.setFlows(flowsShow);
		
		return eventShow;
	}
}
