package com.tva.biblioteca.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tva.biblioteca.entidades.Autor;
import com.tva.biblioteca.excepciones.LibraryException;
import com.tva.biblioteca.servicios.AutorServicio;

@RestController
@RequestMapping("/autor")
public class AutorControlador {
    private Logger autorLog = Logger.getLogger(AutorControlador.class.getName());

    @Autowired
    private AutorServicio autorServicio = new AutorServicio();


    @PostMapping("/crear")
    public ResponseEntity<Object> crearAutor(@RequestParam("nombre") String nombre){
        try {
            autorServicio.crearAutor(nombre);    // llamo a mi servicio para persistir
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LibraryException ex) {
            autorLog.log(Level.SEVERE, "Error");          
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<Object> listarAutores(){
        try {
            List<Autor> autores = autorServicio.listarAutores();
            return new ResponseEntity<Object>(autores, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
