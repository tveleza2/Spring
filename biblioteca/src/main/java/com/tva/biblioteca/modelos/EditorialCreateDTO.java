package com.tva.biblioteca.modelos;


import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditorialCreateDTO {
    private UUID id;
    private String nombre;
}
