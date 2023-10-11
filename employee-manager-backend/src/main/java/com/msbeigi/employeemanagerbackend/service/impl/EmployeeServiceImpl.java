package com.msbeigi.employeemanagerbackend.service.impl;

import com.msbeigi.employeemanagerbackend.entity.Employee;
import com.msbeigi.employeemanagerbackend.entity.VerificationToken;
import com.msbeigi.employeemanagerbackend.exceptions.EmployeeNotFoundException;
import com.msbeigi.employeemanagerbackend.exceptions.ResourceNotFoundException;
import com.msbeigi.employeemanagerbackend.model.EmployeeDTO;
import com.msbeigi.employeemanagerbackend.model.EmployeeDTOMapper;
import com.msbeigi.employeemanagerbackend.model.EmployeeRequestBody;
import com.msbeigi.employeemanagerbackend.model.EmployeeUpdateRequestBody;
import com.msbeigi.employeemanagerbackend.repository.EmployeeRepository;
import com.msbeigi.employeemanagerbackend.repository.VerificationTokenRepository;
import com.msbeigi.employeemanagerbackend.service.EmployeeService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeDTOMapper employeeDTOMapper;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public EmployeeDTO addEmployee(EmployeeRequestBody employeeRequestBody) {

        if (employeeRepository.existsByEmail(employeeRequestBody.email())) {
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
        employee.setEnabled(false);

        employeeRepository.save(employee);

        VerificationToken verificationToken = new VerificationToken(employee);
        verificationTokenRepository.save(verificationToken);

        // TODO send email to user with token

        return employeeDTOMapper.apply(employee);
    }

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return employeeRepository.findAll()
                .stream().map(employeeDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEmployee(Long id, EmployeeUpdateRequestBody employeeUpdateRequestBody) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id %s not found.".formatted(id)));

        employee.setName(employeeUpdateRequestBody.name());
        employee.setEmail(employeeUpdateRequestBody.email());
        employee.setJobTitle(employeeUpdateRequestBody.jobTitle());
        employee.setPhone(employeeUpdateRequestBody.phone());
        employee.setImageUrl(employeeUpdateRequestBody.imageUrl());

        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDTO findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee with id %s not found.".formatted(id)));
        return employeeDTOMapper.apply(employee);
    }

    @Override
    public boolean verifyToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("token not found!"));
        Employee employee = employeeRepository.findByEmailIgnoreCase(verificationToken.getEmployee().getEmail())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found!"));
        employee.setEnabled(true);
        employeeRepository.save(employee);
        return true;
    }
}
