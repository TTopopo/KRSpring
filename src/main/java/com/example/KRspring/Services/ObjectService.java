package com.example.KRspring.Services;

import com.example.KRspring.Models.Object;
import com.example.KRspring.Repositories.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private CustomerService customerService;

    public List<Object> getAllObjects() {
        return objectRepository.findAll();
    }

    public Object getObjectById(Long id) {
        return objectRepository.findById(id).orElse(null);
    }

    public void saveObject(Object object) {
        objectRepository.save(object);
    }

    public void deleteObject(Long id) {
        objectRepository.deleteById(id);
    }

    public List<Object> searchObjects(String name, String type) {
        if (name != null && type != null) {
            return objectRepository.findByNameAndType(name, type);
        } else if (name != null) {
            return objectRepository.findByName(name);
        } else if (type != null) {
            return objectRepository.findByType(type);
        } else {
            return objectRepository.findAll();
        }
    }

    public List<Object> getObjectsByCustomerUsername(String username) {
        return objectRepository.findByCustomerUsername(username);
    }

    public boolean isObjectOwnedByCustomer(Object object, String username) {
        return object.getCustomer() != null && object.getCustomer().getUsername().equals(username);
    }

    public List<Object> searchObjectsByCustomer(String name, String type, String username) {
        if (name != null && type != null) {
            return objectRepository.findByNameAndTypeAndCustomerUsername(name, type, username);
        } else if (name != null) {
            return objectRepository.findByNameAndCustomerUsername(name, username);
        } else if (type != null) {
            return objectRepository.findByTypeAndCustomerUsername(type, username);
        } else {
            return objectRepository.findByCustomerUsername(username);
        }
    }

    public List<Object> getObjectsByForemanName(String foremanName) {
        return objectRepository.findByForemanForemanName(foremanName);
    }

    public List<Object> searchObjectsByForeman(String name, String type, String foremanName) {
        if (name != null && type != null) {
            return objectRepository.findByNameAndTypeAndForemanForemanName(name, type, foremanName);
        } else if (name != null) {
            return objectRepository.findByNameAndForemanForemanName(name, foremanName);
        } else if (type != null) {
            return objectRepository.findByTypeAndForemanForemanName(type, foremanName);
        } else {
            return objectRepository.findByForemanForemanName(foremanName);
        }
    }
}
