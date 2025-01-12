package com.example.KRspring.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя клиента не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя клиента должно быть от 2 до 50 символов")
    private String customerName;

    @NotBlank(message = "Фамилия клиента не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия клиента должна быть от 2 до 50 символов")
    private String customerSurname;

    private String customerPatronymic;

    @NotBlank(message = "Номер телефона клиента не может быть пустым")
    @Size(min = 10, max = 15, message = "Номер телефона клиента должен быть от 10 до 15 символов")
    private String customerPhoneNumber;

    @Email(message = "Некорректный адрес электронной почты")
    @NotBlank(message = "Email не может быть пустым")
    @Size(max = 255, message = "Email должен быть не длиннее 255 символов")
    private String customerEmail;

    @Column(unique = true, nullable = false)
    private String username;

    @OneToMany(mappedBy = "customer")
    private List<Foreman> foremen;

    @OneToMany(mappedBy = "customer")
    private List<Object> objects;
}
