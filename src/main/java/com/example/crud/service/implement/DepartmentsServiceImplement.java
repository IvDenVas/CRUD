package com.example.crud.service.implement;

import com.example.crud.dto.DepartmentDto;
import com.example.crud.entities.Department;
import com.example.crud.service.DepartmentsService;
import com.example.crud.utils.HibernateSessionFactoryUtil;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DepartmentsServiceImplement implements DepartmentsService {
    private ModelMapper mapper;
    private HibernateSessionFactoryUtil hibernateSessionFactoryUtil;


    @Override
    public DepartmentDto getDepartmentById(Long id) {
        try (Session session = hibernateSessionFactoryUtil.getSession()) {
            Department department = session.get(Department.class, id);
            return mapper.map(department, DepartmentDto.class);
        } catch (RuntimeException e) {
            throw new RuntimeException("Department с таким ID не найден!");
        }
    }

    @Override
    public DepartmentDto save(DepartmentDto dto) {
        Department department = mapper.map(dto, Department.class);
        if (department.getId() == null) {
            department.setCreationDate(LocalDateTime.now());
            department.setModificationDate(null);
            try (Session session = hibernateSessionFactoryUtil.getSession()) {
                Transaction tz = session.beginTransaction();
                session.persist(department);
                tz.commit();
                return mapper.map(department, DepartmentDto.class);
            }
        } else {
            return updateDepartmentByIdOrElseThrow(department);
        }
    }


    @Override
    public void deleteDepartmentByID(Long id) {
        try (Session session = hibernateSessionFactoryUtil.getSession()) {
            Transaction tz = session.beginTransaction();
            Department department = session.get(Department.class, id);
            session.remove(department);
            tz.commit();
        } catch (RuntimeException e) {
            throw new RuntimeException("Department с таким ID не найден!");
        }
    }

    public DepartmentDto updateDepartmentByIdOrElseThrow(Department department) {
        try (Session session = hibernateSessionFactoryUtil.getSession()) {
            Department tempDepartment = session.get(Department.class, department.getId());
            Transaction tz = session.beginTransaction();
            session.evict(tempDepartment);
            department.setId(tempDepartment.getId());
            department.setCreationDate(tempDepartment.getCreationDate());
            department.setModificationDate(LocalDateTime.now());
            session.merge(department);
            tz.commit();
            return mapper.map(department, DepartmentDto.class);
        } catch (RuntimeException e) {
            throw new RuntimeException("Department с таким ID нет в базе данных! Введите правильный id!");
        }
    }
}