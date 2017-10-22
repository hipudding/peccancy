package com.pipudding.peccancy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity(name = "flow_history")
@Table(appliesTo = "flow_history")
public class FlowHistoryEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "flow_history_id")
	String flowHistoryId;
	
	@Column(name = "event_id")
	String eventId;
	
	@Column(name = "flow_no")
	int flow_no;
	
	@Column(name = "processor")
	String processor;
	
	public String getFlowHistoryId() {
		return flowHistoryId;
	}

	public void setFlowHistoryId(String flowHistoryId) {
		this.flowHistoryId = flowHistoryId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public int getFlow_no() {
		return flow_no;
	}

	public void setFlow_no(int flow_no) {
		this.flow_no = flow_no;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	
}
