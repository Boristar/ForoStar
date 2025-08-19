package com.Boristar.Foro;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "ana123";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("La contraseña 'ana123' encriptada es:");
        System.out.println(encodedPassword);
    }
}