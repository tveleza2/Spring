package com.tva.biblioteca.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tva.biblioteca.excepciones.LibraryException;
import com.tva.biblioteca.servicios.AutorServicio;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    @Autowired
    private AutorServicio autorServicio = new AutorServicio();

    @GetMapping("/registrar")
    public String registrar(){
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre){
        try {
            autorServicio.crearAutor(nombre);    // llamo a mi servicio para persistir
        
        } catch (LibraryException ex) {          
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "autor_form.html";
        }        
        return "index.html";
     
    }
}
