package com.tva.biblioteca.entidades;
import java.util.UUID;


import jakarta.persistence.*;



@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nombre;

    public Autor(){}

    public String getId() {
        return id.toString();
    }
    public String getNombre() {
        return nombre;
    }
    public void setId(String id) {
        this.id = UUID.fromString(id);
    }
    public void setNombre(String name) {
        this.nombre = name;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Autor: [id: "+getId()+", nombre: "+getNombre()+"]";
    }
}
