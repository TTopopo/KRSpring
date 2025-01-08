package com.example.KRspring.Models;

import jakarta.persistence.*;
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

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 50)
    private String customerName;

    @Column(length = 50)
    private String customerSurname;

    @Column(length = 50)
    private String customerPatronymic;

    @Column(name = "customer_phone_number", length = 15)
    private String customerPhoneNumber;

    @Column(length = 50)
    private String foremanName;

    @Column(length = 50)
    private String foremanSurname;

    @Column(length = 50)
    private String foremanPatronymic;

    @Column(name = "foreman_phone_number", length = 15)
    private String foremanPhoneNumber;

    @Column(length = 50)
    private String specialization;

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
