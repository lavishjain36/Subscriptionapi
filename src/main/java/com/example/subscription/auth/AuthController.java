package com.example.subscription.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
/** Exposes authentication endpoints that issue access tokens after login. */
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    /** Authenticates the supplied credentials and returns a JWT for API requests. */
    public TokenResponse login(@RequestBody LoginRequest request) {
        UserDetails user = (UserDetails) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()))
                .getPrincipal();
        return new TokenResponse(jwtService.generateToken(user));
    }

    /** Request body containing the username and password used for login. */
    public record LoginRequest(String username, String password) {}

    /** Response body containing the bearer token used to call protected APIs. */
    public record TokenResponse(String token) {}
}
