package com.msbeigi.employeemanagerbackend.email;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendSimpleMail(String name, String to, String token, String url);
    String sendMimeMessageWithAttachment(EmailDetails details);
    String sendMimeMessageWithEmbeddedImages(EmailDetails details);
    String sendMimeMessageWithEmbeddedFile(EmailDetails details);
    String sendHtmlEmail(EmailDetails details);
    String sendHtmlEmailWithEmbeddedFiles(EmailDetails details);
}
