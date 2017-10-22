package com.pipudding.peccancy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity(name = "flow")
@Table(appliesTo = "flow")
public class FlowEntity {
	@Id
	@Column(name = "flow_id")
	String flowId;
	
	@Column(name = "flow_no")
	int flowNo;
	
	@Column(name = "flow_group")
	String flowGroup;
	
	@Column(name = "expired")
	int expired;
	
	@Column(name = "flow_desc")
	String flowDesc;

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public int getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(int flowNo) {
		this.flowNo = flowNo;
	}

	public String getFlowGroup() {
		return flowGroup;
	}

	public void setFlowGroup(String flowGroup) {
		this.flowGroup = flowGroup;
	}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	public String getFlowDesc() {
		return flowDesc;
	}

	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}
	
}
