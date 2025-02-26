package com.tva.biblioteca.servicios;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tva.biblioteca.entidades.Autor;
import com.tva.biblioteca.entidades.Editorial;
import com.tva.biblioteca.entidades.Libro;
import com.tva.biblioteca.repositorios.AutorRepositorio;
import com.tva.biblioteca.repositorios.EditorialRepositorio;
import com.tva.biblioteca.repositorios.LibroRepositorio;

import jakarta.transaction.Transactional;

@Service
public class LibroServicio {
    @Autowired
    private LibroRepositorio libRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, int ejemplares, String autorId, String editorialId){
        Autor autor = autorRepositorio.findById(autorId).get();
        Editorial editorial = editorialRepositorio.findById(editorialId).get();
        Libro libro = new Libro();
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setEjemplares(ejemplares);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libRepositorio.save(libro);
    }

}
