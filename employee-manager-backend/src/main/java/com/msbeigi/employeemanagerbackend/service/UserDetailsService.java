package com.msbeigi.employeemanagerbackend.service;

import com.msbeigi.employeemanagerbackend.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService  {

    private final EmployeeRepository employeeRepository;

    public UserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with username %s not found!".formatted(username))
                );
    }
}
