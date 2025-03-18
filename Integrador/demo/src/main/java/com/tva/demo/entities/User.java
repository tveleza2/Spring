package com.tva.demo.entities;

import java.util.UUID;

import com.tva.demo.enums.Rol;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(unique = true, nullable = false)
    private String email;

    private String userName;
    private String surename;
    private String password;
    private Rol rol = Rol.USER;
}
