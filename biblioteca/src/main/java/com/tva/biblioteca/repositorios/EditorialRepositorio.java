package com.tva.biblioteca.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tva.biblioteca.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, UUID> {     
}
