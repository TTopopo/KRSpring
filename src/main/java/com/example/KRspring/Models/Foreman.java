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
public class Foreman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя прораба не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя прораба должно быть от 2 до 50 символов")
    private String foremanName;

    @NotBlank(message = "Фамилия прораба не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия прораба должна быть от 2 до 50 символов")
    private String foremanSurname;

    private String foremanPatronymic;

    @NotBlank(message = "Номер телефона прораба не может быть пустым")
    @Size(min = 10, max = 15, message = "Номер телефона прораба должен быть от 10 до 15 символов")
    private String foremanPhoneNumber;

    @NotBlank(message = "Специализация не может быть пустой")
    @Size(min = 2, max = 50, message = "Специализация должна быть от 2 до 50 символов")
    private String specialization;

    @NotBlank(message = "Квалификация не может быть пустой")
    @Size(min = 2, max = 50, message = "Квалификация должна быть от 2 до 50 символов")
    private String qualification;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_FOREMAN; // Устанавливаем роль по умолчанию

    @Column(unique = true, nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "foreman")
    private List<Worker> workers;
}
