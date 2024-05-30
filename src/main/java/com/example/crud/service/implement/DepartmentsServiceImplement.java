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
import java.util.ArrayList;
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
            return updateDepartment(dto);
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

    private DepartmentDto updateDepartment(DepartmentDto dto) {
        Long id = dto.getId();
        if (departmentRepo.existsById(id)) {
            Department updatedDepartment = departmentRepo.findById(dto.getId()).orElse(null);

            Department departmentTemp = new Department();
            if (Objects.isNull(dto.getEmployeeDtoList())) {
                departmentTemp.setEmployees(new ArrayList<>());
            } else {
                List<EmployeeDto> employeeDtoListInput = dto.getEmployeeDtoList();
                List<Employee> employeeList = employeeDtoListInput.stream()
                        .map((element) -> mapper.map(element, Employee.class))
                        .toList();

                employeeList.forEach(employee -> employee.setDepartment(departmentTemp));
                departmentTemp.setEmployees(employeeList);
            }

            departmentTemp.setId(Objects.requireNonNull(updatedDepartment).getId());
            departmentTemp.setName(dto.getName());
            departmentTemp.setAddress(dto.getAddress());
            departmentTemp.setCreationDate(updatedDepartment.getCreationDate());
            departmentTemp.setModificationDate(LocalDateTime.now());

            return departmentDtoToReturn(departmentTemp);
        } else {
            throw new RuntimeException("Department с таким ID нет в базе данных! Введите правильный id!");
        }
    }

    /**
     * Метод создания департамента.
     *
     * @param dto департамент.
     * @return DepartmentDto департамент.
     */
    private DepartmentDto createDepartment(DepartmentDto dto) {
        Department department = mapper.map(dto, Department.class);
        List<EmployeeDto> employeesDtoList = dto.getEmployeeDtoList();
        if (Objects.isNull(employeesDtoList)) {
            department.setEmployees(new ArrayList<>());
        } else {
            List<Employee> employeeList = employeesDtoList.stream()
                    .map((element) -> mapper.map(element, Employee.class))
                    .toList();

            employeeList.forEach(employee -> employee.setDepartment(department));
            department.setEmployees(employeeList);
        }
        department.setName(department.getName());
        department.setAddress(department.getAddress());
        department.setCreationDate(LocalDateTime.now());
        department.setModificationDate(null);

        return departmentDtoToReturn(department);
    }

    /**
     * Метод возвращает departmentDto со списком сотрудников сохраненного департамента.
     *
     * @param department департамент.
     * @return DepartmentDto департамент.
     */
    private DepartmentDto departmentDtoToReturn(Department department) {
        Department savedDepartment = departmentRepo.save(department);

        List<EmployeeDto> employeeDtoListOut = savedDepartment.getEmployees()
                .stream()
                .map(element -> mapper.map(element, EmployeeDto.class))
                .toList();
        DepartmentDto savedDepartmentDto = mapper.map(savedDepartment, DepartmentDto.class);
        savedDepartmentDto.setEmployeeDtoList(employeeDtoListOut);
        return savedDepartmentDto;
    }
}

