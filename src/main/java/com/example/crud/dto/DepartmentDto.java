package com.example.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс DTO департамент.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    private Long id;

    private String name;

    private String address;

    private LocalDateTime creationDate;

    private LocalDateTime modificationDate;

    private List<EmployeeDto> employeeDtoList;
}
