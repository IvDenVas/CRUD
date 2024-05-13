package com.example.crud.dto;

import lombok.Data;
/**
 * Класс DTO.
 */
@Data
public class DepartmentDto {

    private Long id;
    private String name;
    private String address;
    private String creationDate;
    private String modificationDate;

}
