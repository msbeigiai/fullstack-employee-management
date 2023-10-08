package com.msbeigi.employeemanagerbackend.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
