package com.tva.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.tva.demo.entities.*;
import com.tva.demo.exceptions.InconsistentAttributeException;
import com.tva.demo.repositories.ArticleRepository;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepo;

    @Autowired
    private FactoryService factoryService;

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);
    

    @Transactional
    public void createArticle(String articleName,String description, String factoryId) throws Exception{
        validate(articleName);
        Factory foundFactory = factoryService.findFactory(factoryId);
        Article newArticle = new Article();
        newArticle.setArticleNumber(atomicInteger.incrementAndGet());
        newArticle.setDescription(description);
        newArticle.setArticleName(articleName);
        newArticle.setFactory(foundFactory);
        newArticle.setFactory(null);
        articleRepo.save(newArticle);
    }

    @Transactional(readOnly = true)
    public List<Article> readArticles(){
        List<Article> list = new ArrayList<>();
        list = articleRepo.findAll();
        return list;
    }

    @Transactional
    public void updateArticle(String id, String articleName,String description, String factoryId) throws Exception{
        validate(articleName);
        Optional<Article> response = articleRepo.findById(UUID.fromString(id));
        if(response.isPresent()){
            Article oldArticle = response.get();
            oldArticle.setArticleName(articleName);
            oldArticle.setDescription(description);
            oldArticle.setFactory(factoryService.findFactory(factoryId));
            articleRepo.save(oldArticle);
        }else{
            throw new Exception("The article with id: "+ id +" couldn't be modified.");
        }

    }

    @Transactional
    public void deleteArticle(String id) throws Exception{
        Optional<Article> response = articleRepo.findById(UUID.fromString(id));
        if(response.isPresent()){
            articleRepo.deleteById(UUID.fromString(id));;
        }else{
            throw new Exception("There is no article in the database with id: ["+id+"]");
        }

    }

    public void validate(String name) throws InconsistentAttributeException{
        if (name.isBlank() || name.equals(null)) {
            throw new InconsistentAttributeException("The name can not be empty or null");
        }
    }

}
