package com.student.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.student.DAOs.DAOmySQL;
import com.student.Models.Course;


@ManagedBean
@SessionScoped
public class CourseController {
	private ArrayList<Course> course;
	private DAOmySQL daomySQL;
	private Course courses;

	public CourseController() throws Exception {
		super();
		daomySQL = new DAOmySQL();
		course = new ArrayList<>();
	}

	// Load Courses from DAO 
	public void loadCourses() {
		if (daomySQL != null) {
			try {
				course = daomySQL.loadCourses();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public ArrayList<Course> getCourse() {
		return course;
	}

	// Adding new Course to mySql
	public String addNewCourse(Course c) {
		try {
			daomySQL.addNewCourse(c);
			FacesMessage message = new FacesMessage(" Course Has Been Added ");
			FacesContext.getCurrentInstance().addMessage(null, message);

			return "list_courses.xhtml";
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

	public String deleteCourse(Course c) {
		// Check if DAO is empty
		if (daomySQL != null) {
			try {
				daomySQL.deleteCourse(c);
				FacesMessage message = new FacesMessage( c.getcID() + " Has Been deleted.. ");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return "list_courses.xhtml";
			} catch (MySQLIntegrityConstraintViolationException e) {
				FacesMessage message = new FacesMessage("ERROR Duplicate primary key" + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (CommunicationsException e) {
				FacesMessage message = new FacesMessage("ERROR: Cannot connect to Database" + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (Exception e) {
				FacesMessage message = new FacesMessage("ERROR unable to delete Course " + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}

		}
		return null;

	}
	//Global Variable stores info from list_courses to be passed to studentCourseDetails()
	public String getCourseObject(Course courses) {
		this.courses=courses;
		return "courseStudentDetails.xhtml";
	}

	//Details for ALL Course Details of the students
	public void studentCourseDetails() {
		course=new ArrayList<Course>();
		try {
			course=daomySQL.loadAllDetails(this.courses);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);	
		}
	}
}
