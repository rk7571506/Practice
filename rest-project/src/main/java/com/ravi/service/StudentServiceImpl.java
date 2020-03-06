package com.ravi.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ravi.manager.StudentManagerImpl;
import com.ravi.model.Student;


/**
 * @author Ravi Ranjan
 */
@Path("/student")
public class StudentServiceImpl implements StudentService {

	
	StudentManagerImpl studentManagerImpl;
	
	public StudentServiceImpl() {
	studentManagerImpl = new StudentManagerImpl();	
	}
	
	@GET
	@Path("/getAllStudents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents() {
		
		List<Student> listStudents = studentManagerImpl.getAllStudents();
		
		if(listStudents==null){
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		else
		{
			return Response.status(Response.Status.OK).entity(listStudents).build();
		}
	}

	public Response getStudentByID(String StudentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
