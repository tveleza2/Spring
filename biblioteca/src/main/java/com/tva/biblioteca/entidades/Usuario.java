package com.tva.biblioteca.entidades;

import java.util.UUID;

import com.tva.biblioteca.enums.Rol;

import jakarta.persistence.*;


@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nombre;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario(){}

    public String getEmail() {
        return email;
    }
    public UUID getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getPassword() {
        return password;
    }
    public Rol getRol() {
        return rol;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setNombre(String name) {
        this.nombre = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario: [id: "+getId()+", email: "+getEmail()+", nombre: "+getNombre()+"]";
    }
    
}
