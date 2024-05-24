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

    @Test
    @DisplayName("Тест на получение Department по существующему ID")
    void getDepartmentIfExistIdTest() {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("Test1");
        dto.setAddress("Test1");

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

        DepartmentDto dto = new DepartmentDto();
        dto.setName("Test4");
        dto.setAddress("Test4");

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

        DepartmentDto dto = new DepartmentDto();
        dto.setName("Test5");
        dto.setAddress("Test5");

        EmployeeDto employeeDtoOne = new EmployeeDto();
        employeeDtoOne.setName("EmpTest7");
        employeeDtoOne.setSurname("SurTest7");

        EmployeeDto employeeDtoTwo = new EmployeeDto();
        employeeDtoTwo.setName("EmpTest8");
        employeeDtoTwo.setSurname("SurTest8");

        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        employeeDtoList.add(employeeDtoOne);
        employeeDtoList.add(employeeDtoTwo);

        dto.setEmployeeDtoList(employeeDtoList);

        departmentsService.save(dto);
        DepartmentDto result = departmentsService.getDepartmentById(4L);

        assertEquals(4L, result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getAddress(), result.getAddress());
        assertNotNull(result.getCreationDate());
        assertNotNull(result.getEmployeeDtoList());
        assertEquals(2, result.getEmployeeDtoList().size());
    }


    @Test
    @DisplayName("Тест на изменение departmentDto с существующим в БД id")
    void saveDepartmentIfExistIdTest() {
        DepartmentDto updateDto = new DepartmentDto();
        updateDto.setId(1L);
        updateDto.setName("NewTest1");
        updateDto.setAddress("NewTest1");

        DepartmentDto updatedAndSavedDepartment = departmentsService.save(updateDto);

        assertEquals(1L, updatedAndSavedDepartment.getId());
        assertEquals("NewTest1", updatedAndSavedDepartment.getName());
        assertEquals("NewTest1", updatedAndSavedDepartment.getAddress());
        assertNotNull(updatedAndSavedDepartment.getModificationDate());
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
