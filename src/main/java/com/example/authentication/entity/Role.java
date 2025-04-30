package com.example.authentication.entity;

import com.example.authentication.dto.RoleName;
import jakarta.annotation.Resource;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;


}
