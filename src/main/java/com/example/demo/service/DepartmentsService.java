package com.example.demo.service;

//import com.example.demo.dto.DepartmentsDto;
import com.example.demo.model.Department;

public interface DepartmentsService {
    Department getDepartmentById(Long id);
    void save(Department department);
    void deleteDepartmentByID(Long id);
//    Long getMaxId();
}
