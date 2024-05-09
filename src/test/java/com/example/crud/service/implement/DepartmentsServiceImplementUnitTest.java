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
    void updateDepartmentByIdIfExistIdOrElseThrow(){

        Department department = new Department();
        department.setId(1L);
        department.setName("Name");
        department.setAddress("Address");
        department.setCreationDate(LocalDateTime.now());

        when(repo.findById(1L)).thenReturn(Optional.of(department));
        when(repo.save(department)).thenAnswer(i -> {
            department.setName("NewName");
            department.setAddress("NewAddress");
            department.setModificationDate(LocalDateTime.now());
            return department;
        });

        DepartmentDto result = serviceImplement.updateDepartmentByIdOrElseThrow(department);

        assertEquals(1L, result.getId());
        assertEquals("NewName", result.getName());
        assertEquals("NewAddress", result.getAddress());
        assertNotNull(result.getModificationDate());
    }

    @Test
    @DisplayName("Тест на изменение departmentDto с отсутствующим в БД id")
    void updateDepartmentByIdIfNotExistIdOrElseThrow(){
        Department department = new Department();
        department.setId(1L);

        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> serviceImplement.updateDepartmentByIdOrElseThrow(department));
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