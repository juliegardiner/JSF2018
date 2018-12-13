package com.student.Controllers;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.neo4j.driver.v1.exceptions.Neo4jException;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;
import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import javax.faces.context.*;
import com.student.DAOs.DAOmySQL;
import com.student.DAOs.DAOneo4j;
import com.student.Models.*;

@SessionScoped
@ManagedBean
public class StudentController {

	private ArrayList<Student> students;
	private DAOmySQL daomySQL;
	private DAOneo4j daoneo4j;

	// Constuctor
	public StudentController() throws Exception {
		super();
		daomySQL = new DAOmySQL();
		daoneo4j = new DAOneo4j();
		students = new ArrayList<Student>();
	}

	// Setting the students = to loadStudents in the mySql DAO
	public void loadStudents() throws Exception {

		if (daomySQL != null) {
			try {
				students = daomySQL.loadStudents();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Get the array of students
	public ArrayList<Student> getStudents() {
		return students;
	}

	// Set the arraylist of students
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	// Adding a new Student to both mySQL and neo4j databases, both these are called
	// from the daomysql and daoneo4j DAOS
	// Error handing for the different exceptions that could arise in mySQL and
	// neo4j.
	public String addNewStudent(Student s) {
		try {
			daomySQL.addNewStudent(s);
			daoneo4j.addStudent(s);
			FacesMessage message = new FacesMessage(s.getName() + "  Has Been Added ");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "list_students.xhtml";

		} catch (MySQLIntegrityConstraintViolationException e) {
			FacesMessage message = new FacesMessage(
					"Error: Duplicate Primary key" + e.getMessage() + "Please check " + s.getcID());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		} catch (CommunicationsException e) {
			FacesMessage message = new FacesMessage("ERROR: Cannot connect to Database " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		} catch (ServiceUnavailableException e) {
			FacesMessage message = new FacesMessage(
					"ERROR: Service not available or offine" + s.getName() + "has NOT been added to neo4j");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		} catch (Neo4jException e) {
			FacesMessage message = new FacesMessage("ERROR: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("ERROR: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
	}

	
	// Deleting Student from Neo4j and mySql Databases;
	//Error handing for the different exceptions that could arise in mySQL and neo4j.
	public String DeleteStudent(Student s) {
		if (daomySQL != null) {
			try {
				daomySQL.deleteStudent(s);
				FacesMessage message = new FacesMessage(s.getName() + "  Has Been Deleted ");
				FacesContext.getCurrentInstance().addMessage(null, message);
				daoneo4j.deleteStudent(s);
				return "list_students.xhtml";

			} catch (MySQLIntegrityConstraintViolationException e) {
				FacesMessage message = new FacesMessage("ERROR Duplicate primary key" + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (CommunicationsException e) {
				FacesMessage message = new FacesMessage("ERROR: Cannot connect to Database" + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (ServiceUnavailableException e) {
				FacesMessage message = new FacesMessage(
						"ERROR: Service not available or offine" + s.getName() + "has NOT been added to neo4j");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (Neo4jException e) {
				FacesMessage message = new FacesMessage("ERROR: " + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;	
			} catch (Exception e) {
				FacesMessage message = new FacesMessage("ERROR unable to delete student " + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		}
		return null;

	}

}
