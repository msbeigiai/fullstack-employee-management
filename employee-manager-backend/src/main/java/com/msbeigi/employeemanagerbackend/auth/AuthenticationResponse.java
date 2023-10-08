package com.msbeigi.employeemanagerbackend.auth;

import com.msbeigi.employeemanagerbackend.model.EmployeeDTO;

public record AuthenticationResponse(
        String token,
        EmployeeDTO employeeDTO
) {
}
