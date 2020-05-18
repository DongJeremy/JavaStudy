package org.cloud.ssm.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordUtil {

    public static void main(String[] args) {
        String passString = new BCryptPasswordEncoder().encode("password");
        System.out.println(passString);
    }

}
