package com.bookstore.auth;

import com.bookstore.config.cache.RedisService;
import com.bookstore.exception.AlreadyExistsException;
import com.bookstore.exception.AlreadyExpired;
import com.bookstore.security.UserDetailsImpl;
import com.bookstore.security.jwt.JwtUtils;
import com.bookstore.user.User;
import com.bookstore.user.UserMapper;
import com.bookstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        log.info("Login request, userEmail: {}", authRequest.getEmail());
        var authToken = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
        Authentication authentication = authenticationProvider.authenticate(authToken);
        User user = (User) authentication.getPrincipal();

        return AuthResponse.builder()
                .accessToken(jwtUtils.generateAccessToken(user))
                .refreshToken(jwtUtils.generateRefreshToken(user))
                .build();
    }

    @Override
    public void register(RegisterRequest request) {
        log.info("Register user request, userEmail:{}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("User already exists");
        }
        log.info("Creating new User entity");
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void logout() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("User logout, userEmail {}", userEmail);
        redisService.invalidateTokens(userEmail);
        SecurityContextHolder.clearContext();
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest tokenRequest) {
        String refreshToken = tokenRequest.getRefreshToken();
        String userEmail = jwtUtils.extractUserEmail(refreshToken);
        User user = userRepository.findByEmail(userEmail)
               .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if (!jwtUtils.isRefreshTokenValid(refreshToken, user.getEmail())) {
            throw new AlreadyExpired("Refresh token expired");
        }

        log.info("Generating new Tokens for userEmail {}", user.getEmail());
        redisService.invalidateTokens(userEmail);
        String newAccessToken = jwtUtils.generateAccessToken(user);
        String newRefreshToken = jwtUtils.generateRefreshToken(user);

        return AuthResponse.builder()
                .refreshToken(newRefreshToken)
                .accessToken(newAccessToken)
                .build();
    }


}
