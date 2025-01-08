package com.example.KRspring.Repositories;

import com.example.KRspring.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCustomerNameAndCustomerSurname(String name, String surname);
    List<Customer> findByCustomerName(String name);
    List<Customer> findByCustomerSurname(String surname);
}
