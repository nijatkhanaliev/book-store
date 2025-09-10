package com.bookstore.auth;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    @Email(message = "Email is invalid")
    private String email;
    private String password;
}
