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
public class Factory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID factoryId;
    private String factoryName;
    
}
