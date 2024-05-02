package com.example.crud.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class DepartmentDto {

    private Long id;
    private String name;
    private String address;
    private String creationDate;
    private String modificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentDto dto = (DepartmentDto) o;

        if (!Objects.equals(id, dto.id)) return false;
        if (!Objects.equals(name, dto.name)) return false;
        if (!Objects.equals(address, dto.address)) return false;
        if (!Objects.equals(creationDate, dto.creationDate)) return false;
        return Objects.equals(modificationDate, dto.modificationDate);
    }
}
