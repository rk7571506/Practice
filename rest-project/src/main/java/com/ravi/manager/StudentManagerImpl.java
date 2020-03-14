package com.ravi.manager;

import java.util.List;

import com.ravi.dao.StudentDaoImpl;
import com.ravi.model.Student;

public class StudentManagerImpl implements StudentManager {

	StudentDaoImpl studentDaoImpl = null;

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

		Student student = studentDaoImpl.getStudentByID(studentId);

		if (student == null) {
			return null;
		} else {
			return student;
		}
	}

	public Integer addStudent(Student student) {

		if (student == null) {
			return null;
		} else {

			int studentResponse = studentDaoImpl.addStudent(student);
			if (studentResponse == 0) {

				return studentResponse;
			} else{
				return studentResponse;
			}
		}

	}

	public int delStudent(String studentId) {
		
		int response = studentDaoImpl.delStudent(studentId);
		
		if(response==0){
			return 0;
		}else if(response==1){
			return 1;
		}else{
			return 2;
		}
	}

}
