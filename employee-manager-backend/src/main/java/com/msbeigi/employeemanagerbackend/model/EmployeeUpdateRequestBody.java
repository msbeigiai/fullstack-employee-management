package com.msbeigi.employeemanagerbackend.model;

public record EmployeeUpdateRequestBody(
        String name,
        String email,
        String jobTitle,
        String phone,
        String imageUrl)
{}

