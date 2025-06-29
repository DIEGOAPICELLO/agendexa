package com.agendexa.entity;

import com.agendexa.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
