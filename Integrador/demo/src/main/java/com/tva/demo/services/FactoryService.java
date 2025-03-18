package com.tva.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.tva.demo.entities.Factory;
import com.tva.demo.exceptions.InconsistentAttributeException;
import com.tva.demo.repositories.FactoryRepository;

@Service
public class FactoryService {
    @Autowired
    private FactoryRepository factoryRepo;

    @Transactional
    public void createFactory(String name) throws Exception{
        validate(name);
        Factory newFactory = new Factory();
        newFactory.setFactoryName(name);
        factoryRepo.save(newFactory);       
    }

    @Transactional(readOnly = true)
    public List<Factory> readFactories(){
        List<Factory> list = new ArrayList<>();
        list = factoryRepo.findAll();
        return list;
    }

    @Transactional(readOnly = true)
    public Factory findFactory(String id) throws Exception{
        Optional<Factory> response = factoryRepo.findById(UUID.fromString(id));
        if(!response.isPresent()){
            throw new Exception("No factory with id: "+ id);
        }
        return response.get();
    }

    @Transactional
    public void updateFactory(String id, String name) throws Exception{
        validate(name);
        Optional<Factory> response = factoryRepo.findById(UUID.fromString(id));
        if(response.isPresent()){
            Factory oldFactory = response.get();
            oldFactory.setFactoryName(name);
            factoryRepo.save(oldFactory);
        }else{
            throw new Exception("There is no factory in the database with id: ["+id+"]");
        }
    }



    @Transactional
    public void deleteFactory(String id) throws Exception{
        Optional<Factory> response = factoryRepo.findById(UUID.fromString(id));
        if(response.isPresent()){
            factoryRepo.deleteById(UUID.fromString(id));;
        }else{
            throw new Exception("There is no factory in the database with id: ["+id+"]");
        }
    }

    public void validate(String name) throws InconsistentAttributeException{
        if (name.isBlank() || name.equals(null)) {
            throw new InconsistentAttributeException("The name can not be empty or null");
        }
    }
}
