package com.example.subscription.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
/**
 * Creates login tokens and checks that tokens are genuine and still usable.
 */
public class JwtService {
    private final SecretKey key;
    private final long expirationMs;

    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Creates a token containing the user's name and roles.
     */
    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getAuthorities().stream().map(a -> a.getAuthority()).toList());
        Date now = new Date();
        return Jwts.builder().claims(claims).subject(user.getUsername()).issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs)).signWith(key).compact();
    }

    /**
     * Gets the username stored inside a token.
     */
    public String username(String token) {
        return claim(token, Claims::getSubject);
    }

    /**
     * Checks that the token belongs to this user and has not expired.
     */
    public boolean isValid(String token, UserDetails user) {
        return user.getUsername().equals(username(token)) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return claim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T claim(String token, Function<Claims, T> resolver) {
        return resolver.apply(Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload());
    }
}
