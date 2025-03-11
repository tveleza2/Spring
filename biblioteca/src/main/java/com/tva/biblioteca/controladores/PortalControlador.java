package com.tva.biblioteca.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tva.biblioteca.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class PortalControlador {
    private Logger portLog = Logger.getLogger(PortalControlador.class.getName());

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
    @GetMapping("/registro")
    public String registro(){
        return "registro.html";
    }


    @PostMapping("/registro")
    public String registrar(@RequestParam("nombre") String nombre, @RequestParam("email") String email, @RequestParam("password") String password,@RequestParam("password2") String password2, ModelMap modelo){
        try {
            usuarioServicio.crearUsuario(nombre, email, password, password2);
            modelo.put("exito","Se ha registrado de forma exitosa.");
            return "index.html";
        } catch (Exception e) {
            portLog.log(Level.SEVERE, e.getMessage(), e);
            modelo.put("error",e.getMessage());
            return "registro.html";
        }

        
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo ) {
           if (error != null) {
               modelo.put("error", "Usuario o Contraseña inválidos!");        }
           return "login.html";
    }
   
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo) {

        return "inicio.html";
    }
}
