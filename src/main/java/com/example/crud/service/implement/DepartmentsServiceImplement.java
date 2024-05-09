package com.example.crud.service.implement;

import com.example.crud.dto.DepartmentDto;
import com.example.crud.entities.Department;
import com.example.crud.exception.NotFoundException;
import com.example.crud.repository.DepartmentRepo;
import com.example.crud.service.DepartmentsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DepartmentsServiceImplement implements DepartmentsService {
    private ModelMapper mapper;
    private DepartmentRepo repo;

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        try {
            return mapper.map(repo.findById(id).orElse(null), DepartmentDto.class);
        } catch (RuntimeException e) {
            throw new NotFoundException("Department с таким ID не найден!");
        }
    }

    @Override
    @Transactional
    public DepartmentDto save(DepartmentDto dto) {
        Department department = mapper.map(dto, Department.class);
        if (department.getId() == null) {
            department.setCreationDate(LocalDateTime.now());
            department.setModificationDate(null);
            Department department1 = repo.save(department);
            return mapper.map(department1, DepartmentDto.class);
        } else
            return updateDepartmentByIdOrElseThrow(department);
    }

    @Override
    public void deleteDepartmentByID(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else
            throw new RuntimeException("Department с таким ID не найден!");
    }

    public DepartmentDto updateDepartmentByIdOrElseThrow(Department department) {
        Department tempDepartment = repo.findById(department.getId()).orElse(null);
        if (tempDepartment != null) {
            department.setId(tempDepartment.getId());
            department.setCreationDate(tempDepartment.getCreationDate());
            department.setModificationDate(LocalDateTime.now());
            return mapper.map(repo.save(department), DepartmentDto.class);
        } else
            throw new RuntimeException("Department с таким ID нет в базе данных! Введите правильный id!");
    }
}
