package com.bookstore.auth;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);

    void register(RegisterRequest request);

    void logout();

    AuthResponse refresh(RefreshTokenRequest tokenRequest);
}
