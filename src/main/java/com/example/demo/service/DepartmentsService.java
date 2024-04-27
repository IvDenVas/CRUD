package com.example.demo.service;

import com.example.demo.dto.DepartmentDto;

public interface DepartmentsService {
    DepartmentDto getDepartmentById(Long id);
    void save(DepartmentDto dto);
    void deleteDepartmentByID(Long id);
}
