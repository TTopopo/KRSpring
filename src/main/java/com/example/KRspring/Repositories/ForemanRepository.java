package com.example.KRspring.Repositories;

import com.example.KRspring.Models.Foreman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForemanRepository extends JpaRepository<Foreman, Long> {
    List<Foreman> findByName(String name);
    List<Foreman> findBySurname(String surname);
    List<Foreman> findByNameAndSurname(String name, String surname);
}
