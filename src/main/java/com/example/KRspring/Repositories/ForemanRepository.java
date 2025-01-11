package com.example.KRspring.Repositories;

import com.example.KRspring.Models.Foreman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForemanRepository extends JpaRepository<Foreman, Long> {
    List<Foreman> findByForemanName(String name);
    List<Foreman> findByForemanSurname(String surname);
    List<Foreman> findByForemanNameAndForemanSurname(String name, String surname);
    List<Foreman> findBySpecialization(String specialization);
    List<Foreman> findByQualification(String qualification);

    // Метод для поиска по имени пользователя
    Foreman findByUsername(String username);
    List<Foreman> findByForemanNameContainingAndForemanSurnameContainingAndSpecializationContainingAndQualificationContaining(String name, String surname, String specialization, String qualification);
}
