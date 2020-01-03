package UpdateStudent.com.service;

import UpdateStudent.com.bean.Student;
import UpdateStudent.com.dao.StudentDao;
import UpdateStudent.com.service.impl.DomainServiceImpl;
import UpdateStudent.com.service.impl.FileServiceImpl;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;
import java.util.List;

public interface StudentService {
    
    StudentDao studentDao = new StudentDao();
    DomainService domainService = new DomainServiceImpl();
    FileService fileService = new FileServiceImpl();
    void save(Student student,
                           InputStream photograph,
                           FormDataContentDisposition fileDetail,
                           Integer domainId);
    
    Student find(Integer id);
    boolean updateRollNumber(Student student,String rollNumber);
    Student findByRollNumber(String rollNumber);

    List<Student> findAll();
}
