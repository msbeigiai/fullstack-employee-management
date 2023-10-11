package com.msbeigi.employeemanagerbackend.service;

import com.msbeigi.employeemanagerbackend.model.EmployeeDTO;
import com.msbeigi.employeemanagerbackend.model.EmployeeRequestBody;
import com.msbeigi.employeemanagerbackend.model.EmployeeUpdateRequestBody;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO addEmployee(EmployeeRequestBody employeeRequestBody);

    List<EmployeeDTO> findAllEmployees();

    void updateEmployee(Long id, EmployeeUpdateRequestBody employeeUpdateRequestBody);

    void deleteEmployee(Long id);

    EmployeeDTO findEmployeeById(Long id);

    boolean verifyToken(String token);
}
