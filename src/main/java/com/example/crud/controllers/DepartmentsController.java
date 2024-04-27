package com.example.crud.controllers;

import com.example.crud.dto.DepartmentDto;
import com.example.crud.exception.ErrorMessage;
import com.example.crud.service.implement.DepartmentsServiceImplement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id){
        departmentsServiceImplement.deleteDepartmentByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> getException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
