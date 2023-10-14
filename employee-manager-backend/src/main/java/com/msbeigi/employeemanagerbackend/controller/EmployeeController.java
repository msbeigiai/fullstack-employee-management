package com.msbeigi.employeemanagerbackend.controller;

import com.msbeigi.employeemanagerbackend.email.EmailService;
import com.msbeigi.employeemanagerbackend.entity.Employee;
import com.msbeigi.employeemanagerbackend.entity.VerificationToken;
import com.msbeigi.employeemanagerbackend.event.RegistrationCompleteEvent;
import com.msbeigi.employeemanagerbackend.jwt.JwtUtil;
import com.msbeigi.employeemanagerbackend.model.*;
import com.msbeigi.employeemanagerbackend.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtUtil jwtUtil;
    private final ApplicationEventPublisher publisher;
    private final EmailService emailService;

    @GetMapping("/allEmployees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() throws InterruptedException {
        return ResponseEntity.ok()
                .body(
                        employeeService.findAllEmployees()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeByID(@PathVariable("id") Long id) {

        return ResponseEntity.ok()
                .body(
                        employeeService.findEmployeeById(id)
                );
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(
            @RequestBody EmployeeRequestBody employeeRequestBody,
            final HttpServletRequest request) {

        EmployeeDTO employeeDTO = employeeService.addEmployee(employeeRequestBody);

        publisher.publishEvent(new RegistrationCompleteEvent(employeeDTO, applicationUrl(request)));

        return ResponseEntity.created(URI.create(""))
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("employee", employeeDTO))
                                .message("Employee Created")
                                .status(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .build()
                );
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmEmployeeAccount(@RequestParam("token") String token) {
        String jwtToken = employeeService.validateVerificationToken(token);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .body(
                        HttpResponse
                                .builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("SUCCESS", true))
                                .message("Account verified")
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build()
                );
    }

    @GetMapping("/resendVerifyToken")
    public ResponseEntity<?> resendVerificationToken(@RequestParam("token") String oldToken,
                                                     HttpServletRequest request) {
        VerificationToken verificationToken =
                employeeService.generateNewVerificationToken(oldToken);
        Employee employee = verificationToken.getEmployee();
        resendVerificationMail(employee, applicationUrl(request), verificationToken);
        return ResponseEntity.ok()
                .body(
                        HttpResponse
                                .builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("Verification Link Sent", true))
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build()
                );
    }

    private void resendVerificationMail(Employee employee, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "api/v1/employees/confirm?token=";
        emailService.resendSimpleMail(employee.getName(), employee.getEmail(), verificationToken.getToken(), url);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordModel passwordModel,
                                           HttpServletRequest request) {
        Employee employee = employeeService.findByEmailIgnoreCase(passwordModel.email());
        String token = UUID.randomUUID().toString();
        employeeService.createPasswordResetToken(employee, token);
        String url = passwordResetTokenMail(employee, applicationUrl(request), token);
        return ResponseEntity.created(URI.create(""))
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("URL", url + token))
                                .message("Reset password URL created")
                                .status(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .build()
                );
    }

    @PostMapping("/savePassword")
    public ResponseEntity<?> savePassword(@RequestParam("token") String token,
                                          @RequestBody PasswordModel passwordModel) {
        String result = employeeService.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid")) {
            return ResponseEntity.badRequest()
                    .body(
                            HttpResponse.builder()
                                    .timeStamp(LocalDateTime.now().toString())
                                    .data(Map.of("Invalid token", result))
                                    .message("token is not matched.")
                                    .status(HttpStatus.BAD_REQUEST)
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .build()
                    );
        }

        Optional<Employee> employee = employeeService.getUserByPasswordResetToken(token);

        if (employee.isPresent()) {
            employeeService.changePassword(employee.get(), passwordModel.newPassword());
            return ResponseEntity.created(URI.create(""))
                    .body(
                            HttpResponse.builder()
                                    .timeStamp(LocalDateTime.now().toString())
                                    .data(Map.of("SUCCESS", true))
                                    .message("Password reset successfully")
                                    .status(HttpStatus.CREATED)
                                    .statusCode(HttpStatus.CREATED.value())
                                    .build()
                    );
        } else {
            return ResponseEntity.badRequest()
                    .body(
                            HttpResponse.builder()
                                    .timeStamp(LocalDateTime.now().toString())
                                    .data(Map.of("FAILED", false))
                                    .message("Invalid token")
                                    .status(HttpStatus.BAD_REQUEST)
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .build()
                    );
        }

    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordModel passwordModel) {
        Employee employee = employeeService.findByEmailIgnoreCase(passwordModel.email());
        if (!employeeService.checkIfValidOldPassword(employee, passwordModel.oldPassword())) {
            return ResponseEntity.badRequest()
                    .body(
                            HttpResponse.builder()
                                    .timeStamp(LocalDateTime.now().toString())
                                    .data(Map.of("FAILED", false))
                                    .message("Invalid old password")
                                    .status(HttpStatus.BAD_REQUEST)
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .build()
                    );
        }

        employeeService.changePassword(employee, passwordModel.newPassword());

        return ResponseEntity.created(URI.create(""))
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("SUCCESS", true))
                                .message("Password changed successfully")
                                .status(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .build()
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id,
                                            @RequestBody EmployeeUpdateRequestBody employeeUpdateRequestBody) {
        employeeService.updateEmployee(id, employeeUpdateRequestBody);
        return ResponseEntity.ok()
                .body(
                        "Employee with id " + id + " has been updated"
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();
    }

    private String passwordResetTokenMail(Employee employee, String applicationUrl, String token) {
        String url = applicationUrl + "api/v1/employees/savePassword?token=";
        emailService.resendPasswordSimpleMail(employee.getName(), employee.getEmail(), token, url);
        return url;
    }
}
