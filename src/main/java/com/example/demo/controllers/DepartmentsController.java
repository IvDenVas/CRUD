package com.example.demo.controllers;

import com.example.demo.model.Department;
import com.example.demo.service.implement.DepartmentsServiceImplement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
//@RequiredArgsConstructor
@AllArgsConstructor
public class DepartmentsController {
    private final DepartmentsServiceImplement departmentsServiceImplement;

    @GetMapping("get/{id}")
    public ResponseEntity<Department> findDepartmentById(@PathVariable Long id){
        return new ResponseEntity<>(departmentsServiceImplement.getDepartmentById(id), HttpStatus.OK);
    }
    @PostMapping("save/")
    public ResponseEntity<Department> addNewDepartment(Department department){
        departmentsServiceImplement.save(department);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public void deleteDepartment(@PathVariable Long id){
        departmentsServiceImplement.deleteDepartmentByID(id);
    }
//    return client != null
//            ? new ResponseEntity<>(client, HttpStatus.OK)
//            : new ResponseEntity<>(HttpStatus.NOT_FOUND);

}
