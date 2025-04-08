package com.tva.biblioteca.modelos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AutorCreateDTO {
    private UUID id;
    private String nombre;
    private boolean active = true;
}
