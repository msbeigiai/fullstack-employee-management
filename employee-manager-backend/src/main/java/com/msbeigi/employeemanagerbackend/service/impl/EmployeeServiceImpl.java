package com.msbeigi.employeemanagerbackend.service.impl;

import com.msbeigi.employeemanagerbackend.entity.Employee;
import com.msbeigi.employeemanagerbackend.entity.PasswordResetToken;
import com.msbeigi.employeemanagerbackend.entity.VerificationToken;
import com.msbeigi.employeemanagerbackend.exceptions.EmployeeNotFoundException;
import com.msbeigi.employeemanagerbackend.exceptions.ResourceNotFoundException;
import com.msbeigi.employeemanagerbackend.jwt.JwtUtil;
import com.msbeigi.employeemanagerbackend.model.EmployeeDTO;
import com.msbeigi.employeemanagerbackend.model.EmployeeDTOMapper;
import com.msbeigi.employeemanagerbackend.model.EmployeeRequestBody;
import com.msbeigi.employeemanagerbackend.model.EmployeeUpdateRequestBody;
import com.msbeigi.employeemanagerbackend.repository.EmployeeRepository;
import com.msbeigi.employeemanagerbackend.repository.PasswordResetTokenRepository;
import com.msbeigi.employeemanagerbackend.repository.VerificationTokenRepository;
import com.msbeigi.employeemanagerbackend.service.EmployeeService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeDTOMapper employeeDTOMapper;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final JwtUtil jwtUtil;

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

//        VerificationToken verificationToken = new VerificationToken(employee);
//        verificationTokenRepository.save(verificationToken);
//        emailService.sendSimpleMail(employee.getName(), employee.getEmail(), verificationToken.getToken());

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
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("token not found!"));
        Employee employee = employeeRepository.findByEmailIgnoreCase(verificationToken.getEmployee().getEmail())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found!"));

//        Employee employee1 = verificationToken.getEmployee();

        Calendar calendar = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "Token is expired";
        }

        employee.setEnabled(true);
        employeeRepository.save(employee);

        return jwtUtil.issueToken(employee.getEmail(), "ROLE_USER");
    }

    @Override
    public void validateVerificationToken(String token, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findByEmailIgnoreCase(employeeDTO.email())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        VerificationToken verificationToken = new VerificationToken(employee, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken =
                verificationTokenRepository.findByToken(oldToken)
                        .orElseThrow(() -> new ResourceNotFoundException("token not found"));
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public Employee findByEmailIgnoreCase(String email) {
        return employeeRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    @Override
    public void createPasswordResetToken(Employee employee, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(employee, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {

        PasswordResetToken passwordResetToken =
                passwordResetTokenRepository.findByToken(token)
                        .orElseThrow(() -> new ResourceNotFoundException("Password reset token not found!"));

        Employee employee = passwordResetToken.getEmployee();

        Calendar calendar = Calendar.getInstance();

        if ((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "Token is expired";
        }

        return "valid"; /*jwtUtil.issueToken(employee.getEmail(), "ROLE_USER");*/
    }

    @Override
    public Optional<Employee> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).get().getEmployee());
    }

    @Override
    public void changePassword(Employee employee, String newPassword) {
        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);
    }

    @Override
    public boolean checkIfValidOldPassword(Employee employee, String oldPassword) {
        return passwordEncoder.matches(oldPassword, employee.getPassword());
    }
}














