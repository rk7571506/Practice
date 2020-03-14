package com.ravi.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ravi.manager.StudentManagerImpl;
import com.ravi.model.Student;
import com.ravi.response.MessageForClient;

/**
 * @author Ravi Ranjan
 */
@Path("/student")
public class StudentServiceImpl implements StudentService {

	StudentManagerImpl studentManagerImpl = null;

	public StudentServiceImpl() {
		studentManagerImpl = new StudentManagerImpl();
	}

	/* @GET METHOD */
	/*
	 * URL : http://localhost:8081/rest-project/student/get-all-students
	 * */

	@GET
	@Path("/get-all-students")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllStudents() {

		List<Student> listStudents = studentManagerImpl.getAllStudents();

		if (listStudents == null || listStudents.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
		} else {
			return Response.status(Response.Status.OK.getStatusCode()).entity(listStudents).build();
		}
	}

	/* @GET METHOD */
	/*
	 * URL : http://localhost:8081/rest-project/student/getstudent/{studentId}
	 * */
	@GET
	@Path("/getstudent/{studentId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getStudentByID(@PathParam("studentId") String studentId) {
		Student student = studentManagerImpl.getStudentByID(studentId);

		if (student == null) {
			return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
		} else {
			return Response.status(Response.Status.OK.getStatusCode()).entity(student).build();
		}
	}

	/* @POST METHOD */
	/*
	 * URL : http://localhost:8081/rest-project/student/addStudent
	 * */
	@POST
	@Path("/addStudent")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addStudent(Student student) {

		Integer studentResponse = studentManagerImpl.addStudent(student);

		if (studentResponse == null) {
			MessageForClient message = new MessageForClient(200, "Please, insert Response for student");
			return Response.status(Response.Status.OK.getStatusCode()).entity(message).build();
		} else {

			if (studentResponse == 0) {
				MessageForClient messageResponse = new MessageForClient(500, "Internal Server Error");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(messageResponse)
						.build();

			} else {
				MessageForClient messageResponse = new MessageForClient(200, "Insert Data Suceessfully.");
				return Response.status(Response.Status.OK.getStatusCode()).entity(messageResponse).build();
			}
		}
	}

	/* DELETE METHOD */
	/*
	 * URL : http://localhost:8081/rest-project/student/delstudent/{studentId}
	 * */
	@DELETE
	@Path("/delstudent/{studentId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response delStudnet(@PathParam("studentId") String studentId) {

		if (studentId == null) {
			MessageForClient messageResponse = new MessageForClient(422, "Student Id can't be null");
			return Response.status(422).entity(messageResponse).build();
		} else {

			int response = studentManagerImpl.delStudent(studentId);

			if (response == 0) {
				MessageForClient messageResponse = new MessageForClient(500, "Internal Server Error");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(messageResponse)
						.build();
			} else if(response == 1) {
				MessageForClient messageResponse = new MessageForClient(200, "Deletion is successfully.");
				return Response.status(Response.Status.OK.getStatusCode()).entity(messageResponse).build();
			}else{
				MessageForClient messageResponse = new MessageForClient(200, "No Record Found In Database corresponding subjectId:"+studentId);
				return Response.status(Response.Status.OK.getStatusCode()).entity(messageResponse).build();
			}
		}
	}
}
