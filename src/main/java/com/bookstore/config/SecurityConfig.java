package com.bookstore.config;

import com.bookstore.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((s)-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((req)->
                            req.requestMatchers("/auth/**").permitAll()
                                    .anyRequest().authenticated()
                        )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
