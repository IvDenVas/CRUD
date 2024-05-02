package com.example.crud.service.implement;

import com.example.crud.dto.DepartmentDto;
import com.example.crud.entities.Department;
import com.example.crud.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentsServiceImplementUnitTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    private HibernateSessionFactoryUtil hibernateSessionFactoryUtil;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;
    @InjectMocks
    private DepartmentsServiceImplement departmentsService = new DepartmentsServiceImplement(mapper, hibernateSessionFactoryUtil);

    private Department department;
    private Department department1;
    private DepartmentDto departmentDto;
    private DepartmentDto departmentDto1;

    @BeforeEach
    public void setUp() {
        department = new Department(1L, "name", "name",LocalDateTime.now(), LocalDateTime.now());
        department1 = new Department("name", "name",LocalDateTime.now(), LocalDateTime.now());
        departmentDto = mapper.map(department, DepartmentDto.class);
        departmentDto1 = mapper.map(department1, DepartmentDto.class);
    }

    @Test
    @DisplayName("Тест получение departmentDto по существующему id")
    void getDepartmentByExistIdTest() {
        when(hibernateSessionFactoryUtil.getSession()).thenReturn(session);
        when(session.get(Department.class, department.getId())).thenReturn(department);
        when(mapper.map(department, DepartmentDto.class)).thenReturn(departmentDto);

        assertEquals(departmentDto, departmentsService.getDepartmentById(1L));
    }

    @Test
    @DisplayName("Тест получение departmentDto по несуществующему id")
    void getDepartmentByNotExistIdTest() {
        when(hibernateSessionFactoryUtil.getSession()).thenReturn(session);
        when(session.get(Department.class, department.getId())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> departmentsService.getDepartmentById(2L));
    }

    @Test
    @DisplayName("Тест сохранение departmentDto с отсутствующим в БД id")
    void saveDepartmentIfNotExistIdTest(){
        when(hibernateSessionFactoryUtil.getSession()).thenReturn(session);
        when(mapper.map(departmentDto1, Department.class)).thenReturn(department1);
        when(session.beginTransaction()).thenReturn(transaction);

        departmentsService.save(departmentDto1);

        verify(session).persist(department1);
        verify(transaction).commit();
    }

    @Test
    @DisplayName("Тест сохранение departmentDto с существующим в БД id")
    void saveDepartmentIfExistIdTest(){
        when(hibernateSessionFactoryUtil.getSession()).thenReturn(session);
        when(mapper.map(departmentDto, Department.class)).thenReturn(department);
        when(session.beginTransaction()).thenReturn(transaction);

        assertThrows(RuntimeException.class, () -> departmentsService.save(departmentDto));
    }

    @Test
    @DisplayName("Тест изменения departmentDto с существующим в БД id")
    void updateDepartmentByIdIfExistIdOrElseThrow(){
        when(hibernateSessionFactoryUtil.getSession()).thenReturn(session);
        when(session.get(Department.class, 1L)).thenReturn(department);
        when(session.beginTransaction()).thenReturn(transaction);
        when(mapper.map(department, DepartmentDto.class)).thenReturn(departmentDto);

        department.setName("New");
        department.setAddress("New");

        departmentsService.updateDepartmentByIdOrElseThrow(department);

        verify(session).evict(department);
        verify(session).merge(department);
        verify(transaction).commit();
    }

    @Test
    @DisplayName("Тест изменения departmentDto с отсутствующим в БД id")
    void updateDepartmentByIdIfNotExistIdOrElseThrow(){
        when(hibernateSessionFactoryUtil.getSession()).thenReturn(session);
        when(session.get(Department.class, 111L)).thenReturn(department);
        when(session.beginTransaction()).thenReturn(transaction);
        when(mapper.map(department, DepartmentDto.class)).thenReturn(departmentDto);

        department.setName("New");
        department.setAddress("New");

        assertThrows(RuntimeException.class, () -> departmentsService.updateDepartmentByIdOrElseThrow(department));
    }

    @Test
    @DisplayName("Тест удаления department с существующим в БД id")
    void deleteDepartmentByIdIfExist(){
        when(hibernateSessionFactoryUtil.getSession()).thenReturn(session);
        when(session.get(Department.class, 1L)).thenReturn(department);
        when(session.beginTransaction()).thenReturn(transaction);

        departmentsService.deleteDepartmentByID(1L);

        verify(session).remove(department);
        verify(transaction).commit();
    }

    @Test
    @DisplayName("Тест удаления department с отсутствующим в БД id")
    void deleteDepartmentByIdIfNotExist(){
        when(hibernateSessionFactoryUtil.getSession()).thenReturn(session);
        when(session.get(Department.class, 1L)).thenReturn(department);
        when(session.beginTransaction()).thenReturn(transaction);

        assertThrows(RuntimeException.class, () -> departmentsService.deleteDepartmentByID(101L));
    }



}