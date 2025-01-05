package com.example.KRspring.Controllers;

import com.example.KRspring.Models.Customer;
import com.example.KRspring.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/customers/new")
    public String showNewCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "new_customer";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/customers/new")
    public String addCustomer(@Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "new_customer";
        }
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/{id}")
    public String viewCustomerDetails(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customer_details";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/customers/edit/{id}")
    public String showEditCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "edit_customer";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/customers/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            customer.setId(id);
            return "edit_customer";
        }
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    @GetMapping("/customers/search")
    public String searchCustomers(@RequestParam(required = false) String name, @RequestParam(required = false) String surname, Model model) {
        List<Customer> customers = customerService.searchCustomers(name, surname);
        model.addAttribute("customers", customers);
        return "customers";
    }
}
