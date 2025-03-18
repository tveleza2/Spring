package com.tva.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import com.tva.demo.entities.Article;
import com.tva.demo.exceptions.InconsistentAttributeException;
import com.tva.demo.repositories.ArticleRepository;


public class ArticleService {
    @Autowired
    private ArticleRepository articleRepo;

    @Transactional
    public void createArticle(String articleName) throws Exception{
        validate(name);
        Article newArticle = new Article();
        newArticle.setName(name);
        articleRepo.save(newArticle);       
    }

    @Transactional(readOnly = true)
    public List<Factory> readFactories(){
        List<Factory> list = new ArrayList<>();
        list = articleRepo.findAll();
        return list;
    }

    @Transactional
    public void updateFactory(String id, String name) throws Exception{
        validate(name);
        Optional<Factory> response = articleRepo.findById(UUID.fromString(id));
        if(response.isPresent()){
            Factory oldFactory = response.get();
            oldFactory.setFactoryName(name);
            articleRepo.save(oldFactory);
        }else{
            throw new Exception("There is no factory in the database with id: ["+id+"]");
        }

    }

    @Transactional
    public void deleteFactory(String id) throws Exception{
        Optional<Factory> response = articleRepo.findById(UUID.fromString(id));
        if(response.isPresent()){
            articleRepo.deleteById(UUID.fromString(id));;
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
