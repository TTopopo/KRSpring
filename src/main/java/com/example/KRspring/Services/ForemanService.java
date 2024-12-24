package com.example.KRspring.Services;

import com.example.KRspring.Models.Foreman;
import com.example.KRspring.Repositories.ForemanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForemanService {
    @Autowired
    private ForemanRepository foremanRepository;

    public List<Foreman> getAllForemans() {
        return foremanRepository.findAll();
    }

    public Foreman saveForeman(Foreman foreman) {
        return foremanRepository.save(foreman);
    }

    public Foreman getForemanById(Long id) {
        Optional<Foreman> optionalForeman = foremanRepository.findById(id);
        return optionalForeman.orElse(null);
    }

    public void deleteForeman(Long id) {
        foremanRepository.deleteById(id);
    }

    public List<Foreman> searchForemans(String name, String surname) {
        if (name != null && surname != null) {
            return foremanRepository.findByNameAndSurname(name, surname);
        } else if (name != null) {
            return foremanRepository.findByName(name);
        } else if (surname != null) {
            return foremanRepository.findBySurname(surname);
        } else {
            return foremanRepository.findAll();
        }
    }
}
