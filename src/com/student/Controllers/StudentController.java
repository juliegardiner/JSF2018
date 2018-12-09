package com.student.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;

import com.mysql.jdbc.CommunicationsException;
import com.student.DAOs.DAOmySQL;
import com.student.Models.*;

@ManagedBean
public class StudentController {
	private ArrayList<Student> students;
	private DAOmySQL daomySQL;

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

	public String addNewStudent(Student s) {
		try {
			daomySQL.addNewStudent(s);
			return "index.xhtml";
		} catch (CommunicationsException e) {
			System.out.println("Cannot connect to Database");
			e.printStackTrace();
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
	//public String loadStudentCourse(Student s) {
	//	StudentCourse = DAOmySQL.loadStudentCourse(s);
	//}

}
