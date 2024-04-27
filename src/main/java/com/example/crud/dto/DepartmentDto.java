package com.example.crud.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDto {

    private Long id;
    private String name;
    private String address;
    private String creationDate;
    private String modificationDate;
}
