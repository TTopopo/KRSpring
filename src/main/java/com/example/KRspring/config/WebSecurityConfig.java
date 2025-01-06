package com.example.KRspring.config;

import com.example.KRspring.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/register", "/customers/main").permitAll() // Разрешить доступ к /register и /customers/main без аутентификации
                        .requestMatchers("/customers/delete/**", "/customers/update/**", "/foremans/new", "/objects/new", "/workers/new").hasRole("ADMIN")
                        .requestMatchers("/objects/new").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/workers/new").hasAnyRole("ADMIN", "FOREMAN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Настройка кастомной страницы входа
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Настройка URL для выхода
                        .logoutSuccessUrl("/login?logout") // Перенаправление после успешного выхода
                        .permitAll()
                );

        // Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        return http.build();
    }
}
