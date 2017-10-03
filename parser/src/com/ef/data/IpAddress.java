package com.ef.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class IpAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ipAddress_id")
	private int id;  
	private String address;
	
	@ManyToMany(cascade = {CascadeType.ALL},  mappedBy="ipAddresses")
	@JsonIgnore
	private List<StatusComment> statusComments=new ArrayList<>();
	
	public List<StatusComment> getStatusComments() {
		return statusComments;
	}
	public void setStatusComments(List<StatusComment> statusComments) {
		this.statusComments = statusComments;
	}
	public IpAddress(String address) {
		super();
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	} 
	
	
}
