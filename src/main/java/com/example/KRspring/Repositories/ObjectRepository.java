package com.example.KRspring.Repositories;

import com.example.KRspring.Models.Object;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObjectRepository extends JpaRepository<Object, Long> {
    List<Object> findByName(String name);
    List<Object> findByType(String type);
    List<Object> findByNameAndType(String name, String type);
}
