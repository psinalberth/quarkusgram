package com.github.psinalberth.user.application.port.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class NewUser {

    @NotEmpty(message = "Username is required.")
    private String username;

    @NotEmpty(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotEmpty(message = "Phone number is required.")
    private String phoneNumber;

    @NotEmpty(message = "Password is required.")
    private String password;
}
