package com.Spring.Quiz_Application.service;

import com.Spring.Quiz_Application.Dto.SignUpPageDto;
import com.Spring.Quiz_Application.entity.User;
import com.Spring.Quiz_Application.repository.UserRepository;
import com.Spring.Quiz_Application.utils.CustomMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationService {
    @Autowired
    private CustomMailSender mailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean isEmailAlreadyRegistered(String email) {
        return userRepository.existsByEmail(email);
    }
    public String register(SignUpPageDto signUpPageDto)
    {

       User user = new User();

       user.setEmail(signUpPageDto.getEmail());
       user.setName(signUpPageDto.getName());
       user.setPassword(passwordEncoder.encode(signUpPageDto.getPassword()));
       user.setRole(signUpPageDto.getRole());
        signUpPageDto.setActive(false);
        String verificationToken = generateVerificationToken();
        signUpPageDto.setVerificationToken(verificationToken);
        user.setVerificationToken(signUpPageDto.getVerificationToken());
        user.setActive(signUpPageDto.isActive());


        userRepository.save(user);
        sendVerificationEmail(signUpPageDto.getEmail(), verificationToken);


        return "home";
    }

    public boolean verifyEmail(String verificationToken) {
        User user = userRepository.findByVerificationToken(verificationToken);
        if (user != null) {
            user.setActive(true);
            user.setVerificationToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    private String generateVerificationToken() {

        return UUID.randomUUID().toString();
    }
    private void sendVerificationEmail(String email, String verificationToken) {
        String subject = "Email Verification";
        String body = "Please click the link below to verify your email:\n\n"
                + "http://localhost:8080/verify?token=" + verificationToken;

        mailSender.sendEmail(email, subject, body);
    }

}




