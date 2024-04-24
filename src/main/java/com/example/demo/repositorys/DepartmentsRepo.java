package com.example.demo.repositorys;

import com.example.demo.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DepartmentsRepo extends JpaRepository<Department, Long> {
//    @Query("select m from Department m where m.id = (select max(id) from Department)")
//    Optional<Department> getMaxID();

//    @Modifying
////    @Transactional
//    @Query("UPDATE Department SET address = :address WHERE id = :id")
//    void updateDepartmentById(String address, long id);
}
