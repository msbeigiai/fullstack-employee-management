package com.msbeigi.employeemanagerbackend.email;

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String token) {
        return "Hello " + name +
                ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n"
                + getVerificationUrl(host, token) + "\n\nThe support team";
    }

    private static String getVerificationUrl(String host, String token) {
        return host + "/api/v1/employees?token=" + token;
    }
}
