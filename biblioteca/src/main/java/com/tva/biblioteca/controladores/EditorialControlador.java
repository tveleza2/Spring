package com.tva.biblioteca.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tva.biblioteca.entidades.Editorial;
import com.tva.biblioteca.excepciones.LibraryException;
import com.tva.biblioteca.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    private Logger editorialLog = Logger.getLogger(EditorialControlador.class.getName());

    @Autowired
    EditorialServicio editorialServicio = new EditorialServicio();
    
    @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre,ModelMap modelo){
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La editorial se ha guardado de forma exitosa");
        } catch (LibraryException e) {
            editorialLog.log(Level.SEVERE, e.getMessage(), e);
            System.out.println(editorialServicio.listarEditoriales().get(0).toString());
            modelo.put("error", "No se pudo guardar la editorial");
            return "editorial_form.html";
        }
        return "index.html";
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
            editorialServicio.modificarEditorial(nombre, id);
            return "redirect:../lista";
        } catch (Exception e) {
            editorialLog.log(Level.SEVERE, e.getMessage(), e);
            return "editorial_modificar.html";
        }
        
    }

}
