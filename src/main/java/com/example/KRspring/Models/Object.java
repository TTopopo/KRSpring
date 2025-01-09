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
public class Object {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Тип не может быть пустым")
    @Size(min = 2, max = 50, message = "Тип должен быть от 2 до 50 символов")
    private String type;

    @NotBlank(message = "Название не может быть пустым")
    @Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
    private String name;

    @NotBlank(message = "Адрес не может быть пустым")
    @Size(min = 2, max = 255, message = "Адрес должен быть от 2 до 255 символов")
    private String address;

    @Enumerated(EnumType.STRING)
    private ObjectStatus status = ObjectStatus.NOT_STARTED; // Устанавливаем статус по умолчанию

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "foreman_id")
    private Foreman foreman;

    @OneToMany(mappedBy = "object")
    private List<WorkerObject> workerObjects;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = ObjectStatus.NOT_STARTED;
        }
    }
}
