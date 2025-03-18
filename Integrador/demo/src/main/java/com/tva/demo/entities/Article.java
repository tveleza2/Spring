package com.tva.demo.entities;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID articleId;
    private String articleName;
    private String description;
    private Integer articleNumber;

    @ManyToOne
    private Factory factory;


}
