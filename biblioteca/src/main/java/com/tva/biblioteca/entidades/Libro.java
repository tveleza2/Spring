package com.tva.biblioteca.entidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Libro {

    @Id
    private Long isbn;

    private String titulo;

    private int ejemplares;

    private boolean active;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;
    
}
