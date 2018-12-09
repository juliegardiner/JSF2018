package com.student.Models;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Course {
	//Course Variables
	private String cID;
	private String cName;
	private int duration;

	//Getters and Setters
	public String getcID() {
		return cID;
	}

	public void setcID(String cID) {
		this.cID = cID;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
