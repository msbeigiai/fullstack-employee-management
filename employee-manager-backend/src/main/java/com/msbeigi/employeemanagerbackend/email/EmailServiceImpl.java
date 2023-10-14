package com.msbeigi.employeemanagerbackend.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final static String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private final static String RESEND_USER_ACCOUNT_VERIFICATION = "New User Account Verification - RESEND";
    private final static String RESET_PASSWORD = "Reset Password";

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    @Async
    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(details.getRecipient());
            simpleMailMessage.setText(details.getMsgBody());
            simpleMailMessage.setSubject(details.getSubject());

            javaMailSender.send(simpleMailMessage);
            return "Mail sent successfully...";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String sendSimpleMail(String name, String to, String token, String url) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText(EmailUtils.getEmailMessage(name, url, token));
            javaMailSender.send(simpleMailMessage);
            return "Mail sent successfully...";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String resendSimpleMail(String name, String to, String token, String url) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject(RESEND_USER_ACCOUNT_VERIFICATION);
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText(EmailUtils.getResendEmailMessage(name, url, token));
            javaMailSender.send(simpleMailMessage);
            return "Mail sent successfully...";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String resendPasswordSimpleMail(String name, String to, String token, String url) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject(RESET_PASSWORD);
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText(EmailUtils.getResetPasswordEmailMessage(name, url, token));
            javaMailSender.send(simpleMailMessage);
            return "Mail sent successfully...";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String sendMimeMessageWithAttachment(EmailDetails details) {
        return null;
    }

    @Override
    public String sendMimeMessageWithEmbeddedImages(EmailDetails details) {
        return null;
    }

    @Override
    public String sendMimeMessageWithEmbeddedFile(EmailDetails details) {
        return null;
    }

    @Override
    public String sendHtmlEmail(EmailDetails details) {
        return null;
    }

    @Override
    public String sendHtmlEmailWithEmbeddedFiles(EmailDetails details) {
        return null;
    }
}
