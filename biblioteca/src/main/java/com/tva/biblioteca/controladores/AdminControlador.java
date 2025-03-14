package com.tva.biblioteca.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tva.biblioteca.servicios.UsuarioServicio;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    private Logger adminLog = Logger.getLogger(AdminControlador.class.getName());


    @Autowired
    private UsuarioServicio userService = new UsuarioServicio();
    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo){
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(ModelMap modelo){
        modelo.put("usuarios", userService.listarUsuarios());
        return "usuario_lista.html";
    }

    @PostMapping("/rol_tog/{id}")
    public String cambiarRol(@PathVariable String id, ModelMap modelo){
        // modelo.put("usuarios", userService.listarUsuarios());
        adminLog.log(Level.INFO,"Se llegó a ejecutar la petición HTTP");
        userService.toggleRole(id);
        return "redirect:../usuarios";
    }
    
}
