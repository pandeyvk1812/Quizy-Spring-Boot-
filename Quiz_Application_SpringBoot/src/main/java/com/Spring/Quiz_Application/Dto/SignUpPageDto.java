package com.Spring.Quiz_Application.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpPageDto {
    @Email
    @Column(unique = true)
    private String email;
    @Pattern(regexp = "^[A-Za-z]+$", message = "Field can not be empty and must contain alphabets only")
    private String name;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&*+=]).{8,}$", message = "Invalid password!! It should contain atleast one capital, one small letter alphabet, atleast one number and one special character")
    private String password;
    @NotBlank
    private String role;
    private String verificationToken;
    private boolean active;


}
