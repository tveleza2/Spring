package com.tva.biblioteca.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tva.biblioteca.entidades.Autor;
import com.tva.biblioteca.repositorios.AutorRepositorio;

import jakarta.transaction.Transactional;


@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre){
        Autor autor = new Autor();
        autor.setName(nombre);
        autorRepositorio.save(autor);
    }
    
}
