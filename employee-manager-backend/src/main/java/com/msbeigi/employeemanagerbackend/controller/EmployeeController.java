package com.msbeigi.employeemanagerbackend.controller;

import com.msbeigi.employeemanagerbackend.event.RegistrationCompleteEvent;
import com.msbeigi.employeemanagerbackend.jwt.JwtUtil;
import com.msbeigi.employeemanagerbackend.model.EmployeeDTO;
import com.msbeigi.employeemanagerbackend.model.EmployeeRequestBody;
import com.msbeigi.employeemanagerbackend.model.EmployeeUpdateRequestBody;
import com.msbeigi.employeemanagerbackend.model.HttpResponse;
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

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtUtil jwtUtil;
    private final ApplicationEventPublisher publisher;

    @GetMapping("/allEmployees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() throws InterruptedException {
//        Thread.sleep(1500);
//        throw new RuntimeException("Nothing fetched");
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

        String token = jwtUtil.issueToken(employeeRequestBody.email(), "ROLE_USER");

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

    @GetMapping
    public ResponseEntity<?> confirmEmployeeAccount(@RequestParam("token") String token) {
        boolean isSuccess = employeeService.verifyToken(token);
        return ResponseEntity.ok()
                .body(
                        HttpResponse
                                .builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("SUCCESS", isSuccess))
                                .message("Account verified")
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
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
                + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
