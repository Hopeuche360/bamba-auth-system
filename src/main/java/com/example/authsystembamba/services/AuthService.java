package com.example.authsystembamba.services;

import com.example.authsystembamba.pojos.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    String signup(SignupRequest request);
}
