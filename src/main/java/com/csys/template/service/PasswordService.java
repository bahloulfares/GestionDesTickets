package com.csys.template.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    
    private final BCryptPasswordEncoder passwordEncoder;
    
    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    public String encryptPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            return plainPassword;
        }
        
        // Si le mot de passe est déjà crypté, on le retourne tel quel
        if (plainPassword.startsWith("{bcrypt}")) {
            return plainPassword;
        }
        
        return "{bcrypt}" + passwordEncoder.encode(plainPassword.toLowerCase());
    }
}