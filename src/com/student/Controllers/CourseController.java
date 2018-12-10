package com.student.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import com.mysql.jdbc.CommunicationsException;
import com.student.DAOs.DAOmySQL;
import com.student.DAOs.DAOneo4j;
import com.student.Models.Course;
import com.student.Models.Student;
import com.student.Models.StudentCourse;

@ManagedBean
public class CourseController {
	private ArrayList<Course> course;
	private DAOmySQL daomySQL;
	private ArrayList<StudentCourse>allDetails;
	

	public CourseController() throws Exception {
		super();
		daomySQL = new DAOmySQL();
		course = new ArrayList<>();
		
		
	}

	public void loadCourses() {
		try {
			course = daomySQL.loadCourses();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("The course size = " + course.size());
	}

	public ArrayList<Course> getCourses() {
		return course;
	}

	public String addNewCourse(Course c) {
		try {
			daomySQL.addNewCourse(c);
			return "index.xhtml";
		} catch (CommunicationsException e) {
			System.out.println("Cannot connect to Database");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			System.out.println("That course is already in the database");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("Unknown Exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public String deleteCourse(Course c){
		try {
			daomySQL.deleteCourse(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list_courses.xhtml";
		
	}
	public void studentCourseDetails(Student s) {
		try {
			allDetails=daomySQL.loadAllDetails(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	

}
