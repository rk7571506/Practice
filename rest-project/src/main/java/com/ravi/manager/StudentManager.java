package com.ravi.manager;

import java.util.List;

import com.ravi.model.Student;

public interface StudentManager {
	
	public List<Student> getAllStudents();
	
	public Student getStudentByID(String studentId);

}
