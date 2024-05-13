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
import java.util.Objects;

/**
 * Класс описывает методы, реализующие бизнес-логику CRUD приложения.
 */
@Service
@AllArgsConstructor
public class DepartmentsServiceImplement implements DepartmentsService {
    private ModelMapper mapper;
    private DepartmentRepo repo;

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentDto getDepartmentById(Long id) {
        try {
            return mapper.map(repo.findById(id).orElse(null), DepartmentDto.class);
        } catch (RuntimeException e) {
            throw new NotFoundException("Department с таким ID не найден!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DepartmentDto save(DepartmentDto dto) {
        Department department = mapper.map(dto, Department.class);
        if (Objects.isNull(department.getId())) {
            return createDepartment(department);
        } else
            return updateDepartmentByIdOrElseThrow(department);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDepartmentByID(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else
            throw new RuntimeException("Department с таким ID не найден!");
    }

    /**
     * Метод обновляет существующий департамент или выбрасывает ошибку.
     *
     * @param department департамент.
     * @return DepartmentDto dto департамента.
     * @throws RuntimeException при отсутствии в БД id.
     */
    private DepartmentDto updateDepartmentByIdOrElseThrow(Department department) {
        Department updateDepartment = repo.findById(department.getId()).orElse(null);
        if (updateDepartment != null) {
            updateDepartment.setName(department.getName());
            updateDepartment.setAddress(department.getAddress());
            updateDepartment.setModificationDate(LocalDateTime.now());
            return mapper.map(repo.save(updateDepartment), DepartmentDto.class);
        } else
            throw new RuntimeException("Department с таким ID нет в базе данных! Введите правильный id!");
    }

    /**
     * Метод сохранения нового департамента.
     *
     * @param department департамент.
     * @return DepartmentDto dto департамента.
     */
    private DepartmentDto createDepartment(Department department){
        department.setCreationDate(LocalDateTime.now());
        department.setModificationDate(null);
        Department departmentSaved = repo.save(department);
        return mapper.map(departmentSaved, DepartmentDto.class);
    }
}
