package com.pipudding.peccancy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity(name = "custom")
@Table(appliesTo = "custom")
public class CustomerEntity {

	@Id
	@Column(name = "custom_id")
	private String customerId;
	
	@Column(name = "custom_name")
	private String customerName;

	@Column(name = "custom_id_card_no")
	private String customerIdentity;
	
	@Column(name = "custom_tel")
	private String customerTel;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerTel() {
		return customerTel;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	public String getCustomerIdentity() {
		return customerIdentity;
	}

	public void setCustomerIdentity(String customerIdentity) {
		this.customerIdentity = customerIdentity;
	}
	
	
}
