package com.tva.demo.repositories;

import java.util.UUID;
import com.tva.demo.entities.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends JpaRepository<Article,UUID>{
}
