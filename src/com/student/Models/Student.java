package com.student.Models;

import javax.faces.bean.ManagedBean;

import com.student.DAOs.DAOmySQL;
import com.student.DAOs.DAOneo4j;


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

	public Student(String sid, String cID, String name, String address) {
	this.sid=sid;
	this.cID=cID;
	this.name=name;
	this.address=address;
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


