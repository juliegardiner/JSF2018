package com.student.Models;

import javax.faces.bean.ManagedBean;


@ManagedBean
public class Student {
	//Student variables
	private String sid;
	private String cID;
	private String name;
	private String address;
	
	
	//Getters and Setters
	
	public Student() {
	}

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getcID() {
		return cID;
	}

	public void setcID(String cID) {
		this.cID = cID;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
