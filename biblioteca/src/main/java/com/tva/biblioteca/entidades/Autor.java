package com.tva.biblioteca.entidades;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;



@Entity
public class Autor {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;

    public Autor(){}

    public String getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setNombre(String name) {
        this.nombre = name;
    }
}
