package com.example.KRspring.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    @Column(name = "customer_name", length = 50)
    private String customerName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия должна быть от 2 до 50 символов")
    @Column(name = "customer_surname", length = 50)
    private String customerSurname;

    @NotBlank(message = "Отчество не может быть пустым")
    @Size(min = 2, max = 50, message = "Отчество должно быть от 2 до 50 символов")
    @Column(name = "customer_patronymic", length = 50)
    private String customerPatronymic;

    @NotBlank(message = "Номер телефона не может быть пустым")
    @Size(min = 10, max = 15, message = "Номер телефона должен быть от 10 до 15 символов")
    @Column(name = "customer_phone_number", length = 15)
    private String customerPhoneNumber;

    @Column(name = "username", unique = true, length = 50)
    private String username;

    @OneToMany(mappedBy = "customer")
    private List<Foreman> foremen;
}
