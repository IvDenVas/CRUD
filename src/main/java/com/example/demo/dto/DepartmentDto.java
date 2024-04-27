package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DepartmentDto {

    private Long id;
    private String name;
    private String address;
    private String creationDate;
    private String modificationDate;
}
