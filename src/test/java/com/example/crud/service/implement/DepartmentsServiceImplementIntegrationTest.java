package com.example.crud.service.implement;

import com.example.crud.config.ContainerPostgres;
import com.example.crud.dto.DepartmentDto;
import com.example.crud.dto.EmployeeDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Sql(value = "/sql/schema.sql")
@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(ContainerPostgres.class)
public class DepartmentsServiceImplementIntegrationTest {

    @Autowired
    private DepartmentsServiceImplement departmentsService;

    private DepartmentDto getDepartmentDto(String name, String address) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(name);
        departmentDto.setAddress(address);
        return departmentDto;
    }

    private DepartmentDto getDepartmentDto(Long id, String name, String address) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(id);
        departmentDto.setName(name);
        departmentDto.setAddress(address);
        return departmentDto;
    }

    private EmployeeDto getEmployeeDto(String name, String surname) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName(name);
        employeeDto.setSurname(surname);
        return employeeDto;
    }

    private EmployeeDto getEmployeeDto(Long id, String name, String surname) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(id);
        employeeDto.setName(name);
        employeeDto.setSurname(surname);
        return employeeDto;
    }


    @Test
    @DisplayName("Тест на получение Department по существующему ID")
    void getDepartmentIfExistIdTest() {
        DepartmentDto dto = getDepartmentDto("Test1", "Test1");

        List<EmployeeDto> employeeDtoList = Arrays.asList(
                new EmployeeDto(1L,"EmpTest1", "SurTest1"),
                new EmployeeDto(2L, "EmpTest2", "SurTest2")
        );
        dto.setEmployeeDtoList(employeeDtoList);

        DepartmentDto result = departmentsService.getDepartmentById(1L);//изменить id согласно sequence

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getAddress(), result.getAddress());
        assertEquals(dto.getEmployeeDtoList(), result.getEmployeeDtoList());
    }

    @Test
    @DisplayName("Тест на получение Department по несуществующему ID")
    void getDepartmentIfNotExistIdTest() {
        assertThrows(RuntimeException.class, () -> departmentsService.getDepartmentById(111L));
    }

    @Test
    @DisplayName("Тест на сохранение Department без списка сотрудников с отсутствующим в БД id")
    void saveDepartmentWithoutListEmployeeIfNotExistIdTest() {

        DepartmentDto dto = getDepartmentDto("Test4", "Test4");

        departmentsService.save(dto);
        DepartmentDto result = departmentsService.getDepartmentById(4L);

        assertEquals(4L, result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getAddress(), result.getAddress());
        assertNotNull(result.getCreationDate());
        assertEquals(List.of(), result.getEmployeeDtoList());
    }

    @Test
    @DisplayName("Тест на сохранение Department со списком сотрудников с отсутствующим в БД id")
    void saveDepartmentWithListEmployeeIfNotExistIdTest() {

        DepartmentDto dto = getDepartmentDto("Test5", "Test5");

        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        employeeDtoList.add(getEmployeeDto("EmpTest7", "SurTest7"));
        employeeDtoList.add(getEmployeeDto("EmpTest8", "SurTest8"));

        dto.setEmployeeDtoList(employeeDtoList);

        departmentsService.save(dto);
        DepartmentDto result = departmentsService.getDepartmentById(4L);

        assertEquals(4L, result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getAddress(), result.getAddress());
        assertNotNull(result.getCreationDate());
        assertNotNull(result.getEmployeeDtoList());
        assertEquals(2, result.getEmployeeDtoList().size());
        assertNotNull(result.getEmployeeDtoList().get(0).getId());
        assertEquals("EmpTest7", result.getEmployeeDtoList().get(0).getName());
        assertEquals("SurTest7", result.getEmployeeDtoList().get(0).getSurname());
        assertNotNull(result.getEmployeeDtoList().get(1).getId());
        assertEquals("EmpTest8", result.getEmployeeDtoList().get(1).getName());
        assertEquals("SurTest8", result.getEmployeeDtoList().get(1).getSurname());
    }

    @Test
    @DisplayName("Тест на изменение departmentDto без списка с существующим в БД id")
    void saveDepartmentWithoutListIfExistIdTest() {
        DepartmentDto updateDto = getDepartmentDto(2L,"NewTest2", "NewTest2");

        DepartmentDto updatedAndSavedDepartment = departmentsService.save(updateDto);

        assertEquals(2L, updatedAndSavedDepartment.getId());
        assertEquals("NewTest2", updatedAndSavedDepartment.getName());
        assertEquals("NewTest2", updatedAndSavedDepartment.getAddress());
        assertNotNull(updatedAndSavedDepartment.getModificationDate());
    }

    @Test
    @DisplayName("Тест на изменение departmentDto со списком и существующим в БД id")
    void saveDepartmentWithListIfExistIdTest() {
        DepartmentDto updateDto = getDepartmentDto(1L,"NewTest1", "NewTest1");

        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        employeeDtoList.add(getEmployeeDto(7L, "NewEmpTest7", "NewSurTest7"));
        employeeDtoList.add(getEmployeeDto(8L, "NewEmpTest8", "NewSurTest8"));

        updateDto.setEmployeeDtoList(employeeDtoList);

        DepartmentDto updatedAndSavedDepartment = departmentsService.save(updateDto);

        assertEquals(1L, updatedAndSavedDepartment.getId());
        assertEquals("NewTest1", updatedAndSavedDepartment.getName());
        assertEquals("NewTest1", updatedAndSavedDepartment.getAddress());
        assertNotNull(updatedAndSavedDepartment.getModificationDate());
        assertEquals(7L, updatedAndSavedDepartment.getEmployeeDtoList().get(0).getId());
        assertEquals("NewEmpTest7", updatedAndSavedDepartment.getEmployeeDtoList().get(0).getName());
        assertEquals("NewSurTest7", updatedAndSavedDepartment.getEmployeeDtoList().get(0).getSurname());
        assertEquals(8L, updatedAndSavedDepartment.getEmployeeDtoList().get(1).getId());
        assertEquals("NewEmpTest8", updatedAndSavedDepartment.getEmployeeDtoList().get(1).getName());
        assertEquals("NewSurTest8", updatedAndSavedDepartment.getEmployeeDtoList().get(1).getSurname());
    }

    @Test
    @DisplayName("Тест на изменение departmentDto с несуществующим в БД id")
    void saveDepartmentIfNotExistIdGetThrowTest() {
        DepartmentDto updateDepartment = new DepartmentDto();
        updateDepartment.setId(12L);

        assertThrows(RuntimeException.class, () -> departmentsService.save(updateDepartment));
    }

    @Test
    @DisplayName("Тест на удаление department с отсутствующим в БД id")
    void deleteDepartmentByIdIfNotExist() {
        assertThrows(RuntimeException.class, () -> departmentsService.deleteDepartmentByID(111L));
    }

    @Test
    @DisplayName("Тест на удаление department с существующим в БД id")
    void deleteDepartmentByIdIfExist() {
        Long id = 1L;
        departmentsService.deleteDepartmentByID(id);

        assertThrows(RuntimeException.class, () -> departmentsService.getDepartmentById(id));
    }
}
