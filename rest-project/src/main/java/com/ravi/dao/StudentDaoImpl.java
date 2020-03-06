package com.ravi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Loading Driver Successfully");
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 
		{
			connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
			System.out.println("Connection is estd. successfully.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public List<Student> getAllStudent() {
		// TODO Auto-generated method stub
		String queryAllStudent = "SELECT * FROM STUDENT_REST";
		List<Student> listStudents = new ArrayList<Student>();
		try 
		{
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryAllStudent);
			System.out.println("Query Successfully Executed.");
			
			while(resultSet.next())
			{
				Student student = new Student();
				 
				String studentRestId = resultSet.getString("STUDENT_REST_ID");
				String studentName = resultSet.getString("STUDENT_NAME");
				String studentRollNo = resultSet.getString("STUDENT_ROLL_NO");
				
				String QueryForAddress = "SELECT STREET,CITY,STATE,PINCODE FROM STUDENT_ADDRESS_REST WHERE STUDENT_REST_ID_FK = ?";
				preparedStatement = connection.prepareStatement(QueryForAddress);
				preparedStatement.setString(1, studentRestId);
				ResultSet res = preparedStatement.executeQuery();
				
				while(res.next())
				{
					StudentAddress studentAddress = new StudentAddress();
					String street = res.getString("STREET");
					String city = res.getString("CITY");
					String state = res.getString("STATE");
					String pincode =res.getString("PINCODE");
					
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
		}finally {
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

	
	public Student getStudentByID(String StudentId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
