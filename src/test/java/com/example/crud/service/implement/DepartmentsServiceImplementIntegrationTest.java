package com.example.crud.service.implement;

import com.example.crud.DemoApplication;
import com.example.crud.dto.DepartmentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@Sql(value = "/sql/schema.sql")
@ActiveProfiles("test")
@SpringBootTest
public class DepartmentsServiceImplementIntegrationTest {

    @Autowired
    private DepartmentsServiceImplement departmentsService;
    @Test
    @DisplayName("Тест на получение Department по существвующему ID")
    void getDepartmentByExistIdTest() {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("Department1");
        dto.setAddress("Address1");

        DepartmentDto result = departmentsService.getDepartmentById(1L);//изменить id согласно sequence

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getAddress(), result.getAddress());
    }
    @Test
    @DisplayName("Тест на сохранение Department с отсутствующим в БД id")
    void saveDepartmentIfNotExistIdTest(){

        DepartmentDto dto = new DepartmentDto();
        dto.setName("Department4");
        dto.setAddress("Address4");

        departmentsService.save(dto);
        DepartmentDto result = departmentsService.getDepartmentById(4L);

        assertEquals(4L,result.getId());
        assertEquals(dto.getName(),result.getName());
        assertEquals(dto.getAddress(),result.getAddress());
        assertNotNull(result.getCreationDate());
    }

    @Test
    @DisplayName("Тест на изменение departmentDto с существующим в БД id")
    void saveDepartmentIfExistIdTest(){
        DepartmentDto updateDto = new DepartmentDto();
        updateDto.setId(1L);
        updateDto.setName("NewDepartment1");
        updateDto.setAddress("NewAddress1");

        DepartmentDto updatedAndSavedDepartment = departmentsService.save(updateDto);

        assertEquals(1L, updatedAndSavedDepartment.getId());
        assertEquals("NewDepartment1", updatedAndSavedDepartment.getName());
        assertEquals("NewAddress1", updatedAndSavedDepartment.getAddress());
        assertNotNull(updatedAndSavedDepartment.getModificationDate());
    }

    @Test
    @DisplayName("Тест на изменение departmentDto с несуществующим в БД id")
    void saveDepartmentIfNotExistIdGetThrowTest(){
        DepartmentDto updateDepartment = new DepartmentDto();
        updateDepartment.setId(12L);

        assertThrows(RuntimeException.class, () -> departmentsService.save(updateDepartment));
    }

    @Test
    @DisplayName("Тест на удаление department с отсутствующим в БД id")
    void deleteDepartmentByIdIfNotExist(){
        assertThrows(RuntimeException.class, () -> departmentsService.deleteDepartmentByID(111L));
    }
    @Test
    @DisplayName("Тест на удаление department с существующим в БД id")
    void deleteDepartmentByIdIfExist(){
        Long id = 1L;
        departmentsService.deleteDepartmentByID(id);

        assertThrows(RuntimeException.class, () -> departmentsService.deleteDepartmentByID(id));
    }
}
