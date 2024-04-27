package com.example.demo.controllers;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.exception.ErrorMessage;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.implement.DepartmentsServiceImplement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class DepartmentsController {
    private DepartmentsServiceImplement departmentsServiceImplement;

    @GetMapping("get/{id}")
    public ResponseEntity<DepartmentDto> findDepartmentById(@PathVariable Long id){
        return new ResponseEntity<>(departmentsServiceImplement.getDepartmentById(id), HttpStatus.OK);
    }
    @PostMapping("save/")
    public ResponseEntity<DepartmentDto> addOrUpdateDepartment(DepartmentDto dto){
        departmentsServiceImplement.save(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public void deleteDepartment(@PathVariable Long id){
        departmentsServiceImplement.deleteDepartmentByID(id);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
