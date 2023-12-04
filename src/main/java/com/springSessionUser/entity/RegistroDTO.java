package com.springSessionUser.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDTO {
    private String userName;
    private String password;
    private char sessionActive;
    private String nombres;
    private String apellidos;
    private String identificacion;
    private LocalDate fechaNacimiento;
    private String status;
}
