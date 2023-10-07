package com.msbeigi.employeemanagerbackend.controller;

import com.msbeigi.employeemanagerbackend.entity.Employee;
import com.msbeigi.employeemanagerbackend.model.EmployeeRequestBody;
import com.msbeigi.employeemanagerbackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

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
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
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
