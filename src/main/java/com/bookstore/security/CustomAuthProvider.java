package com.bookstore.security;

import com.bookstore.exception.InvalidInputException;
import com.bookstore.user.User;
import com.bookstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userEmail = authentication.getName();
        String userPassword = authentication.getCredentials().toString();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists"));

        if (!user.getIsActive()) {
            throw new DisabledException("User is disabled");
        }

        if (!passwordEncoder.matches(userPassword, user.getPassword())) {
            throw new InvalidInputException("User password is invalid");
        }

        var simpleGrantedAuthority = new SimpleGrantedAuthority(user.getUserRole().toString());

        return new UsernamePasswordAuthenticationToken(user,
                userPassword, Collections.singleton(simpleGrantedAuthority));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
