package com.example.KRspring.Services;

import com.example.KRspring.Models.Customer;
import com.example.KRspring.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElse(null);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> searchCustomers(String name, String surname) {
        if (name != null && surname != null) {
            return customerRepository.findByCustomerNameAndCustomerSurname(name, surname);
        } else if (name != null) {
            return customerRepository.findByCustomerName(name);
        } else if (surname != null) {
            return customerRepository.findByCustomerSurname(surname);
        } else {
            return customerRepository.findAll();
        }
    }

    // Другие методы
}
