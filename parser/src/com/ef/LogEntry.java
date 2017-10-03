package com.ef;

import java.util.Date;

/**Java Class ~ a Blue print for each log*/
public class LogEntry {
	//immutable ~private & no setter defnz
	private String ipAddress;
	private Date accessTime;
	private int StatusCode;
	//immutable ~ innitialize only at object creation
	
	public String getIpAddress() {
		return ipAddress;
	}
	public LogEntry(String ipAddress, Date accessTime, int statusCode) {
		super();
		this.ipAddress = ipAddress;
		this.accessTime = accessTime;
		StatusCode = statusCode;
	}
	public LogEntry() {
		// TODO Auto-generated constructor stub
	}
	public Date getAccessTime() {
		return accessTime;
	}
	public int getStatusCode() {
		return StatusCode;
	}
	@Override// delimited by pipe (|)
	public String toString() {
		return ipAddress+"|" + accessTime + "|" + StatusCode ;
	}
	
}
