package com.student.Controllers;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import org.neo4j.driver.v1.exceptions.Neo4jException;
import com.mysql.jdbc.CommunicationsException;
import javax.faces.context.*;
import com.student.DAOs.DAOmySQL;
import com.student.DAOs.DAOneo4j;
import com.student.Models.*;

@ManagedBean
public class StudentController {
	private ArrayList<Student> students;
	private DAOmySQL daomySQL;
	private DAOneo4j daoneo4j;

	public StudentController() throws Exception {
		super();
		daomySQL = new DAOmySQL();
		students = new ArrayList<>();
	}

	public void loadStudents() {
		try {
			students = daomySQL.loadStudents();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("The student size = " + students.size());
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	//Adding a new Student to both mySQL and neo4j databases
	public String addNewStudent(Student s) {
		try {
			daomySQL.addNewStudent(s);
			daoneo4j.addStudent(s);
			return "index.xhtml";
		
		} catch (CommunicationsException e) {
			System.out.println("Cannot connect to Database");
			e.printStackTrace();
			return null;
		} catch (SQLIntegrityConstraintViolationException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Duplicate Entry"));
			return null;
		} catch (SQLException e) {
			System.out.println("Student already exists in the database");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("Unknown Exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String deleteStudent(String sid){
		try {
			daomySQL.deleteStudent(sid);
			daoneo4j.deleteStudent(sid);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Neo4jException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Referential Integrity: Student cannot be removed at this time"));
			
		}
		return "list_students.xhtml";
		
	

	}

}
