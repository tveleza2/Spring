package com.tva.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.tva.demo.entities.*;
import com.tva.demo.enums.*;
import com.tva.demo.exceptions.*;
import com.tva.demo.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    

    @Transactional
    public void createUser(String userName, String surename, String email, String password, String password2, Rol role) throws Exception{
        validateRecord(userName, surename, email, password, password2);
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setSurename(surename);
        newUser.setRol(Rol.USER);
        userRepo.save(newUser);
    }

    @Transactional(readOnly = true)
    public List<User> readUsers(){
        List<User> list = new ArrayList<>();
        list = userRepo.findAll();
        return list;
    }

    @Transactional
    public void updateUser(String id, String userName, String surename, String email, String password, String password2, Rol role) throws Exception{
        validateRecord(userName, surename, email, password, password2);
        Optional<User> response = userRepo.findById(UUID.fromString(id));
        if(response.isPresent()){
            User oldUser = response.get();
            oldUser.setUserName(userName);
            oldUser.setEmail(email);
            oldUser.setPassword(password);
            oldUser.setSurename(surename);
            oldUser.setRol(Rol.USER);
            userRepo.save(oldUser);
        }else{
            throw new Exception("The User with id: "+ id +" couldn't be modified.");
        }

    }

    @Transactional
    public void deleteUser(String id) throws Exception{
        Optional<User> response = userRepo.findById(UUID.fromString(id));
        if(response.isPresent()){
            userRepo.deleteById(UUID.fromString(id));;
        }else{
            throw new Exception("There is no user in the database with id: ["+id+"]");
        }

    }

    private void validateRecord(String name, String surename, String email, String password, String password2) throws InconsistentAttributeException{
        if(name.isEmpty() || name == null){
            throw new InconsistentAttributeException("Name attribute can not be empty or be null.");
        }
        if(surename.isEmpty() || surename == null){
            throw new InconsistentAttributeException("Surename attribute can not be empty or be null.");
        }
        if(email.isEmpty() || email == null){
            throw new InconsistentAttributeException("E-mail attribute can not be empty or be null.");
        }
        if(password.isEmpty() || password == null ||password.length()<=5){
            throw new InconsistentAttributeException("Password can not be empty, null or have less than 5 characters");
        }
        if(password2.isEmpty() || password2 == null){
            throw new InconsistentAttributeException("Password has not been verified");
        }
        if(!password.equals(password2)){
            throw new InconsistentAttributeException("Both passwords are different.");
        }
        List<User> lista = readUsers();
        for (User user : lista) {
            if(user.getEmail().equals(email)){
                throw new InconsistentAttributeException("There is already a user registered with the email: "+email);
            }
        }
    }
}
