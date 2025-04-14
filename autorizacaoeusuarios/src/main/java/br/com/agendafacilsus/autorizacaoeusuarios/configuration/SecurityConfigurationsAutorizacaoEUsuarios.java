package br.com.agendafacilsus.autorizacaoeusuarios.configuration;

import br.com.agendafacilsus.autorizacaoeusuarios.filter.SecurityFilterAutorizacaoEUsuarios;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper.FetchMapper;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper.IFetchMapper;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper.IUserMapper;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfigurationsAutorizacaoEUsuarios implements WebMvcConfigurer {
    
    private final SecurityFilterAutorizacaoEUsuarios securityFilter;

    private static final String[] swaggerWhiteList = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/springdoc/**"
    };

    private static final String ALLOWED_ORIGIN = "http://localhost:8080";


    @Bean
    public SecurityFilterChain securityFilterChainAutorizacaoEUsuarios(HttpSecurity httpSecurity) throws Exception {

        return  httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/validation").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/ping").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/favicon.ico/**").permitAll()
                        .requestMatchers(swaggerWhiteList).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Primary
    public AuthenticationManager authenticationManagerAutorizacaoEUsuarios(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(ALLOWED_ORIGIN)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public PasswordEncoder passwordEncoderAutorizacaoEUsuarios(){
        return new BCryptPasswordEncoder();
    }

    @Bean public IUserMapper userMapperAutorizacaoEUsuarios() {
        return new UserMapper();
    }

    @Bean public IFetchMapper fetchMapperAutorizacaoEUsuarios() {
        return new FetchMapper();
    }
}
