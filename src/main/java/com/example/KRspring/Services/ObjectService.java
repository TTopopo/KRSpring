package com.example.KRspring.Services;

import com.example.KRspring.Models.Object;
import com.example.KRspring.Repositories.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectService {
    @Autowired
    private ObjectRepository objectRepository;

    public List<Object> getAllObjects() {
        return objectRepository.findAll();
    }

    public Object saveObject(Object object) {
        return objectRepository.save(object);
    }

    public Object getObjectById(Long id) {
        Optional<Object> optionalObject = objectRepository.findById(id);
        return optionalObject.orElse(null);
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
}
