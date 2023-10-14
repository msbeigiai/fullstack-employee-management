package com.msbeigi.employeemanagerbackend.service;

import com.msbeigi.employeemanagerbackend.entity.Employee;
import com.msbeigi.employeemanagerbackend.entity.VerificationToken;
import com.msbeigi.employeemanagerbackend.model.EmployeeDTO;
import com.msbeigi.employeemanagerbackend.model.EmployeeRequestBody;
import com.msbeigi.employeemanagerbackend.model.EmployeeUpdateRequestBody;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    EmployeeDTO addEmployee(EmployeeRequestBody employeeRequestBody);

    List<EmployeeDTO> findAllEmployees();

    void updateEmployee(Long id, EmployeeUpdateRequestBody employeeUpdateRequestBody);

    void deleteEmployee(Long id);

    EmployeeDTO findEmployeeById(Long id);

    String validateVerificationToken(String token);

    void validateVerificationToken(String token, EmployeeDTO employeeDTO);

    VerificationToken generateNewVerificationToken(String oldToken);

    Employee findByEmailIgnoreCase(String email);

    void createPasswordResetToken(Employee employee, String token);

    String validatePasswordResetToken(String token);

    Optional<Employee> getUserByPasswordResetToken(String token);

    void changePassword(Employee employee, String newPassword);

    boolean checkIfValidOldPassword(Employee employee, String oldPassword);
}
