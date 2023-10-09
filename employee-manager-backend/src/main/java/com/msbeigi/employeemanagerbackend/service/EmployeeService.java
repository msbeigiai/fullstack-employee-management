package com.msbeigi.employeemanagerbackend.service;

import com.msbeigi.employeemanagerbackend.entity.Employee;
import com.msbeigi.employeemanagerbackend.model.EmployeeRequestBody;

import java.util.List;

public interface EmployeeService {

    void addEmployee(EmployeeRequestBody employeeRequestBody);

    List<Employee> findAllEmployees();

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long id);

    Employee findEmployeeById(Long id);
}
