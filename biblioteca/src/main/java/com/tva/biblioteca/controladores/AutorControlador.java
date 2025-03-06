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

import com.tva.biblioteca.entidades.Autor;
import com.tva.biblioteca.excepciones.LibraryException;
import com.tva.biblioteca.servicios.AutorServicio;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    private Logger autorLog = Logger.getLogger(AutorControlador.class.getName());

    @Autowired
    private AutorServicio autorServicio = new AutorServicio();

    @GetMapping("/registrar")
    public String registrar(){
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre, ModelMap model){
        try {
            autorServicio.crearAutor(nombre);    // llamo a mi servicio para persistir
            model.put("exito", "El autor fue creado de forma exitosa");
        } catch (LibraryException ex) {          
            autorLog.log(Level.SEVERE, null, ex);
            model.put("error","Hubo un error creando el autor");
            return "autor_form.html";
        }        
        return "index.html";
     
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Autor> autorLista = autorServicio.listarAutores();
        modelo.put("autores",autorLista);
        return "autor_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo){
        try {
            Autor oldAutor = autorServicio.findById(id);
            modelo.put("autor", oldAutor);
        } catch (Exception e) {
            autorLog.log(Level.SEVERE, e.getMessage(), e);
        }
        return "autor_modificar.html";
        
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try {
            autorServicio.modificarAutor(nombre, id);
            return "redirect:../lista";
        } catch (Exception e) {
            autorLog.log(Level.SEVERE, e.getMessage(), e);
            return "autor_modificar.html";
        }
        
    }
}
