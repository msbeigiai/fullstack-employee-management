package com.msbeigi.employeemanagerbackend.service.impl;

import com.msbeigi.employeemanagerbackend.entity.Employee;
import com.msbeigi.employeemanagerbackend.exceptions.EmployeeNotFoundException;
import com.msbeigi.employeemanagerbackend.model.EmployeeRequestBody;
import com.msbeigi.employeemanagerbackend.repository.EmployeeRepository;
import com.msbeigi.employeemanagerbackend.service.EmployeeService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addEmployee(EmployeeRequestBody employeeRequestBody) {

        if (employeeRepository.existsEmployeeByEmail(employeeRequestBody.email())) {
            throw new DuplicateRequestException("Email already taken");
        }

        Employee employee = new Employee();

        employee.setName(employeeRequestBody.name());
        employee.setEmail(employeeRequestBody.email());
        employee.setJobTitle(employeeRequestBody.jobTitle());
        employee.setPhone(employeeRequestBody.phone());
        employee.setImageUrl(employeeRequestBody.imageUrl());
        employee.setPassword(passwordEncoder.encode(employeeRequestBody.password()));

        employee.setEmployeeCode(UUID.randomUUID().toString());
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee with id %s not found.".formatted(id)));
    }
}
