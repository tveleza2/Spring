package com.tva.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// import java.util.logging.Level;
// import java.util.logging.Logger;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tva.biblioteca.entidades.*;
import com.tva.biblioteca.excepciones.*;
import com.tva.biblioteca.modelos.LibroCreateDTO;
import com.tva.biblioteca.modelos.LibroListarActivosDTO;
import com.tva.biblioteca.repositorios.LibroRepositorio;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroServicio {
    // private final Logger libroLogger = Logger.getLogger(LibroServicio.class.getName());

    private final LibroRepositorio libroRepositorio;

    private final AutorServicio autorServicio;

    private final EditorialServicio editorialServicio;

    @Transactional
    public void crearLibro(LibroCreateDTO libroCreateDTO) throws LibraryException{
        Long isbn = libroCreateDTO.getIsbn();
        String titulo = libroCreateDTO.getTitulo();
        int ejemplares = libroCreateDTO.getEjemplares();
        String autorId = libroCreateDTO.getIdAutor();
        String editorialId = libroCreateDTO.getIdEditorial();
        Boolean active = libroCreateDTO.isActive();
        validar(titulo);
        validar(ejemplares);
        validar(autorId);
        validar(editorialId);
        
        Autor autor = autorServicio.findById(autorId);
        Editorial editorial = editorialServicio.findById(editorialId);
        Libro libro = new Libro();
        libro.setActive(active);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setEjemplares(ejemplares);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros(){
        List<Libro> lista = new ArrayList<>();
        lista =libroRepositorio.findAll();
        return lista;
    }

    @Transactional(readOnly = true)
    public List<LibroListarActivosDTO> listarLibrosActivos(){
        List<LibroListarActivosDTO> lista = libroRepositorio.buscarActivos();
        return lista;
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo,int ejemplares, String autorId, String editorialId, boolean active) throws LibraryException{
        validar(ejemplares);
        validar(editorialId);
        validar(titulo);
        validar(autorId);
        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Autor autor = autorServicio.findById(autorId);
        Editorial editorial = editorialServicio.findById(editorialId);

        
        if(!respuestaLibro.isPresent()){
            throw new LibraryException("El isbn no se encuentra registrado");
        }
        
        Libro libro = respuestaLibro.get();
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setActive(active);
        libroRepositorio.save(libro);
         
    }

    @Transactional
    public void modificarLibro(Long isbn,String titulo){
        Optional<Libro> resp = libroRepositorio.findById(isbn);
        if(resp.isPresent()){
            Libro libro = resp.get();
            libro.setTitulo(titulo);
            libroRepositorio.save(libro);
        }
    }

    @Transactional
    public void modificarLibro(Long isbn,int ejemplares){
        Optional<Libro> resp = libroRepositorio.findById(isbn);
        if(resp.isPresent()){
            Libro libro = resp.get();
            libro.setEjemplares(ejemplares);
            libroRepositorio.save(libro);
        }
    }

    @Transactional
    public void modificarLibro(Long isbn, Autor autor){
        Optional<Libro> resp = libroRepositorio.findById(isbn);
        if(resp.isPresent()){
            Libro libro = resp.get();
            libro.setAutor(autor);
            libroRepositorio.save(libro);
        }
    }

    @Transactional
    public void modificarLibro(Long isbn, Editorial editorial){
        Optional<Libro> resp = libroRepositorio.findById(isbn);
        if(resp.isPresent()){
            Libro libro = resp.get();
            libro.setEditorial(editorial);
            libroRepositorio.save(libro);
        }
    }

    @Transactional
    public void eliminarLibro(Long id)throws LibraryException{
        Optional<Libro> possibleLibro = libroRepositorio.findById(id);
        if(possibleLibro.isPresent()){
            Libro libro = possibleLibro.get();
            libro.setActive(false);
            libroRepositorio.save(libro);
        }
    }

    @Transactional(readOnly = true)
    public Libro findById(Long isbn) throws EntityNotFoundException{
        Libro libro = libroRepositorio.getReferenceById(isbn);
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
