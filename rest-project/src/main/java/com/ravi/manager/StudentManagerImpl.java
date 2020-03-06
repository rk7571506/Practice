package com.ravi.manager;

import java.util.List;

import com.ravi.dao.StudentDaoImpl;
import com.ravi.model.Student;

public class StudentManagerImpl implements StudentManager {

	StudentDaoImpl studentDaoImpl;

	public StudentManagerImpl() {
		studentDaoImpl = new StudentDaoImpl();
	}

	public List<Student> getAllStudents() {

		List<Student> listStudent = studentDaoImpl.getAllStudent();

		if (listStudent == null) {
			return null;
		} else {
			return listStudent;
		}
	}

	public Student getStudentByID(String studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
