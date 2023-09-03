package com.Spring.Quiz_Application.controller;
import com.Spring.Quiz_Application.Dto.SignUpPageDto;
import com.Spring.Quiz_Application.entity.User;
import com.Spring.Quiz_Application.repository.UserRepository;
import com.Spring.Quiz_Application.utils.CustomMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Spring.Quiz_Application.entity.Question;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.util.UUID;

@RestController
public class HomeController {

    @Autowired
    private CustomMailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender;
    @GetMapping("/register")
    public  ModelAndView showRegistrationForm(Model model) {
        model.addAttribute("user", new SignUpPageDto());
        return new ModelAndView("SignupForm");
    }

    @GetMapping({"/","/home"})

    public ModelAndView homeHandler(){
        return new ModelAndView("home");
    }
    @GetMapping("/host/dashboard")
    public ModelAndView hostDashboard() {
        return new ModelAndView("HostDashboard");
    }
    @GetMapping("/candidate/dashboard")
    public ModelAndView candidateDashboard() {
        return new ModelAndView("CandidateDashboard");
    }
    @GetMapping("/signin")
    public ModelAndView showLoginForm(Model model) {
        return new ModelAndView("LoginForm");
    }
    @GetMapping("/access-denied")
    public ModelAndView authenticationFailed(){
        return new ModelAndView("AccessDeniedPage");
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    

    
    @GetMapping("/show-Change-Password")
    public ModelAndView openSettings(){
        return new ModelAndView("ChangePasswordForm");
    }
    
    @PostMapping("/settings/change-password")
    public ModelAndView processChangePassword( String email, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, Model model) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {


            if (passwordEncoder.matches(oldPassword, existingUser.getPassword())) {

                String encryptedPassword = passwordEncoder.encode(newPassword);
                existingUser.setPassword(encryptedPassword);



                userRepository.save(existingUser);
                model.addAttribute("successMessage", "Password changed successfully!");
            } else {
                model.addAttribute("errorMessage", "Old password is incorrect!");
            }
        } else {
            model.addAttribute("errorMessage", "User not found!");
        }
        return new ModelAndView("ChangePasswordForm");
    }
    
    @GetMapping("/forgot-password")
    public ModelAndView showForgotPasswordForm() {
        return new ModelAndView("ForgotPasswordForm");
    }

    @PostMapping("/forgot-password")
    public ModelAndView processForgotPassword(@RequestParam("email") String email, Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userRepository.save(user);

            String resetLink = "http://localhost:8080/reset-password?token=" + token;
            String emailSubject = "Password Reset Request";
            String emailContent = "Click the link below to reset your password:\n" + resetLink;

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(emailSubject);
            mailMessage.setText(emailContent);
            javaMailSender.send(mailMessage);

            model.addAttribute("successMessage", "Password reset link sent to your email.");
        } else {
            model.addAttribute("errorMessage", "User not found!");
        }
        return new ModelAndView("ForgotPasswordForm");
    }

    @GetMapping("/reset-password")
    public ModelAndView showResetPasswordForm(@RequestParam("token") String token, Model model) {
        User user = userRepository.findByResetToken(token);
        if (user != null) {
            model.addAttribute("token", token);
            return new ModelAndView("ResetPasswordForm");
        } else {
            model.addAttribute("errorMessage", "Invalid or expired reset token.");
            return new ModelAndView("ForgotPasswordForm");
        }
    }

    @PostMapping("/reset-password")
    public ModelAndView processResetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword, Model model) {
        User user = userRepository.findByResetToken(token);
        if (user != null) {
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            user.setResetToken(null);
            userRepository.save(user);

            model.addAttribute("successMessage", "Password reset successfully!");
        } else {
            model.addAttribute("errorMessage", "Invalid or expired reset token.");
        }
        return new ModelAndView("LoginForm");
    }
}

