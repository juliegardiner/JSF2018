package com.student.DAOs;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.exceptions.Neo4jException;

import static org.neo4j.driver.v1.Values.*;
import com.student.Models.Student;

public class DAOneo4j {

	// Setting up the connection variables for neo4j/
	private Session session;
	private Driver dvr;

	// Constructor
	public DAOneo4j() {
		super();

	}

	// Adding a new Student node to Neo4j
	public void addStudent(Student student) throws Neo4jException {
		//Connection Setup
		dvr = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4jdb"));
		session = dvr.session();

		session.writeTransaction(new TransactionWork<String>() {

			@Override
			public String execute(Transaction tx) {//neo4J Query for Adding a new student node
				tx.run("CREATE (:STUDENT {name:{name}, address:{address}})",
						parameters("name", student.getName(), "address", student.getAddress()));
				return null;
			}

		});
		// CLOSE SESSION AND DB DRIVER
		session.close();
		dvr.close();

	}

	// Deleting student node using name
	public void deleteStudent(Student student) throws Neo4jException {
		//Connection Setup
		dvr = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4jdb"));
		session = dvr.session();

		session.writeTransaction(new TransactionWork<String>() {

			@Override
			public String execute(Transaction tx) {//neo4J Query for deleting the student node
				tx.run("MATCH (s:STUDENT) WHERE s.name = {studentName} DELETE s", parameters("studentName", student));
				return null;
			}

		});
		// CLOSE SESSION AND DB DRIVER
		session.close();
		dvr.close();

	}

}
