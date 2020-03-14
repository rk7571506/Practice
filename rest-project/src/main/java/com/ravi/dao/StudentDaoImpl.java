package com.ravi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ravi.model.Student;
import com.ravi.model.StudentAddress;

public class StudentDaoImpl implements StudentDao {

	private String URL = "jdbc:oracle:thin:@//localhost:1521/XE";
	private String USER_NAME = "SYSTEM";
	private String PASSWORD = "SYSTEM";
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;

	public StudentDaoImpl() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Student> getAllStudent() {

		String queryAllStudent = "SELECT * FROM STUDENT_REST";
		List<Student> listStudents = new ArrayList<Student>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryAllStudent);
			System.out.println("Query Successfully Executed.");

			while (resultSet.next()) {
				Student student = new Student();

				String studentRestId = resultSet.getString("STUDENT_REST_ID");
				String studentName = resultSet.getString("STUDENT_NAME");
				String studentRollNo = resultSet.getString("STUDENT_ROLL_NO");

				String QueryForAddress = "SELECT STREET,CITY,STATE,PINCODE FROM STUDENT_ADDRESS_REST WHERE STUDENT_REST_ID_FK = ?";
				preparedStatement = connection.prepareStatement(QueryForAddress);
				preparedStatement.setString(1, studentRestId);
				ResultSet res = preparedStatement.executeQuery();

				while (res.next()) {
					StudentAddress studentAddress = new StudentAddress();
					String street = res.getString("STREET");
					String city = res.getString("CITY");
					String state = res.getString("STATE");
					String pincode = res.getString("PINCODE");

					student.setStudentId(studentRestId);
					student.setStudentName(studentName);
					student.setStudentRollNo(studentRollNo);
					studentAddress.setStreet(street);
					studentAddress.setCity(city);
					studentAddress.setState(state);
					studentAddress.setPincode(pincode);
					student.setStudentAddress(studentAddress);
				}
				listStudents.add(student);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
				resultSet.close();
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listStudents;
	}

	public Student getStudentByID(String studentId) {
		Student student = new Student();
		try {
			String queryForGivenId = "SELECT * FROM STUDENT_REST WHERE STUDENT_REST_ID = ?";
			preparedStatement = connection.prepareStatement(queryForGivenId);
			preparedStatement.setString(1, studentId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next() == true) {
				String studentRestID = resultSet.getString("STUDENT_REST_ID");
				String studentName = resultSet.getString("STUDENT_NAME");
				String studentRollNo = resultSet.getString("STUDENT_ROLL_NO");

				student.setStudentId(studentRestID);
				student.setStudentName(studentName);
				student.setStudentRollNo(studentRollNo);

				String queryForAddress = "SELECT * FROM STUDENT_ADDRESS_REST WHERE STUDENT_REST_ID_FK = ?";
				preparedStatement = connection.prepareStatement(queryForAddress);
				preparedStatement.setString(1, studentId);
				ResultSet res = preparedStatement.executeQuery();

				while (res.next()) {
					StudentAddress studentAddress = new StudentAddress();
					String street = res.getString("STREET");
					String city = res.getString("CITY");
					String state = res.getString("STATE");
					String pincode = res.getString("PINCODE");

					studentAddress.setStreet(street);
					studentAddress.setCity(city);
					studentAddress.setState(state);
					studentAddress.setPincode(pincode);
					student.setStudentAddress(studentAddress);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				preparedStatement.close();
				resultSet.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return student;
	}

	public int addStudent(Student student) {

		String studentTableData = "INSERT INTO STUDENT_REST(STUDENT_REST_ID, STUDENT_NAME, STUDENT_ROLL_NO) VALUES(?,?,?)";

		try {
			preparedStatement = connection.prepareStatement(studentTableData);
			preparedStatement.setString(1, student.getStudentId());
			preparedStatement.setString(2, student.getStudentName());
			preparedStatement.setString(3, student.getStudentRollNo());

			int response = preparedStatement.executeUpdate();

			if (response > 0) {

				String studentAddressTableData = "INSERT INTO STUDENT_ADDRESS_REST(STUDENT_ADDRESS_ID, STREET,CITY,STATE,PINCODE,STUDENT_REST_ID_FK) VALUES(?,?,?,?,?,?)";

				PreparedStatement preparedStatementStudent = connection.prepareStatement(studentAddressTableData);
				UUID idForAddress = UUID.randomUUID();
				String uuidForStudent = String.valueOf(idForAddress).replaceAll("-", "");

				preparedStatementStudent.setString(1, uuidForStudent);
				preparedStatementStudent.setString(2, student.getStudentAddress().getStreet());
				preparedStatementStudent.setString(3, student.getStudentAddress().getCity());
				preparedStatementStudent.setString(4, student.getStudentAddress().getState());
				preparedStatementStudent.setString(5, student.getStudentAddress().getPincode());
				preparedStatementStudent.setString(6, student.getStudentId());

				int responseAddress = preparedStatementStudent.executeUpdate();

				if (responseAddress > 0) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return 0;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int delStudent(String studentId) {

		String delStudentAddressQuery = "DELETE FROM STUDENT_ADDRESS_REST WHERE STUDENT_REST_ID_FK = ?";
		try {
			preparedStatement = connection.prepareStatement(delStudentAddressQuery);
			preparedStatement.setString(1, studentId);
			int response = preparedStatement.executeUpdate();
			if (response > 0) {

				String delstudentQuery = "DELETE FROM STUDENT_REST WHERE STUDENT_REST_ID = ?";
				preparedStatement = connection.prepareStatement(delstudentQuery);
				preparedStatement.setString(1, studentId);
				int responseConfirm = preparedStatement.executeUpdate();

				if (responseConfirm > 0) {
					return 1;
				} else {
					return 0;
				}

			} else {
				return 2;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
