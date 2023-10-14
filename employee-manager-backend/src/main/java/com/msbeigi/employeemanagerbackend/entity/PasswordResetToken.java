package com.msbeigi.employeemanagerbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetToken {
    private static final int EXPIRATION_TIME = 10;

    @Id
    @SequenceGenerator(
            name = "verification_token_sequence",
            sequenceName = "verification_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "verification_token_sequence"
    )
    private Long id;
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createDate;

    private Date expirationTime;

    @OneToOne(targetEntity = Employee.class, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "employee_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_EMPLOYEE_PASSWORD_TOKEN")
    )
    private Employee employee;

    public PasswordResetToken(Employee employee, String token) {
        this.employee = employee;
        this.createDate = LocalDateTime.now();
        this.token = token;
        this.expirationTime = calculateExpirationDateTime(EXPIRATION_TIME);
    }

    public PasswordResetToken(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDateTime(EXPIRATION_TIME);
    }

    private Date calculateExpirationDateTime(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
