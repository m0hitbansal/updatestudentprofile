package UpdateStudent.com.service.impl;

import UpdateStudent.com.bean.Domain;
import UpdateStudent.com.service.DomainService;

import java.util.List;

public class DomainServiceImpl implements DomainService {
    
    @Override
    public void save(Domain domain) {
        domainDao.save(domain);
    }
    
    @Override
    public Domain find(Integer id) {
        return domainDao.find(id);
    }
    
    @Override
    public Domain findByCode(String code) {
        return domainDao.findByCode(code);
    }
    
    @Override
    public List<Domain> findAll() {
        return domainDao.findAll();
    }
}
