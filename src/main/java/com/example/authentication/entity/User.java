package com.example.authentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", unique = true)
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private Timestamp createdAt;
    @Column(name = "updatedAt", updatable = false)
    @CreationTimestamp
    private Timestamp updatedAt;
}
