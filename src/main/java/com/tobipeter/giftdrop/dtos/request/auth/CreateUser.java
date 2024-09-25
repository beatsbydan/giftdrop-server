package com.tobipeter.giftdrop.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUser {
    @NotBlank(message = "Firstname cannot be blank.")
    private String firstName;

    @NotBlank(message = "Username cannot be blank.")
    private String userName;

    @NotBlank(message = "Email cannot be blank.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Enter a valid email address."
    )
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    @NotBlank(message = "Role cannot be empty.")
    private String role;
}
