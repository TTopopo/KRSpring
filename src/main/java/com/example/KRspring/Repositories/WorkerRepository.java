package com.example.KRspring.Repositories;

import com.example.KRspring.Models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    List<Worker> findByName(String name);
    List<Worker> findBySurname(String surname);
    List<Worker> findByNameAndSurname(String name, String surname);
}