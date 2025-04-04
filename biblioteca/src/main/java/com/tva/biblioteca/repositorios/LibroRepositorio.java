package com.tva.biblioteca.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tva.biblioteca.entidades.Libro;
import com.tva.biblioteca.modelos.LibroListarActivosDTO;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor = :aut")
    public Libro buscarPorAutor(@Param("autor") String autor);

    @Query("SELECT new com.tva.biblioteca.modelos.LibroListarActivosDTO(l.titulo,l.ejemplares,l.autor.nombre)"+"FROM Libro l WHERE l.active = true")
    public List<LibroListarActivosDTO> buscarActivos();
}
