package com.ravi.service;

import javax.ws.rs.core.Response;

/**
 * @author Ravi Ranjan
 * */
public interface StudentService {
	
	/**
	 * Method For Lookup All Students.
	 * */
	public Response getAllStudents();
	
	/**
	 * Method For Lookup Student By STUDENT_ID
	 * */
	
	public Response getStudentByID(String studentId);

}
