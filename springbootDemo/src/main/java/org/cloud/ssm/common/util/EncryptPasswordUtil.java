package org.cloud.ssm.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptPasswordUtil {
    public static String encrypt(String plainText) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(plainText);
        System.out.println(passwordEncoder.matches("Passw0rd", encryptedPassword)); // true
        System.out.println(passwordEncoder.matches("password", encryptedPassword)); // false
        return encryptedPassword;
    }
    
    public static void main(String[] args) {
        System.out.println(encrypt("password"));
        System.out.println(encrypt("12345678"));
        System.out.println(encrypt("sinocom11#"));
        
    }
}
