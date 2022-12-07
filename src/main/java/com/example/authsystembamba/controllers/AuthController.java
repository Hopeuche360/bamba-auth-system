package com.example.authsystembamba.controllers;

import com.example.authsystembamba.config.JwtUtils;
import com.example.authsystembamba.pojos.HttpResponse;
import com.example.authsystembamba.pojos.LoginRequest;
import com.example.authsystembamba.pojos.SignupRequest;
import com.example.authsystembamba.services.AuthService;
import com.example.authsystembamba.services.JwtUserDetailsService;
import com.example.authsystembamba.services.UserService;
import com.example.authsystembamba.services.serviceImpl.UserServiceImpl;
import com.example.authsystembamba.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.example.authsystembamba.utils.UserUtil.createHttpResponse;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final AuthService authService;

    private void authenticateUser(@RequestBody LoginRequest loginRequest, AuthenticationManager authenticationManager) {
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }


    @PostMapping("/sign-up")
    public ResponseEntity<HttpResponse> signup(@RequestBody SignupRequest request) {
        return createHttpResponse(CREATED, authService.signup(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        final UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        if (user != null)
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        else
            return ResponseEntity.status(400).body("Some error has occurred");
    }
}
