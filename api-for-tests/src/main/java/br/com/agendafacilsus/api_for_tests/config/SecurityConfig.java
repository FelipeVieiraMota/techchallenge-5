package br.com.agendafacilsus.api_for_tests.config;

import br.com.agendafacilsus.jwt_security_library.JwtAuthenticationFilter;
import br.com.agendafacilsus.jwt_security_library.JwtTokenValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public JwtTokenValidator jwtTokenValidator() {
        return new JwtTokenValidator(secretKey);
    }

    @Bean
    public SecurityFilterChain filterChain(
        final HttpSecurity http,
        final JwtTokenValidator jwtTokenValidator
    ) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/public/**").permitAll()
            .anyRequest().authenticated())
            .addFilterBefore (
                new JwtAuthenticationFilter(jwtTokenValidator),
                UsernamePasswordAuthenticationFilter.class
            )
            .build();
    }
}

