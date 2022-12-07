package com.example.authsystembamba.models;

import com.example.authsystembamba.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
//@Builder
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isAccountVerified;
    private Role role;

}
