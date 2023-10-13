package com.msbeigi.employeemanagerbackend.event.listener;

import com.msbeigi.employeemanagerbackend.email.EmailService;
import com.msbeigi.employeemanagerbackend.event.RegistrationCompleteEvent;
import com.msbeigi.employeemanagerbackend.model.EmployeeDTO;
import com.msbeigi.employeemanagerbackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final EmployeeService employeeService;
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        EmployeeDTO employeeDTO = event.getEmployeeDTO();
        String token = UUID.randomUUID().toString();

        employeeService.verifyToken(token, employeeDTO);

        String url = event.getApplicationUrl() + "verifyRegistration?token=";

        emailService.sendSimpleMail(employeeDTO.name(), employeeDTO.email(), token, url);
//        log.info("Click the link to verify your account: {}", url);
    }
}
