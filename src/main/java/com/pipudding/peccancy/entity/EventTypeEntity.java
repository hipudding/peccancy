package com.pipudding.peccancy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity(name = "event_type")
@Table(appliesTo = "event_type")
public class EventTypeEntity {
	
	@Id
	@Column(name = "type_id")
	String typeId;
	
	@Column(name = "type_desc")
	String typeDesc;
	
	@Column(name = "parent")
	String parent;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	
	
}
