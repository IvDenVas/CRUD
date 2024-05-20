package com.example.crud.repositories;

import com.example.crud.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс осуществляющий работу с базой данных.
 */
@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
}
