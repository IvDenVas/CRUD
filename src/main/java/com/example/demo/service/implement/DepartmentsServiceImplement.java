package com.example.demo.service.implement;

import com.example.demo.repositorys.DepartmentsRepo;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class DepartmentsServiceImplement implements DepartmentsService {

    private DepartmentsRepo repo;
    @Override
    public Department getDepartmentById(Long id) {
        return  repo.findById(id).get();
    }

    @Override
    public void save(Department department) {
        if(department.getId() == null) repo.save(department);
        else {
            department.setModificationDate(LocalDateTime.now().format(DateTimeFormatter
                    .ofPattern("yy-MM-dd hh:mm:ss")));
            repo.updateDepartmentById(department.getName(), department.getAddress(), department.getModificationDate(),
                    department.getId());
        }
    }

    @Override
    public void deleteDepartmentByID(Long id) {
        repo.deleteById(id);
    }
}
