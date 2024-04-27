package com.example.demo.service.implement;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repositorys.DepartmentsRepo;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentsService;
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
        Department department = repo.findById(id).get();
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

    public Department findByIdOrElseThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Department с таким ID нет в базе данных!"));
    }
}