package com.example.KRspring.Models;

import com.example.KRspring.validation.CustomerValidationGroup;
import com.example.KRspring.validation.ForemanValidationGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 50, message = "Имя пользователя должно быть от 3 до 50 символов")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    private String password;

    @NotNull(message = "Роль не может быть пустой")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Size(max = 50, message = "Имя клиента не может превышать 50 символов", groups = CustomerValidationGroup.class)
    @Column(length = 50)
    private String customerName;

    @Size(max = 50, message = "Фамилия клиента не может превышать 50 символов", groups = CustomerValidationGroup.class)
    @Column(length = 50)
    private String customerSurname;

    @Size(max = 50, message = "Отчество клиента не может превышать 50 символов", groups = CustomerValidationGroup.class)
    @Column(length = 50)
    private String customerPatronymic;

    @Pattern(regexp = "\\d{11}", message = "Номер телефона клиента должен содержать 11 цифр", groups = CustomerValidationGroup.class)
    @Column(name = "customer_phone_number", length = 15)
    private String customerPhoneNumber;

    @Email(message = "Некорректный адрес электронной почты", groups = CustomerValidationGroup.class)
    @NotBlank(message = "Email не может быть пустым", groups = CustomerValidationGroup.class)
    @Size(max = 255, message = "Email должен быть не длиннее 255 символов", groups = CustomerValidationGroup.class)
    @Column(length = 255)
    private String customerEmail;

    @Size(max = 50, message = "Имя прораба не может превышать 50 символов", groups = ForemanValidationGroup.class)
    @Column(length = 50)
    private String foremanName;

    @Size(max = 50, message = "Фамилия прораба не может превышать 50 символов", groups = ForemanValidationGroup.class)
    @Column(length = 50)
    private String foremanSurname;

    @Size(max = 50, message = "Отчество прораба не может превышать 50 символов", groups = ForemanValidationGroup.class)
    @Column(length = 50)
    private String foremanPatronymic;

    @Pattern(regexp = "\\d{11}", message = "Номер телефона прораба должен содержать 11 цифр", groups = ForemanValidationGroup.class)
    @Column(name = "foreman_phone_number", length = 15)
    private String foremanPhoneNumber;

    @Size(max = 50, message = "Специализация не может превышать 50 символов", groups = ForemanValidationGroup.class)
    @Column(length = 50)
    private String specialization;

    @Size(max = 50, message = "Квалификация не может превышать 50 символов", groups = ForemanValidationGroup.class)
    @Column(length = 50)
    private String qualification;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
