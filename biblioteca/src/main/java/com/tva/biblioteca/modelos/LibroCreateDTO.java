package com.tva.biblioteca.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class LibroCreateDTO {
    private Long isbn;
    private String titulo;
    private int ejemplares;
    private String idAutor;
    private String idEditorial;
    private boolean active;
}
