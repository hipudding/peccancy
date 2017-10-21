package com.pipudding.peccancy.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pipudding.peccancy.dao.CustomerDao;
import com.pipudding.peccancy.dao.EventTypeDao;
import com.pipudding.peccancy.dao.ImageDao;
import com.pipudding.peccancy.entity.CustomerEntity;
import com.pipudding.peccancy.entity.EventTypeEntity;
import com.pipudding.peccancy.entity.ImageEntity;
import com.pipudding.peccancy.utils.CustomerInfoType;
import com.pipudding.peccancy.utils.EventType;

@Service
@Transactional
public class WeixinService {
	
	@Autowired
	ImageDao imageDao;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	EventTypeDao eventTypeDao;
	
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
}
