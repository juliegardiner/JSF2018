package com.student.Models;

public class StudentCourse {
	//Mix Student and course variables for a join
	//Student variables
		private String sid;
		private String name;
		private String address;
	//Course Variables
		private String cID;
		private String cName;
		private int duration;

		
		//Getters and Setters
		public String getSid() {
			return sid;
		}
		public void setSid(String sid) {
			this.sid = sid;
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
