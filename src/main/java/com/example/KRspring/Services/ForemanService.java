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

    public List<Foreman> searchForemans(String name, String surname, String specialization, String qualification) {
        if (name != null && surname != null) {
            return foremanRepository.findByForemanNameAndForemanSurname(name, surname);
        } else if (name != null) {
            return foremanRepository.findByForemanName(name);
        } else if (surname != null) {
            return foremanRepository.findByForemanSurname(surname);
        } else if (specialization != null) {
            return foremanRepository.findBySpecialization(specialization);
        } else if (qualification != null) {
            return foremanRepository.findByQualification(qualification);
        } else {
            return foremanRepository.findAll();
        }
    }

    // Метод для поиска по имени пользователя
    public Foreman getForemanByUsername(String username) {
        return foremanRepository.findByUsername(username);
    }
}
