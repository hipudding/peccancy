package com.pipudding.peccancy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity(name = "event")
@Table(appliesTo = "event")
public class EventEntity {
	@Id
	@Column(name = "event_id")
	String eventId;
	
	@Column(name = "description")
	String description;
	
	@Column(name = "flow_no")
	int flowNo;
	
	@Column(name = "flow_group")
	String flowGroup;
	
	@Column(name = "event_type")
	String eventType;
	
	@Column(name = "result")
	String result;
	
	@Column(name = "stars")
	int  stars;
	
	@Column(name = "commitor")
	String  commitor;
	
	@Column(name = "finish")
	int finish;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getCommitor() {
		return commitor;
	}

	public void setCommitor(String commitor) {
		this.commitor = commitor;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}
	
	
}
