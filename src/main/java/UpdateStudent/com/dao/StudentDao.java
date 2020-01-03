package UpdateStudent.com.dao;

import UpdateStudent.com.bean.Domain;
import UpdateStudent.com.bean.Student;
import UpdateStudent.com.resources.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDao {
    public void update(Student student) {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.update(student);

        transaction.commit();
        session.close();
    }
    public Student find(Integer id) {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Student student = session.get(Student.class, id);
        transaction.commit();
        session.close();
        student.getDomain().setStudents(null);
        return student;
    }

    public Student findByRollNumber(String rollNumber) {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Student WHERE rollNumber = :roll_number";
        Query query = session.createQuery(hql);
        query.setParameter("roll_number", rollNumber);
        Student student = (Student) query.getSingleResult();

        transaction.commit();
        session.close();
        return student;
    }
    public boolean isUniqueRollNumber(int Id, String rollNumber){
        List<Student> student;
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Student WHERE rollNumber = :roll_number AND id <> :i_d";
        Query query = session.createQuery(hql);
        query.setParameter("roll_number",rollNumber);
        query.setParameter("i_d",Id);
        student = query.list();
        transaction.commit();
        session.close();
        return student.isEmpty();
    }

    public List<Student> findAll() {

        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Student";
        Query query = session.createQuery(hql);
        List<Student> studentList = query.list();
        transaction.commit();
        session.close();
        studentList.parallelStream().forEach((student) -> student.getDomain().setStudents(null));
        return studentList;
    }

    public String getLastRollNumber(Domain domain) {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT s.rollNumber FROM Student s, Domain d WHERE s.domain = d AND d = :givenDomain ORDER BY s.rollNumber DESC";
        Query query = session.createQuery(hql);
        query.setParameter("givenDomain", domain);
        String rollNumber = (String) query.list().get(0);

        transaction.commit();
        session.close();
        return rollNumber;
    }
}
