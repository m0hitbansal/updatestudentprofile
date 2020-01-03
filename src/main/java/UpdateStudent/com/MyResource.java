package UpdateStudent.com;

import UpdateStudent.com.bean.Domain;
import UpdateStudent.com.bean.Student;
import UpdateStudent.com.resources.SessionUtil;
import org.hibernate.Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {return "Got it!";}

    public static void main(String args[]){

        Session session = SessionUtil.getSession();
        session.beginTransaction();
        Domain domain1 = new Domain();
        Domain domain2 = new Domain();
        Domain domain3 = new Domain();
        Domain domain4 = new Domain();
        Domain domain5 = new Domain();
        Domain domain6 = new Domain();

        domain1.setDiscipline("M.Tech");
        domain2.setDiscipline("M.Tech");
        domain3.setDiscipline("iM.Tech");
        domain4.setDiscipline("iM.Tech");
        domain5.setDiscipline("MS");
        domain6.setDiscipline("MS");

        domain1.setBranch("CSE");
        domain2.setBranch("ECE");
        domain3.setBranch("CSE");
        domain4.setBranch("ECE");
        domain5.setBranch("CSE");
        domain6.setBranch("ECE");

        domain1.setCode("MT");
        domain2.setCode("MT");
        domain3.setCode("IMT");
        domain4.setCode("IMT");
        domain5.setCode("MS");
        domain6.setCode("MS");

        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Student student4 = new Student();
        Student student5 = new Student();
        Student student6 = new Student();


        student1.setFirstName("Parth");
        student1.setMiddleName("");
        student1.setLastName("Trehan");
        student1.setRollNumber("MT2019074");
        student1.setEmailId("Parth.Trehan@iiitb.org");
        student1.setPhotograph("images/student/1.jpg");

        student2.setFirstName("Mohit");
        student2.setMiddleName("");
        student2.setLastName("Bansal");
        student2.setRollNumber("MT2019065");
        student2.setEmailId("Mohit.Bansal@iiitb.org");
        student2.setPhotograph("images/student/2.jpg");

        student3.setFirstName("Ankita");
        student3.setMiddleName("");
        student3.setLastName("Bisht");
        student3.setRollNumber("IMT2019074");
        student3.setEmailId("Ankita.Bisht@iiitb.org");
        student3.setPhotograph("images/student/3.jpg");

        student4.setFirstName("Shashank");
        student4.setMiddleName("");
        student4.setLastName("Agarwal");
        student4.setRollNumber("IMT2019065");
        student4.setEmailId("Shashank.Agarwal@iiitb.org");
        student4.setPhotograph("images/student/4.jpg");

        student5.setFirstName("Jayant");
        student5.setMiddleName("");
        student5.setLastName("Tiwari");
        student5.setRollNumber("MS2019074");
        student5.setEmailId("Jayant.Tiwari@iiitb.org");
        student5.setPhotograph("images/student/5.jpg");

        student6.setFirstName("Archit");
        student6.setMiddleName("");
        student6.setLastName("Semwal");
        student6.setRollNumber("MS2019065");
        student6.setEmailId("Archit.Semwal@iiitb.org");
        student6.setPhotograph("images/student/6.jpg");

        student1.setDomain(domain1);
        domain1.getStudents().add(student1);

        student2.setDomain(domain2);
        domain2.getStudents().add(student2);

        student3.setDomain(domain3);
        domain3.getStudents().add(student3);

        student4.setDomain(domain4);
        domain4.getStudents().add(student4);

        student5.setDomain(domain5);
        domain5.getStudents().add(student5);

        student6.setDomain(domain6);
        domain6.getStudents().add(student6);


        session.save(student1);
        session.save(student2);
        session.save(student3);
        session.save(student4);
        session.save(student5);
        session.save(student6);
        session.save(domain1);
        session.save(domain2);
        session.save(domain3);
        session.save(domain4);
        session.save(domain5);
        session.save(domain6);

        session.getTransaction().commit();
        session.close();


    }
}
