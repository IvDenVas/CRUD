package com.example.crud.service.implement;

import com.example.crud.dto.DepartmentDto;
import com.example.crud.entities.Department;
import com.example.crud.repository.DepartmentRepo;;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DepartmentsServiceImplementIntegrationTest {
    @Autowired
    private DepartmentsServiceImplement departmentsService;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Test
    @DisplayName("Тест на полчение Department по существвующему ID")
    void getDepartmentByExistIdTest(){
        DepartmentDto dto = new DepartmentDto();
        dto.setId(3L);
        dto.setName("Third");
        dto.setAddress("Address3");

        DepartmentDto result = departmentsService.getDepartmentById(3L);

        assertEquals(result.getName(), dto.getName());
        assertEquals(result.getAddress(), dto.getAddress());
    }

    @Test
    @DisplayName("Тест на сохранение Department")
    void saveTest() {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("Name");
        dto.setAddress("Address");

        Department department = new Department();
        department.setName("Name");
        department.setAddress("Address");
        departmentsService.save(dto);

        DepartmentDto result = departmentsService.getDepartmentById(5L);//изменить id согласно sequence

        assertNotNull(result);
        assertEquals(department.getName(), result.getName());
        assertEquals(department.getAddress(), result.getAddress());
    }

    @Test
    @DisplayName("Тест на удаление department с существующим в БД id")
    void deleteDepartmentByIdIfExist(){

        departmentsService.deleteDepartmentByID(5L);//изменить id согласно sequence

        assertFalse(departmentRepo.existsById(5L));//изменить id согласно sequence
    }

    @Test
    @DisplayName("Тест на удаление department с отсутствующим в БД id")
    void deleteDepartmentByIdIfNotExist(){

        assertThrows(RuntimeException.class, () -> departmentsService.deleteDepartmentByID(111L));
    }
}
