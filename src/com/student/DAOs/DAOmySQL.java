package com.student.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import com.student.Models.*;
import com.sun.jmx.snmp.daemon.CommunicationException;

@ManagedBean
public class DAOmySQL {
	private DataSource mysqlDS;
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private Course course;
	private Student student;

	// Constructor
	public DAOmySQL() throws Exception {
		super();
		Context context = new InitialContext();
		String jndiName = "java:comp/env/jdbc/studentdb";
		mysqlDS = (DataSource) context.lookup(jndiName);
	}

	// **************************************************************//
	//////////////////////// STUDENT///////////////////////////////////
	// ***************************************************************//

	// Get the students array
	public ArrayList<Student> loadStudents() throws Exception {
		ArrayList<Student> students = new ArrayList<Student>();

		conn = mysqlDS.getConnection();
		String query = ("SELECT * from student");
		ps = conn.prepareStatement(query);
		// Return ResultSet
		rs = ps.executeQuery();

		while (rs.next()) {
			String sid = rs.getString(1);
			String cID = rs.getString(2);
			String name = rs.getString(3);
			String address = rs.getString(4);

			Student s = new Student(sid, cID, name, address);
			students.add(s);
		}

		// Close Connections
		rs.close();
		ps.close();
		conn.close();
		return students;
	}

	// Add New Student to DB
	public void addNewStudent(Student s) throws SQLException {
		conn = mysqlDS.getConnection();
		String query = ("INSERT into student values(?,?,?,?)");

		ps = conn.prepareStatement(query);
		ps.setString(1, s.getSid());
		ps.setString(2, s.getcID());
		ps.setString(3, s.getName());
		ps.setString(4, s.getAddress());
		ps.executeUpdate();

		// Close Connections
		ps.close();
		conn.close();

	}

	// Delete Student
	public void deleteStudent(Student s) throws Exception {
		conn = null;
		ps = null;

		conn = mysqlDS.getConnection();
		String query = ("DELETE from student where name = ?");
		ps = conn.prepareStatement(query);

		ps.setString(1, s.getName());
		ps.executeUpdate();

		System.out.println("Student successfully deleted...");

		// Close connection
		conn.close();
		ps.close();
	}

	// **************************************************************//
	//////////////////////// COURSES///////////////////////////////////
	// ***************************************************************//

	// Get the Courses array
	public ArrayList<Course> loadCourses() throws Exception {
		ArrayList<Course> courses = new ArrayList<Course>();
		conn = mysqlDS.getConnection();
		String query = ("SELECT * from course");
		ps = conn.prepareStatement(query);
		// Return ResultSet
		rs = ps.executeQuery();

		while (rs.next()) {
			String cID = rs.getString(1);
			String cName = rs.getString(2);
			int duration = rs.getInt(3);

			Course c = new Course(cID, cName, duration);

			// Add to the Course to the arraylist
			courses.add(c);
		}
		// Close Connections
		rs.close();
		ps.close();
		conn.close();

		return courses;
	}

	// Add New Student to DB
	public void addNewCourse(Course c) throws SQLException {
		conn = mysqlDS.getConnection();
		String query = ("INSERT into Course values(?,?,?)");
		ps = conn.prepareStatement(query);
		ps.setString(1, c.getcID());
		ps.setString(2, c.getcName());
		ps.setInt(3, c.getDuration());

		ps.executeUpdate();

		// Close Connections
		ps.close();
		conn.close();
	}

	// Delete Course
	public void deleteCourse(Course c) throws SQLException {
		conn = null;
		ps = null;

		conn = mysqlDS.getConnection();
		String query = "DELETE from course where cID = ?";
		ps = conn.prepareStatement(query);
		ps.setString(1, c.getcID());
		ps.executeUpdate();

		System.out.println("Course" + c + "successfully deleted...");

		// Close connection
		conn.close();
		ps.close();
	}

	// **************************************************************//
	//////////////////////// STUDENT_COURSES///////////////////////////
	// ***************************************************************//

	public ArrayList<Course> loadAllDetails(Course c) throws SQLException {

		ArrayList<Course> scDetails = new ArrayList<Course>();
		conn = mysqlDS.getConnection();
		String query = ("select c.*,s.name,s.address from course c join student s on c.cID=s.cID where c.cID=?");
		ps = conn.prepareStatement(query);
		ps.setString(1, c.getcID());
		// Result Set
		rs = ps.executeQuery();

		while (rs.next()) {
			course = new Course(rs.getString("cID"), rs.getString("cName"), rs.getInt("duration"));
			student = new Student();
			student.setcID(rs.getString("name"));
			student.setAddress(rs.getString("address"));
			course.setStudent(student);
			scDetails.add(course);
		}
		// Close connection
		conn.close();
		ps.close();

		return scDetails;

	}

}
