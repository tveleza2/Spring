package com.tva.biblioteca.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tva.biblioteca.entidades.Editorial;
import com.tva.biblioteca.excepciones.LibraryException;
import com.tva.biblioteca.servicios.EditorialServicio;

@RestController
@RequestMapping("/editorial")
public class EditorialControlador {
    private Logger editorialLog = Logger.getLogger(EditorialControlador.class.getName());

    @Autowired
    EditorialServicio editorialServicio = new EditorialServicio();


    @PostMapping("/crear")
    public ResponseEntity<Object> crearEditorial(@RequestParam("nombre") String nombre,ModelMap modelo){
        try {
            editorialServicio.crearEditorial(nombre);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LibraryException e) {
            editorialLog.log(Level.SEVERE, "Error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/listar")
    public ResponseEntity<Object> listarEditorial(){
        try {
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            return new ResponseEntity<Object>(editoriales, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar_activas")
    public ResponseEntity<Object> listarEditorialesActivas(){
        try {
            List<Editorial> editoriales = editorialServicio.listarEditorialesActivas();
            return new ResponseEntity<Object>(editoriales, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/modificar")
    public ResponseEntity<Object> modificarEditorial(@RequestParam String nombre, @RequestParam String id){
        try {
            editorialServicio.modificarEditorial(nombre, id, true);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
