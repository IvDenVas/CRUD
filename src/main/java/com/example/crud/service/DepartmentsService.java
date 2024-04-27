package com.example.crud.service;

import com.example.crud.dto.DepartmentDto;

public interface DepartmentsService {
    DepartmentDto getDepartmentById(Long id);
    void save(DepartmentDto dto);
    void deleteDepartmentByID(Long id);
}
