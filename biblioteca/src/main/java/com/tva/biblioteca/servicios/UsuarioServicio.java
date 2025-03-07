package com.tva.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tva.biblioteca.entidades.Usuario;
import com.tva.biblioteca.enums.Rol;
import com.tva.biblioteca.excepciones.AuthenticationException;
import com.tva.biblioteca.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio userRepo;

    @Transactional
    public void crearUsuario(String nombre, String email, String password, String password2) throws AuthenticationException{
        validarRegistro(nombre,email,password,password2);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        userRepo.save(usuario);
    }
    
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios(){
        List<Usuario> lista = new ArrayList<>();
        lista = userRepo.findAll();
        return lista;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = userRepo.buscarPorEmail(email);
        if(user!=null){
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ user.getRol().toString());
            permisos.add(p);
            return new User(user.getEmail(),user.getPassword(),permisos);
        }else{
            return null;
        }
    }
    




    private void validarRegistro(String nombre, String email, String password, String password2) throws AuthenticationException{
        if(nombre.isEmpty() || nombre == null){
            throw new AuthenticationException("El nombre no puede estar vacío ni ser nulo");
        }
        if(email.isEmpty() || email == null){
            throw new AuthenticationException("El email no puede estar vacío ni ser nulo");
        }
        if(password.isEmpty() || password == null ||password.length()<=8){
            throw new AuthenticationException("La contraseña no puede estar vacía, ser nula ni tener menos de 8 caracteres");
        }
        if(password2.isEmpty() || password2 == null){
            throw new AuthenticationException("No se ha verificado la contraseña");
        }
        if(!password.equals(password2)){
            throw new AuthenticationException("La contraseña y su verificación no coinciden");
        }
    }

}
