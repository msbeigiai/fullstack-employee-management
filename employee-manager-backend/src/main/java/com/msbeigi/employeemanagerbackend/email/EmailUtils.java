package com.msbeigi.employeemanagerbackend.email;

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String token) {
        return "Hello " + name +
                ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n"
                + getVerificationUrl(host, token) + "\n\nThe support team";
    }

    public static String getResendEmailMessage(String name, String host, String token) {
        return "Hello " + name +
                ",\n\nYour new verification link has been sent. Please click the link below to verify your account. \n\n"
                + getVerificationUrl(host, token) + "\n\nThe support team";
    }

    public static String getResetPasswordEmailMessage(String name, String host, String token) {
        return "Hello " + name +
                ",\n\nYour reset password link has been sent. Please click the link below to reset your password. \n\n"
                + getVerificationUrl(host, token) + "\n\nThe support team";
    }

    private static String getVerificationUrl(String host, String token) {
        return host + token;
    }
}
