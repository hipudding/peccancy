package com.pipudding.peccancy.utils;

import java.util.List;

public class EventShowType {

	String eventNo;
	
	String text;

	String result;
	
	boolean showInputResult;
	
	boolean eventFinish;
	
	String longitude;
	
	String latitude;
	
	int flowCount;

	List<String> images;

	List<FlowHistoryType> flows;
	
	CustomerInfoType customer;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<FlowHistoryType> getFlows() {
		return flows;
	}

	public void setFlows(List<FlowHistoryType> flows) {
		this.flows = flows;
	}

	public String getEventNo() {
		return eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public CustomerInfoType getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerInfoType customer) {
		this.customer = customer;
	}

	public boolean isShowInputResult() {
		return showInputResult;
	}

	public void setShowInputResult(boolean showInputResult) {
		this.showInputResult = showInputResult;
	}

	public boolean isEventFinish() {
		return eventFinish;
	}

	public void setEventFinish(boolean eventFinish) {
		this.eventFinish = eventFinish;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public int getFlowCount() {
		return flowCount;
	}

	public void setFlowCount(int flowCount) {
		this.flowCount = flowCount;
	}
	
	

}
