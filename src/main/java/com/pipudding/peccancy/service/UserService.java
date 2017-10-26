package com.pipudding.peccancy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pipudding.peccancy.dao.CustomerDao;
import com.pipudding.peccancy.dao.UserDao;
import com.pipudding.peccancy.entity.CustomerEntity;
import com.pipudding.peccancy.entity.UserEntity;
import com.pipudding.peccancy.type.CustomerInfoType;
import com.pipudding.peccancy.type.UserInfoType;
import com.pipudding.peccancy.type.UserLoginInfoType;

@Service
@Transactional
public class UserService {
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	UserDao userDao;
	
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
	
	public List<UserInfoType> getUser()
	{
		String hql = "FROM user";
		List<UserEntity> userList = userDao.findByHQL(hql);
		List<UserInfoType> userInfoList = new ArrayList<UserInfoType>();
		if(userList == null)
			return userInfoList;
		
		for(UserEntity user:userList)
		{
			UserInfoType userInfo = new UserInfoType();
			userInfo.setUserId(user.getUserId());
			userInfo.setUserEmail(user.getEmail());
			userInfo.setUserName(user.getUserName());
			userInfoList.add(userInfo);
		}
		return userInfoList;
	}
	
	public void addUser(UserInfoType userInfo)
	{
		//TODO 重名用户
		UserEntity user = new UserEntity();
		user.setUserId(UUID.randomUUID().toString());
		user.setUserName(userInfo.getUserName());
		user.setEmail(userInfo.getUserEmail());
		user.setPassword("");
		userDao.save(user);
	}
	
	public void resetUserPassword(String userId)
	{
		UserEntity user = userDao.findById(userId);
		if(user == null)
			return;
		
		user.setPassword("");
		userDao.saveOrUpdate(user);
	}
	
	public boolean login(UserLoginInfoType loginType,StringBuffer userId,StringBuffer userName)
	{
		String hql = "FROM user WHERE user_name = ?0";
		List<UserEntity> userList = userDao.findByHQL(hql, loginType.getUserName());
		if(userList == null || userList.size() == 0)
			return false;
		
		UserEntity user = userList.get(0);
		String encript = "";
		if(user.getPassword().length() == 0)
		{
			encript = DigestUtils.sha256Hex(loginType.getPassword());
			user.setPassword(encript);
			userDao.saveOrUpdate(user);
			userId.append(user.getUserId());
			userName.append(user.getUserName());
			return true;
		}
		
		encript = DigestUtils.sha256Hex(loginType.getPassword());
		if(encript.equals(user.getPassword()))
		{
			userId.append(user.getUserId());
			userName.append(user.getUserName());
			return true;
		}
		else
			return false;
		
	}
	
	public boolean customerExist(String customerId)
	{
		CustomerEntity customer = customerDao.findById(customerId);
		return customer == null?false:true;
	}
}
