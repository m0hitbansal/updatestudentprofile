package UpdateStudent.com.service.impl;

import UpdateStudent.com.bean.Domain;
import UpdateStudent.com.bean.Student;
import UpdateStudent.com.service.StudentService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    
    @Override
    public void save(Student student,
                     InputStream photograph,
                     FormDataContentDisposition fileDetail,
                     Integer domainId) {
        Domain domain = domainService.find(domainId);
        student.setDomain(domain);
        if(fileDetail.getFileName().length() == 0) {
            studentDao.update(student);
        }
        else {

            String rollNumber = student.getRollNumber();
            String tempFileDetail = fileDetail.getFileName();
            int length = tempFileDetail.length();
            String photographPath = "images/student/" + student.getId() + ".jpg";
            if (!fileService.upload(photograph, photographPath))
                System.out.println("File Upload Error!");
            else {
                student.setPhotograph(photographPath);
                studentDao.update(student);
            }
        }
    }
    
    @Override
    public Student find(Integer id) {
        return studentDao.find(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }
    
    @Override
    public Student findByRollNumber(String rollNumber) {
        return studentDao.findByRollNumber(rollNumber);
    }
    @Override
    public boolean updateRollNumber(Student student,String rollNumber) {
        if (studentDao.isUniqueRollNumber(student.getId(), rollNumber)){
            student.setRollNumber(rollNumber);
            studentDao.update(student);
            return true;
        }
        else
            return false;
    }

    private String generateRollNumber(Domain domain) {
        String lastRollNumber = studentDao.getLastRollNumber(domain);
        Integer newSerialNumber;
        if (lastRollNumber == null)
            newSerialNumber = 1;
        else {
            String serialNumber = lastRollNumber.substring(lastRollNumber.length() - 3);
            newSerialNumber = Integer.parseInt(serialNumber) + 1;
        }
        String tempSerialNumber = newSerialNumber.toString();
        switch (tempSerialNumber.length()) {
            case 1:
                tempSerialNumber = "00" + tempSerialNumber;
                break;
            case 2:
                tempSerialNumber = "0" + tempSerialNumber;
                break;
            default:
                break;
        }
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());
        String newRollNumber = domain.getCode() + currentYear + tempSerialNumber;
        return newRollNumber;
    }
}