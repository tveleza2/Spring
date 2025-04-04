package com.tva.biblioteca.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LibroListarActivosDTO {
    public String titulo;
    public int ejemplares;
    public String autor;
}
