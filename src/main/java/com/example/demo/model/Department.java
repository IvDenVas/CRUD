package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department")
public class Department {
    @Id
//    @Column(name = "id")
//    @SequenceGenerator(name = "departmentsIdSeq",sequenceName = "department_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "departmentsIdSeq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "creation_date")
    private String creationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss"));

    @Column(nullable = false, name = "modification_date")
    private String modificationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss"));
}
