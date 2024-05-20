package com.example.crud.service.implement;

import com.example.crud.dto.DepartmentDto;
import com.example.crud.dto.EmployeeDto;
import com.example.crud.entities.Department;
import com.example.crud.entities.Employee;
import com.example.crud.repositories.DepartmentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentsServiceImplementUnitTest {
    private ModelMapper mapper;
    private DepartmentRepo repo;
    private DepartmentsServiceImplement serviceImplement;

    @BeforeEach
    void setUp() {
        mapper = new ModelMapper();
        repo = mock(DepartmentRepo.class);
        serviceImplement = new DepartmentsServiceImplement(mapper, repo);
    }

    @Test
    @DisplayName("Тест на получение departmentDto по существующему id")
    void getDepartmentByExistIdTest() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setAddress("Address");
        List<Employee> employeeList = Arrays.asList(
                new Employee(1L, "name1", "surname1", department),
                new Employee(2L, "name2", "surname2", department)
        );
        department.setEmployees(employeeList);
        List<EmployeeDto> employeeDtoList = employeeList.stream()
                .map(employee -> mapper.map(employee, EmployeeDto.class))
                .toList();

        DepartmentDto departmentDto = mapper.map(department, DepartmentDto.class);
        departmentDto.setEmployeeDtoList(employeeDtoList);

        when(repo.findById(1L)).thenReturn(Optional.of(department));

        assertEquals(departmentDto, serviceImplement.getDepartmentById(1L));
    }

    @Test
    @DisplayName("Тест на получение departmentDto по несуществующему id")
    void getDepartmentByNotExistIdTest() {

        when(repo.findById(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> serviceImplement.getDepartmentById(1L));
    }

    @Test
    @DisplayName("Тест на сохранение departmentDto без списка сотрудников с отсутствующим в БД id")
    void saveDepartmentIfNotExistIdWithoutListOfEmployeesTest() {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("Name");
        dto.setAddress("Address");

        when(repo.save(any(Department.class))).thenAnswer(i -> {
            Department department = new Department();
            department.setId(1L);
            department.setName("Name");
            department.setAddress("Address");
            return department;
        });

        DepartmentDto result = serviceImplement.save(dto);

        assertEquals(1L, result.getId());
        assertEquals("Name", result.getName());
        assertEquals("Address", result.getAddress());
    }

    @Test
    @DisplayName("Тест на сохранение departmentDto со списком сотрудников с отсутствующим в БД id")
    void saveDepartmentIfNotExistIdWithListOfEmployeesTest() {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("Name");
        dto.setAddress("Address");

        List<EmployeeDto> employeeDtoList = Arrays.asList(
                new EmployeeDto(1L, "Name1", "Surname1"),
                new EmployeeDto(2L, "Name2", "Surname2")
        );

        dto.setEmployeeDtoList(employeeDtoList);

        List<Employee> employeeList = employeeDtoList.stream()
                .map(employee -> mapper.map(employee, Employee.class))
                .toList();

        when(repo.save(any(Department.class))).thenAnswer(i -> {
            Department department = new Department();
            department.setId(1L);
            department.setName("Name");
            department.setAddress("Address");
            department.setEmployees(employeeList);
            return department;
        });

        DepartmentDto result = serviceImplement.save(dto);

        assertEquals(1L, result.getId());
        assertEquals("Name", result.getName());
        assertEquals("Address", result.getAddress());
        assertEquals(employeeDtoList, result.getEmployeeDtoList());
    }

    @Test
    @DisplayName("Тест на изменение departmentDto с существующим в БД id")
    void saveDepartmentIfExistIdTest() {
        Long id = 1L;
        LocalDateTime createDateTime = LocalDateTime.now();

        Department department = new Department();
        department.setId(id);
        department.setName("Name");
        department.setAddress("Address");
        department.setCreationDate(createDateTime);
        department.setModificationDate(null);

        List<Employee> employeeList = Arrays.asList(
                new Employee(1L, "1", "1", department),
                new Employee(2L, "2", "2", department)
        );

        List<EmployeeDto> employeeDtoList = employeeList.stream()
                .map(employee -> mapper.map(employee, EmployeeDto.class))
                .toList();

        department.setEmployees(employeeList);

        DepartmentDto updateDto = new DepartmentDto();
        updateDto.setId(id);
        updateDto.setName("NewName");
        updateDto.setAddress("NewAddress");
        updateDto.setCreationDate(createDateTime);
        updateDto.setEmployeeDtoList(employeeDtoList);
        updateDto.setEmployeeDtoList(employeeDtoList);

        when(repo.existsById(updateDto.getId())).thenReturn(true);
        when(repo.findById(1L)).thenReturn(Optional.of(department));
        when(repo.save(any())).thenAnswer(i -> {
            Department departmentToReturn = new Department();
            departmentToReturn.setId(((Department) i.getArguments()[0]).getId());
            departmentToReturn.setName(((Department) i.getArguments()[0]).getName());
            departmentToReturn.setAddress(((Department) i.getArguments()[0]).getAddress());
            departmentToReturn.setCreationDate(createDateTime);
            departmentToReturn.setModificationDate(LocalDateTime.now());
            departmentToReturn.setEmployees(Arrays.asList(
                    new Employee(1L, "1", "1", departmentToReturn),
                    new Employee(2L, "2", "2", departmentToReturn)
            ));
            return departmentToReturn;
        });

        DepartmentDto updatedAndSavedDepartment = serviceImplement.save(updateDto);
        employeeList.forEach(employee -> employee.setDepartment(mapper.map(updatedAndSavedDepartment, Department.class)));
        List<EmployeeDto> updateAndSaveListEmployeeDto = employeeList.stream()
                .map(employee -> mapper.map(employee, EmployeeDto.class))
                .toList();
        updatedAndSavedDepartment.setEmployeeDtoList(updateAndSaveListEmployeeDto);

        assertEquals(1L, updatedAndSavedDepartment.getId());
        assertEquals("NewName", updatedAndSavedDepartment.getName());
        assertEquals("NewAddress", updatedAndSavedDepartment.getAddress());
        assertEquals(createDateTime, updatedAndSavedDepartment.getCreationDate());
        assertNotNull(updatedAndSavedDepartment.getModificationDate());
        assertEquals(employeeDtoList, updatedAndSavedDepartment.getEmployeeDtoList());
    }

    @Test
    @DisplayName("Тест на изменение departmentDto с несуществующим в БД id")
    void saveDepartmentIfNotExistIdGetThrowTest() {
        DepartmentDto updateDepartment = new DepartmentDto();
        updateDepartment.setId(12L);

        when(repo.existsById(12L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> serviceImplement.save(updateDepartment));
    }

    @Test
    @DisplayName("Тест на удаление department с существующим в БД id")
    void deleteDepartmentByIdIfExist() {

        when(repo.existsById(1L)).thenReturn(true);

        serviceImplement.deleteDepartmentByID(1L);

        verify(repo).deleteById(1L);
    }

    @Test
    @DisplayName("Тест на удаление department с отсутствующим в БД id")
    void deleteDepartmentByIdIfNotExist() {

        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> serviceImplement.getDepartmentById(1L));
    }
}