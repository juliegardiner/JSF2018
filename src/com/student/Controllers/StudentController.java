package com.student.Controllers;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.neo4j.driver.v1.exceptions.Neo4jException;
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

	//Constuctor
	public StudentController() throws Exception {
		super();
		daomySQL = new DAOmySQL();
		daoneo4j= new DAOneo4j();
		students = new ArrayList<Student>();
	}

	public void loadStudents() throws Exception {
		//empties the list
		//students.clear();
		
		if (daomySQL !=null) {
		try {
			students = daomySQL.loadStudents();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("The student size = " + students.size());
		}
	}


	//Get the array of students
	public ArrayList<Student> getStudents() {
		return students;
	}
	//Set the arraylist of students
	public void setStudents(ArrayList<Student>students) {
		this.students = students;
}

	// Adding a new Student to both mySQL and neo4j databases
	public String addNewStudent(Student s) {
		try {
			daomySQL.addNewStudent(s);
			daoneo4j.addStudent(s);
			FacesMessage message = new FacesMessage(s.getName() +"  Has Been Added ");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "list_students.xhtml";
			
			
		} catch (MySQLIntegrityConstraintViolationException e) {
			FacesMessage message = new FacesMessage("Error: Duplicate Primary key" +e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		} catch (CommunicationsException e) {
			FacesMessage message = new FacesMessage("ERROR: Cannot connect to Database");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("ERROR: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
	}

	//Deleting Student from Neo4j and mySql Databases;
	public String DeleteStudent(Student s) {
		if (daomySQL != null) {
			try {
				daomySQL.deleteStudent(s);
				FacesMessage message = new FacesMessage(s.getName() +"  Has Been Deleted ");
				FacesContext.getCurrentInstance().addMessage(null, message);
				daoneo4j.deleteStudent(s);
				return "list_students.xhtml";

			} catch (MySQLIntegrityConstraintViolationException e) {
				FacesMessage message = new FacesMessage("ERROR Duplicate primary key"+ e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (CommunicationsException e) {
				FacesMessage message = new FacesMessage("ERROR: Cannot connect to Database" + e.getMessage());
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
