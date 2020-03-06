package com.ravi.dao;

import java.util.List;

import com.ravi.model.Student;

public interface StudentDao {
	
	public List<Student> getAllStudent();
	
	public Student getStudentByID(String StudentId);

}
