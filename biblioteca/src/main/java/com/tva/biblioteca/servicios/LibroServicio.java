package com.tva.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tva.biblioteca.entidades.*;
import com.tva.biblioteca.excepciones.*;
import com.tva.biblioteca.repositorios.*;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LibroServicio {
    @Autowired
    private LibroRepositorio libRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, int ejemplares, String autorId, String editorialId) throws LibraryException{

        validar(titulo);
        validar(ejemplares);
        validar(autorId);
        validar(editorialId);
        
        Autor autor = autorRepositorio.findById(UUID.fromString(autorId)).get();
        Editorial editorial = editorialRepositorio.findById(UUID.fromString(editorialId)).get();
        Libro libro = new Libro();
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setEjemplares(ejemplares);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros(){
        List<Libro> lista = new ArrayList<>();
        lista =libRepositorio.findAll();
        return lista;
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo,int ejemplares, String autorId, String editorialId) throws LibraryException{
        validar(ejemplares);
        validar(editorialId);
        validar(titulo);
        validar(autorId);
        Optional<Libro> respuestaLibro = libRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(UUID.fromString(autorId));
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(UUID.fromString(editorialId));

        
        if(!respuestaLibro.isPresent()){
            throw new LibraryException("El isbn no se encuentra registrado");
        }
        if(!respuestaAutor.isPresent()){
            throw new LibraryException("No encontramos ningun autor con el identificador: " + autorId);
        }
        if(!respuestaEditorial.isPresent()){
            throw new LibraryException("No encontramos ninguna editorial con el identificador: " + editorialId);
        }
        Libro libro = respuestaLibro.get();
        Autor autor = respuestaAutor.get();
        Editorial editorial = respuestaEditorial.get();
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libRepositorio.save(libro);
         
    }

    @Transactional
    public void modificarLibro(Long isbn,String titulo){
        Optional<Libro> resp = libRepositorio.findById(isbn);
        if(resp.isPresent()){
            Libro libro = resp.get();
            libro.setTitulo(titulo);
            libRepositorio.save(libro);
        }
    }

    @Transactional
    public void modificarLibro(Long isbn,int ejemplares){
        Optional<Libro> resp = libRepositorio.findById(isbn);
        if(resp.isPresent()){
            Libro libro = resp.get();
            libro.setEjemplares(ejemplares);
            libRepositorio.save(libro);
        }
    }

    @Transactional
    public void modificarLibro(Long isbn, Autor autor){
        Optional<Libro> resp = libRepositorio.findById(isbn);
        if(resp.isPresent()){
            Libro libro = resp.get();
            libro.setAutor(autor);
            libRepositorio.save(libro);
        }
    }

    @Transactional
    public void modificarLibro(Long isbn, Editorial editorial){
        Optional<Libro> resp = libRepositorio.findById(isbn);
        if(resp.isPresent()){
            Libro libro = resp.get();
            libro.setEditorial(editorial);
            libRepositorio.save(libro);
        }
    }

    @Transactional(readOnly = true)
    public Libro findById(Long isbn) throws EntityNotFoundException{
        Libro libro = libRepositorio.getReferenceById(isbn);
        return libro;
    }

    private void validar(String nombre) throws LibraryException{
        if(nombre.isEmpty() || nombre ==null ){
            throw new LibraryException("El string no puede ser nulo o estar vacío.");
        }
    }
    private void validar(int ejemplares) throws LibraryException{
        if(ejemplares<=0 ){
            throw new LibraryException("No puede haber menos de 0 ejemplares.");
        }
    }
    


}
