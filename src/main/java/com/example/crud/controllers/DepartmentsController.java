package com.example.crud.controllers;

import com.example.crud.dto.DepartmentDto;
import com.example.crud.exception.ErrorMessage;
import com.example.crud.service.implement.DepartmentsServiceImplement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Класс REST контроллер.
 */
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class DepartmentsController {
    private DepartmentsServiceImplement departmentsServiceImplement;

    /**
     * Получение департамента по ID.
     *
     * @param id идентификатор департамента.
     * @return ResponseEntity содержит DTO департамента и HTTP статус.
     */
    @GetMapping("get/{id}")
    public ResponseEntity<DepartmentDto> findDepartmentById(@PathVariable Long id) {
        return new ResponseEntity<>(departmentsServiceImplement.getDepartmentById(id), HttpStatus.OK);
    }

    /**
     * Обновление департамента.
     *
     * @param dto DTO департамента для его обновления.
     * @return ResponseEntity содержит DTO департамента и HTTP статус.
     */
    @PostMapping( "save/")
    public ResponseEntity<DepartmentDto> addOrUpdateDepartment(@RequestBody DepartmentDto dto) {
        return new ResponseEntity<>(departmentsServiceImplement.save(dto), HttpStatus.OK);
    }

    /**
     * Удаление департамента по ID.
     *
     * @param id идентификатор дапартамента, который нужно удалить.
     * @return ResponseEntity содержит HTTP статус.
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentsServiceImplement.deleteDepartmentByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Обработка исключений и возвращение сообщений об ошибке.
     *
     * @param exception исключение во время выполнения.
     * @return ResponseEntity возвращает сообщение об ошибке.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> getException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
