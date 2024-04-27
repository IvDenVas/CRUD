package com.example.crud.repositories;

import com.example.crud.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface DepartmentsRepo extends JpaRepository<Department, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Department SET name = :name,address = :address, modificationDate = :modificationDate WHERE id = :id")
    void updateDepartmentById(String name, String address, LocalDateTime modificationDate, long id);
}
