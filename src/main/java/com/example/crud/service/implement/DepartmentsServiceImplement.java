package com.example.crud.service.implement;

import com.example.crud.dto.DepartmentDto;
import com.example.crud.repositories.DepartmentsRepo;
import com.example.crud.model.Department;
import com.example.crud.service.DepartmentsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DepartmentsServiceImplement implements DepartmentsService {
    private DepartmentsRepo repo;
    private ModelMapper mapper;

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = repo.findById(id).orElseThrow(() -> new RuntimeException("Department " +
                "с таким ID нет в базе данных!"));
        return mapper.map(department, DepartmentDto.class);
    }

    @Override
    public void save(DepartmentDto dto) {
        Department department = mapper.map(dto, Department.class);
        if (department.getId() == null) {
            repo.save(department);
        } else {
            findByIdOrElseThrow(dto.getId());
            department.setModificationDate(LocalDateTime.now());
            repo.updateDepartmentById(department.getName(),
                    department.getAddress(),
                    department.getModificationDate(),
                    department.getId());
        }
    }

    @Override
    public void deleteDepartmentByID(Long id) {
        repo.deleteById(id);
    }

    public void findByIdOrElseThrow(Long id) {
        repo.findById(id).orElseThrow(() -> new RuntimeException("Department " +
                "с таким ID нет в базе данных! Введите правильный id!"));
    }
}