package com.msbeigi.employeemanagerbackend.controller;

import com.msbeigi.employeemanagerbackend.entity.Employee;
import com.msbeigi.employeemanagerbackend.jwt.JwtUtil;
import com.msbeigi.employeemanagerbackend.model.EmployeeRequestBody;
import com.msbeigi.employeemanagerbackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() throws InterruptedException {
        Thread.sleep(1500);
//        throw new RuntimeException("Nothing fetched");
        return ResponseEntity.ok()
                .body(
                        employeeService.findAllEmployees()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                employeeService.findEmployeeById(id),
                HttpStatus.CREATED
        );
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(
            @RequestBody EmployeeRequestBody request) {

        employeeService.addEmployee(request);

        String token = jwtUtil.issueToken(request.email(), "ROLE_USER");

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id,
                                                   @RequestBody EmployeeRequestBody employeeRequestBody) {

        Employee updateEmployee = employeeService.findEmployeeById(id);

        updateEmployee.setEmail(employeeRequestBody.email());
        updateEmployee.setName(employeeRequestBody.name());
        updateEmployee.setPhone(employeeRequestBody.phone());
        updateEmployee.setImageUrl(employeeRequestBody.imageUrl());
        updateEmployee.setJobTitle(employeeRequestBody.jobTitle());

        employeeService.updateEmployee(updateEmployee);

        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
