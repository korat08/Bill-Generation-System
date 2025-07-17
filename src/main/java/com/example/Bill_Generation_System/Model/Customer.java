package com.example.Bill_Generation_System.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name is required")
    @Column(nullable = false,unique = true)
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid Email Format")
    @Column(nullable = false,unique = true)
    private String email;

    @NotNull(message = "Phone Number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid India phone Number(must be start with 6-9 and be 10 Digits)"
    )
    @Column(nullable = false,unique = true)
    private String phoneNo;

    @NotNull
    @Column(nullable = false)
    private String city;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
