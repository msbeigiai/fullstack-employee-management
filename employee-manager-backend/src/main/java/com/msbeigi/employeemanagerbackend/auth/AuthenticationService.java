package com.msbeigi.employeemanagerbackend.auth;

import com.msbeigi.employeemanagerbackend.entity.Employee;
import com.msbeigi.employeemanagerbackend.jwt.JwtUtil;
import com.msbeigi.employeemanagerbackend.model.EmployeeDTO;
import com.msbeigi.employeemanagerbackend.model.EmployeeDTOMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager manager;
    private final EmployeeDTOMapper dtoMapper;
    private final JwtUtil jwtUtil;

    public AuthenticationService (
            AuthenticationManager manager,
            EmployeeDTOMapper dtoMapper,
            JwtUtil jwtUtil) {
        this.manager = manager;
        this.dtoMapper = dtoMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        Employee principal = (Employee) authentication.getPrincipal();
        EmployeeDTO employeeDTO = dtoMapper.apply(principal);
        String token = jwtUtil.issueToken(employeeDTO.email(), employeeDTO.roles());
        return new AuthenticationResponse(token, employeeDTO);
    }
}








