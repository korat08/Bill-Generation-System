package com.example.Bill_Generation_System.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid India phone Number(must be start with 6-9 and be 10 Digits)"
    )
    private String phoneNumber;

    @NotBlank
    private String password;

    @NotBlank
    private String city;
}
