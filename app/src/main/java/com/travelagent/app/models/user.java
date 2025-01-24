package com.travelagent.app.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
