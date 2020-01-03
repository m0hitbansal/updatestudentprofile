package UpdateStudent.com.controller;

import UpdateStudent.com.bean.Student;
import UpdateStudent.com.resources.SessionUtil;
import UpdateStudent.com.service.StudentService;
import UpdateStudent.com.service.impl.StudentServiceImpl;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

@Path("/profile")
public class StudentController {

    private StudentService studentService = new StudentServiceImpl();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response showAllStudent() {
        List<Student> studentList = studentService.findAll();
        if (studentList == null)
            return Response.noContent().build();
        return Response.ok().entity(studentList).build();
    }

    @Path("/{userid}")
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response studentDetails(@PathParam("userid") String Id){
        Student student = new Student();
        try {
            student = studentService.find(Integer.parseInt((Id)));
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return Response.status(200).entity(student).build();
    }

    @POST
    @Path("/update/{userid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response updateDetails(@PathParam("userid") String Id,
                                  @FormDataParam("firstName") String firstName,
                                  @FormDataParam("middleName") String middleName,
                                  @FormDataParam("lastName") String lastName,
                                  @FormDataParam("emailId") String emailAddress,
                                  @FormDataParam("rollNumber") String rollNumber,
                                  @FormDataParam("domainId") Integer domainId,
                                  @FormDataParam("photograph") InputStream photograph,
                                  @FormDataParam("photograph") FormDataContentDisposition fileDetail){
        // Error Section
        if(domainId == 0)
            return Response.status(402).build();
        else if(firstName.length() == 0)
            return Response.status(405).build();
        else if(emailAddress.length() == 0)
            return Response.status(406).build();
        else if(rollNumber.length() == 0)
            return Response.status(407).build();

        Student student = studentService.find(Integer.parseInt((Id)));;
        student.setFirstName(firstName);
        student.setMiddleName(middleName);
        student.setLastName(lastName);
        student.setEmailId(emailAddress);
        if(rollNumber != student.getRollNumber() && studentService.updateRollNumber(student,rollNumber)) {
            studentService.save(student, photograph, fileDetail, domainId);
            student = studentService.find(Integer.parseInt(Id));
            SessionUtil.clearCache();
            return Response.status(200).entity(student).build();
        }
        else
            return Response.status(403).build();
    }
}
