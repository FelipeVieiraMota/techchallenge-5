package br.com.agendafacilsus.commonlibrary.service;

import br.com.agendafacilsus.commonlibrary.domains.dtos.UserDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    public Claims extractClaims(final String secret, final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Token validation failed", e);
        }
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public String generateToken(final String secret, final UserDto user){
        try{
            final List<String> roles = getRoles(user);
            final Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.login())
                    .withClaim("roles", roles)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    private static List<String> getRoles(final UserDto user) {
        final Collection<? extends GrantedAuthority> authorities = user.authorities();
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public String tokenSubject(final String secret, final String token){
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    public boolean validateToken(final String secret, final String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}