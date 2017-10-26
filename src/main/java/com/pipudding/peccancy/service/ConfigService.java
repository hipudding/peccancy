package com.pipudding.peccancy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pipudding.peccancy.dao.EventTypeDao;
import com.pipudding.peccancy.dao.FlowDao;
import com.pipudding.peccancy.entity.EventTypeEntity;
import com.pipudding.peccancy.entity.FlowEntity;
import com.pipudding.peccancy.type.EventTypeSubmit;

@Service
@Transactional
public class ConfigService {
	
	@Autowired
	FlowDao flowDao;
	
	@Autowired
	EventTypeDao eventTypeDao;
	
	public List<String> getFlowInfo()
	{
		String hql = "FROM flow WHERE expired = ?0 ORDER BY flow_no";
		List<FlowEntity> flowList = flowDao.findByHQL(hql,0);
		List<String> flowInfoList = new ArrayList<String>();
		if(flowList == null)
			return flowInfoList;
		
		for(FlowEntity flow:flowList)
		{
			flowInfoList.add(flow.getFlowDesc());
		}
		return flowInfoList;
	}
	
	public void updateFlowInfo(String[] flowNames)
	{
		String hql = "UPDATE flow f SET f.expired= ?0";
		flowDao.executeHQL(hql, 1);
		int flowNo = 0;
		String flowGroup = UUID.randomUUID().toString();
		for(String flowName:flowNames)
		{
			FlowEntity flow = new FlowEntity();
			
			
			String flowId = UUID.randomUUID().toString();
			flow.setFlowId(flowId);
			flow.setExpired(0);
			flow.setFlowGroup(flowGroup);
			flow.setFlowNo(flowNo);
			if(flowName.startsWith(">"))
			{
				flowName = flowName.substring(1);
				flow.setRecordResult(1);
			}
			else
			{
				flow.setRecordResult(0);
			}
			flow.setFlowDesc(flowName);

			flowDao.save(flow);
			flowNo ++;
		}
	}
	
	public void updateTypeInfo(EventTypeSubmit[] types)
	{
		String hql = "DELETE FROM event_type";
		eventTypeDao.executeHQL(hql);
		
		for(EventTypeSubmit type:types)
		{
			EventTypeEntity level1 = new EventTypeEntity();
			level1.setTypeId(UUID.randomUUID().toString());
			level1.setTypeDesc(type.getLabel());
			level1.setParent("0");
			eventTypeDao.save(level1);
			
			for(String child:type.getChildren())
			{
				EventTypeEntity level2 = new EventTypeEntity();
				level2.setParent(level1.getTypeId());
				level2.setTypeDesc(child);
				level2.setTypeId(UUID.randomUUID().toString());
				eventTypeDao.save(level2);
			}
		}
	}
}
