package com.example.crud.service.implement;

import com.example.crud.dto.DepartmentDto;
import com.example.crud.dto.EmployeeDto;
import com.example.crud.entities.Department;
import com.example.crud.entities.Employee;
import com.example.crud.exception.NotFoundException;
import com.example.crud.repositories.DepartmentRepo;
import com.example.crud.service.DepartmentsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Класс описывает методы, реализующие бизнес-логику CRUD приложения.
 */
@Service
@AllArgsConstructor
public class DepartmentsServiceImplement implements DepartmentsService {
    private ModelMapper mapper;
    private DepartmentRepo departmentRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepo.findById(id).orElse(null);
        if (!Objects.isNull(department)) {
            List<EmployeeDto> employeeDtoList = department.getEmployees().stream()
                    .map(employee -> mapper.map(employee, EmployeeDto.class))
                    .toList();
            DepartmentDto departmentDto = mapper.map(department, DepartmentDto.class);
            departmentDto.setEmployeeDtoList(employeeDtoList);
            return departmentDto;
        } else {
            throw new NotFoundException("Department с таким ID не найден!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DepartmentDto save(DepartmentDto dto) {
        if (Objects.isNull(dto.getId())) {
            return createDepartment(dto);
        } else {
            return updateDepartmentByIdOrElseThrow(dto);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDepartmentByID(Long id) {
        if (departmentRepo.existsById(id)) {
            departmentRepo.deleteById(id);
        } else
            throw new RuntimeException("Department с таким ID не найден!");
    }

    /**
     * Метод обновляет существующий департамент или выбрасывает ошибку.
     *
     * @param dto департамент.
     * @return DepartmentDto dto департамента.
     * @throws RuntimeException при отсутствии в БД id.
     */
    private DepartmentDto updateDepartmentByIdOrElseThrow(DepartmentDto dto) {
        Long id = dto.getId();
        if (departmentRepo.existsById(id)) {
            Department updateDepartment = departmentRepo.findById(id).orElse(null);
            updateDepartment.setName(dto.getName());
            updateDepartment.setAddress(dto.getAddress());
            updateDepartment.setModificationDate(LocalDateTime.now());
            return mapper.map(departmentRepo.save(updateDepartment), DepartmentDto.class);
        } else
            throw new RuntimeException("Department с таким ID нет в базе данных! Введите правильный id!");
    }
    /**
     * Метод выбора способа сохранения.
     *
     * @param dto департамент.
     * @return DepartmentDto департамент.
     */
    private DepartmentDto createDepartment(DepartmentDto dto) {
        Department department = mapper.map(dto, Department.class);
        List<EmployeeDto> employeesDtoList = dto.getEmployeeDtoList();
        if(Objects.isNull(employeesDtoList)) {
            return createIfNotExistEmployeeDto(department);
        } else {
            return createIfExistEmployeeDto(department, employeesDtoList);
        }
    }
    /**
     * Метод сохранения нового департамента без сотрудников.
     *
     * @param department департамент.
     * @return DepartmentDto dto департамента.
     */
    private DepartmentDto createIfNotExistEmployeeDto(Department department) {
        department.setCreationDate(LocalDateTime.now());
        department.setModificationDate(null);
        department.setEmployees(null);
        Department savedDepartment = departmentRepo.save(department);
        return mapper.map(savedDepartment, DepartmentDto.class);
    }
    /**
     * Метод сохранения нового департамента со списком сотрудников.
     *
     * @param department департамент.
     * @param employeesDtoList список сотрдуников
     * @return DepartmentDto dto департамента.
     */
    private DepartmentDto createIfExistEmployeeDto(Department department, List<EmployeeDto> employeesDtoList) {
        List<Employee> employeeList = employeesDtoList.stream()
                .map((element) -> mapper.map(element, Employee.class))
                .toList();

        employeeList.forEach(employee -> employee.setDepartment(department));

        department.setEmployees(employeeList);
        department.setName(department.getName());
        department.setAddress(department.getAddress());
        department.setCreationDate(LocalDateTime.now());
        department.setModificationDate(null);
        department.setEmployees(employeeList);

        Department savedDepartment = departmentRepo.save(department);
        List<EmployeeDto> employeeDtoList = savedDepartment.getEmployees()
                .stream()
                .map(element -> mapper.map(element, EmployeeDto.class))
                .toList();
        DepartmentDto savedDepartmentDto = mapper.map(savedDepartment, DepartmentDto.class);
        savedDepartmentDto.setEmployeeDtoList(employeeDtoList);
        return savedDepartmentDto;
    }
}

