package com.bookstore.security;

import com.bookstore.user.User;
import com.bookstore.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityUtil {

    public static User getCurrentUser(UserRepository userRepository) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public static User findAdmin(UserRepository userRepository) {
        return userRepository.findByEmail("admin@gmail.com")
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));
    }

}
