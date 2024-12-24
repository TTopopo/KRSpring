package com.example.KRspring.Repositories;

import com.example.KRspring.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByName(String name);
    List<Customer> findBySurname(String surname);
    List<Customer> findByNameAndSurname(String name, String surname);
}
