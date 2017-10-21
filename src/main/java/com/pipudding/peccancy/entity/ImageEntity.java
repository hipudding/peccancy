package com.pipudding.peccancy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity(name = "img")
@Table(appliesTo = "img")
public class ImageEntity {
	@Column(name = "event_id")
	private String eventId;
	
	@Id
	@Column(name = "img_id")
	private String imgId;
	
	@Column(name = "user_id")
	private String customerId;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
	
	
}
