package com.msbeigi.employeemanagerbackend.model;

import jakarta.persistence.Column;

import java.util.List;

public record EmployeeDTO(
        Long id,
        String name,
        String email,
        String jobTitle,
        String phone,
        String imageUrl,
        List<String> roles
) {

}
