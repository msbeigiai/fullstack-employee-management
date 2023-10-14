package com.msbeigi.employeemanagerbackend.model;

public record PasswordModel(String email, String oldPassword, String newPassword) {
}
