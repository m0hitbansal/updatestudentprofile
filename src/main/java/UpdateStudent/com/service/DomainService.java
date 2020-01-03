package UpdateStudent.com.service;

import UpdateStudent.com.bean.Domain;
import UpdateStudent.com.dao.DomainDao;

import java.util.List;

public interface DomainService {
    
    DomainDao domainDao = new DomainDao();
    void save(Domain domain);
    Domain find(Integer id);
    Domain findByCode(String code);
    List<Domain> findAll();
    
}
