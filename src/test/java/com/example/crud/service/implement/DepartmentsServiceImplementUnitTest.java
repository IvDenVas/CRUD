package com.example.crud.service.implement;

import com.example.crud.dto.DepartmentDto;
import com.example.crud.entities.Department;
import com.example.crud.repository.DepartmentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentsServiceImplementUnitTest {
    private ModelMapper mapper;
    private DepartmentRepo repo;
    private DepartmentsServiceImplement serviceImplement;
    @BeforeEach
    void setUp(){
        mapper = new ModelMapper();
        repo = mock(DepartmentRepo.class);
        serviceImplement = new DepartmentsServiceImplement(mapper,repo);
    }

    @Test
    @DisplayName("Тест на получение departmentDto по существующему id")
    void getDepartmentByExistIdTest() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setAddress("Address");
        DepartmentDto departmentDto = mapper.map(department, DepartmentDto.class);

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
    @DisplayName("Тест на сохранение departmentDto с отсутствующим в БД id")
    void saveDepartmentIfNotExistIdTest(){
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
    @DisplayName("Тест на изменение departmentDto с существующим в БД id")
    void saveDepartmentIfExistIdTest(){
        LocalDateTime createDateTime = LocalDateTime.now();

        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setAddress("Address");
        department.setCreationDate(createDateTime);
        department.setModificationDate(null);

        DepartmentDto updateDto = new DepartmentDto();
        updateDto.setId(1L);
        updateDto.setName("NewName");
        updateDto.setAddress("NewAddress");

        when(repo.findById(1L)).thenReturn(Optional.of(department));
        when(repo.save(any())).thenAnswer(i -> {
            department.setName(updateDto.getName());
            department.setAddress(updateDto.getAddress());
            department.setModificationDate(LocalDateTime.now());
            return department;
        });

        DepartmentDto updatedAndSavedDepartment = serviceImplement.save(updateDto);

        assertEquals(1L, updatedAndSavedDepartment.getId());
        assertEquals("NewName", updatedAndSavedDepartment.getName());
        assertEquals("NewAddress", updatedAndSavedDepartment.getAddress());
        assertEquals(createDateTime.toString(), updatedAndSavedDepartment.getCreationDate());
        assertNotNull(updatedAndSavedDepartment.getModificationDate());
    }

    @Test
    @DisplayName("Тест на изменение departmentDto с несуществующим в БД id")
    void saveDepartmentIfNotExistIdGetThrowTest(){
        DepartmentDto updateDepartment = new DepartmentDto();
        updateDepartment.setId(12L);

        when(repo.existsById(12L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> serviceImplement.save(updateDepartment));
    }

    @Test
    @DisplayName("Тест на удаление department с существующим в БД id")
    void deleteDepartmentByIdIfExist(){

        when(repo.existsById(1L)).thenReturn(true);

        serviceImplement.deleteDepartmentByID(1L);

        verify(repo).deleteById(1L);
    }

    @Test
    @DisplayName("Тест на удаление department с отсутствующим в БД id")
    void deleteDepartmentByIdIfNotExist(){

        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> serviceImplement.deleteDepartmentByID(1L));
    }
}