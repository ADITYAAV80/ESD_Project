package com.aditya.project.Encryption;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptionService {

    //this is an object from spring boot which is linked with security config and this checks if password is correct or not and also encodes
    //I have set encoder as no encoder by updating in security config
    private final PasswordEncoder passwordEncoder;

    //it doesn't encode anything if we want to encode we can use it in when doing Create, it's not used anywhere
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    //validate method is used by Offer Service during login
    public boolean validates(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
