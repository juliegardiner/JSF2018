package com.student.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import com.student.Models.*;


@ManagedBean
public class DAOmySQL {
	private DataSource mysqlDS;
	private Connection conn;
	private PreparedStatement ps;
    private ResultSet rs;
	
    //Constructor
	public DAOmySQL() throws Exception{
		super();
		Context context = new InitialContext();
		String jndiName = "java:comp/env/jdbc/studentdb";
		mysqlDS = (DataSource) context.lookup(jndiName);
	}

	//**************************************************************//
	////////////////////////STUDENT///////////////////////////////////
	//***************************************************************//
		
	//Get the students array
	public ArrayList<Student> loadStudents() throws Exception{
		ArrayList<Student> students = new ArrayList<Student>();
		conn=mysqlDS.getConnection();
		String query=("SELECT * from student");
		ps=conn.prepareStatement(query);
		//Return ResultSet
		rs=ps.executeQuery();
	
		while(rs.next()) {
		Student s = new Student();
		//sid
		rs.getString(1);
		//cid
		rs.getString(2);
		//name
		rs.getString(3);
		//address
		rs.getString(4);
		
		//Add to the students to the arraylist  
		students.add(s);
		}
		//Close Connections
		rs.close();
		ps.close();
		conn.close();
		
		return students;		
}
	//Add New Student to DB
	public void addNewStudent(Student s) throws SQLException  {
		conn=mysqlDS.getConnection();
		String query=("INSERT into student values(?,?,?,?)");
		ps=conn.prepareStatement(query);
		ps.setString(1, s.getSid());
		ps.setString(2, s.getcID());
		ps.setString(3, s.getName());
		ps.setString(4, s.getAddress());
		
		ps.executeUpdate();
	//Close Connections
		ps.close();
		conn.close();
		
	}
	//Delete Student
	public void deleteStudent(Student s) throws SQLException {
		conn=mysqlDS.getConnection();
		String query = ("DELETE from student where sid = ?");
		ps=conn.prepareStatement(query);
		ps.setString(1, s.getSid());
		
		int del = ps.executeUpdate();
		System.out.println("No of students deleted "+del);
		System.out.println("Student has been deleted");
	
	}	
	
	//**************************************************************//
	////////////////////////COURSES///////////////////////////////////
	//***************************************************************//
	
	//Get the Courses array
	public ArrayList<Course> loadCourses() throws Exception{
		ArrayList<Course> courses = new ArrayList<Course>();
		conn=mysqlDS.getConnection();
		String query=("SELECT * from course");
		ps=conn.prepareStatement(query);
		//Return ResultSet
		rs=ps.executeQuery();
	
		while(rs.next()) {
		Course c = new Course();
		//cid
		rs.getString(1);
		//cName
		rs.getString(2);
		//duration
		rs.getString(3);
		
		
		//Add to the Course to the arraylist  
		courses.add(c);
		}
		//Close Connections
		rs.close();
		ps.close();
		conn.close();
		
		return courses;		
}
	//Add New Student to DB
	public void addNewCourse(Course c) throws SQLException  {
		conn=mysqlDS.getConnection();
		String query=("INSERT into Course values(?,?,?)");
		ps=conn.prepareStatement(query);
		ps.setString(1, c.getcID());
		ps.setString(2, c.getcName());
		ps.setInt(3, c.getDuration());
	
		
		ps.executeUpdate();
	//Close Connections
		ps.close();
		conn.close();
	}
	
	//Delete Course
		public void deleteCourse(Course c) throws SQLException {
			conn=mysqlDS.getConnection();
			String query = ("DELETE from course where cid = ?");
			ps=conn.prepareStatement(query);
			ps.setString(1, c.getcID());
			
			int del = ps.executeUpdate();
			System.out.println("No of courses deleted "+del);
			System.out.println("Course has been deleted");
		
		}
		//**************************************************************//
		////////////////////////STUDENT_COURSES///////////////////////////
		//***************************************************************//
		
		public ArrayList<StudentCourse> loadAllDetails(Student s) throws SQLException{
			ArrayList<StudentCourse> scDetails = new ArrayList<StudentCourse>();
			conn=mysqlDS.getConnection();
			String query= ("select sid,c.cID,name,address,cName,duration from student s JOIN course c where c.cID=s.cID");
			ps=conn.prepareStatement(query);
			
			while(rs.next()) {
			StudentCourse sc = new StudentCourse();
			//ResultSet
			rs.getString(1);
			rs.getString(2);
			rs.getString(3);
			rs.getString(4);
			rs.getString(5);
			rs.getInt(6);
			
			scDetails.add(sc);
			}
			return scDetails;
			
			
		}
}


		