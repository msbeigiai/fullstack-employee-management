package com.msbeigi.employeemanagerbackend.model;

public record EmployeeRequestBody(
        String name,
        String email,
        String jobTitle,
        String phone,
        String imageUrl) {
}
