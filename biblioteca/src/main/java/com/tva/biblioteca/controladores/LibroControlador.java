package com.tva.biblioteca.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tva.biblioteca.excepciones.LibraryException;
import com.tva.biblioteca.modelos.LibroCreateDTO;
import com.tva.biblioteca.modelos.LibroListarActivosDTO;
import com.tva.biblioteca.servicios.*;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/libro")
public class LibroControlador {
    private Logger libroLog = Logger.getLogger(LibroControlador.class.getName());


    private final LibroServicio libroServicio;

    @GetMapping("/listar")
    public ResponseEntity<Object> listarLibrosActivos(){
        try {
            libroLog.log(Level.INFO, "Se accedió a listar");
            List<LibroListarActivosDTO> librosActivos = libroServicio.listarLibrosActivos();
            libroLog.log(Level.INFO, "La lista recopilada fue: "+librosActivos.toString());
            return ResponseEntity.status(HttpStatus.OK).body(librosActivos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Hubo un problema al consultar la base de datos\"}");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<Object> crearLibro(@RequestBody(required = true) LibroCreateDTO libroCreateDTO){
        try {
            libroServicio.crearLibro(libroCreateDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LibraryException e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Algun dato no es correcto o es nulo, revisar.\"}");
        }
    }

   


}
