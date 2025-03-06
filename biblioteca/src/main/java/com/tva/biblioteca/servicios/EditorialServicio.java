package com.tva.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.tva.biblioteca.entidades.Editorial;
import com.tva.biblioteca.excepciones.LibraryException;
import com.tva.biblioteca.repositorios.EditorialRepositorio;


@Service
public class EditorialServicio {
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws LibraryException{
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarLibros(){
        List<Editorial> lista = new ArrayList<>();
        lista = editorialRepositorio.findAll();
        return lista;
    }

    @Transactional
    public void modificarEditorial(String nombre, String id) throws LibraryException{
        validar(nombre);
        Optional<Editorial> resp = editorialRepositorio.findById(id);
        if(resp.isPresent()){
            Editorial editorial = resp.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }
    }


    private void validar(String nombre) throws LibraryException{
        if(nombre.isEmpty() || nombre ==null ){
            throw new LibraryException("El nombre no puede ser nulo o estar vacío.");
        }
    }


}
