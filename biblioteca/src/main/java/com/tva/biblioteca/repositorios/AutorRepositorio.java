package com.tva.biblioteca.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tva.biblioteca.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor,UUID> {
}