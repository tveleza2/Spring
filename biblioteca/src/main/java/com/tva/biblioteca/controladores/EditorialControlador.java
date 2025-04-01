package com.tva.biblioteca.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }

    @PostMapping("/crear")
    public ResponseEntity<Object> crearEditorial(@RequestParam("nombre") String nombre,ModelMap modelo){
        try {
            editorialServicio.crearEditorial(nombre);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LibraryException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.put("editoriales",editoriales);
        return "editorial_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo){
        try {
            Editorial editorial = editorialServicio.findById(id);
            modelo.put("editorial", editorial);
        } catch (Exception e) {
            editorialLog.log(Level.SEVERE, e.getMessage(), e);
        }
        return "editorial_modificar.html";
        
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try {
            editorialServicio.modificarEditorial(nombre, id, true);
            return "redirect:../lista";
        } catch (Exception e) {
            editorialLog.log(Level.SEVERE, e.getMessage(), e);
            return "editorial_modificar.html";
        }
        
    }

}
