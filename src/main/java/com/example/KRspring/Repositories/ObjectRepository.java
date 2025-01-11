package com.example.KRspring.Repositories;

import com.example.KRspring.Models.Object;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObjectRepository extends JpaRepository<Object, Long> {
    List<Object> findByName(String name);
    List<Object> findByType(String type);
    List<Object> findByNameAndType(String name, String type);
    List<Object> findByCustomerUsername(String username);
    List<Object> findByNameAndCustomerUsername(String name, String username);
    List<Object> findByTypeAndCustomerUsername(String type, String username);
    List<Object> findByNameAndTypeAndCustomerUsername(String name, String type, String username);

    // Методы для поиска по имени прораба
    List<Object> findByForemanForemanName(String foremanName);
    List<Object> findByNameAndForemanForemanName(String name, String foremanName);
    List<Object> findByTypeAndForemanForemanName(String type, String foremanName);
    List<Object> findByNameAndTypeAndForemanForemanName(String name, String type, String foremanName);

    // Метод для поиска объектов по идентификатору прораба
    List<Object> findByForemanId(Long foremanId);
}
