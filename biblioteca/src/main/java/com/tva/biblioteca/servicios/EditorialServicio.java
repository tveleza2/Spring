package com.tva.biblioteca.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tva.biblioteca.entidades.Editorial;
import com.tva.biblioteca.repositorios.EditorialRepositorio;

import jakarta.transaction.Transactional;

@Service
public class EditorialServicio {
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre){
        Editorial editorial = new Editorial();
        editorial.setName(nombre);
        editorialRepositorio.save(editorial);
    }
}
