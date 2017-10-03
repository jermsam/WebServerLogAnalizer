package com.ef.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class StatusComment {
	@Id
	@Column(name="statusComment_code")
	private int code;
	private String comment;
	
	@ManyToMany(cascade = {CascadeType.ALL})
//	@JsonBackReference
	@JoinTable(
		name = "ipAddresses_statusComments",
		joinColumns = {
				@JoinColumn(name = "statusComment_code", referencedColumnName = "statusComment_code")
		},
		inverseJoinColumns = @JoinColumn(name = "ipAddress_id", referencedColumnName = "ipAddress_id"))
	private List<IpAddress> ipAddresses;
	
	public List<IpAddress> getIpAddresses() {
		return ipAddresses;
	}
	public void setIpAddresses(List<IpAddress> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}
	public StatusComment(int code, String comment,List<IpAddress> ipAddresses) {
		super();
		this.code = code;
		this.comment = comment;
		this.ipAddresses=ipAddresses;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
