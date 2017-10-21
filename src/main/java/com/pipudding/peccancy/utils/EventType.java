package com.pipudding.peccancy.utils;

import java.util.List;

public class EventType {
	
	String label;
	
	String value;
	
	List<EventType> children;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<EventType> getChildren() {
		return children;
	}

	public void setChildren(List<EventType> children) {
		this.children = children;
	}
	
	

}
