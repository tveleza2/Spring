package com.tva.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.tva.biblioteca.entidades.Autor;
import com.tva.biblioteca.excepciones.LibraryException;
import com.tva.biblioteca.repositorios.AutorRepositorio;

import jakarta.persistence.EntityNotFoundException;



@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws LibraryException{
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarAutores(){
        List<Autor> lista = new ArrayList<>();
        lista = autorRepositorio.findAll();
        return lista;
    }

    @Transactional
    public void modificarAutor(String nombre, String id) throws LibraryException{     
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(UUID.fromString(id));
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
           
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
    }

    @Transactional(readOnly = true)
    public Autor findById(String id) throws EntityNotFoundException{
        Autor autor = autorRepositorio.getReferenceById(UUID.fromString(id));
        return autor;
    }

    private void validar(String nombre) throws LibraryException{
        if(nombre == null || nombre.isEmpty()){
            throw new LibraryException("El nombre no puede ser nulo o estar vacío.");
        }
    }
}
