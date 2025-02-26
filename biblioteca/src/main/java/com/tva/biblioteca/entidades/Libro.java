package com.tva.biblioteca.entidades;
import java.util.Date;
import jakarta.persistence.*;


@Entity
public class Libro {

    @Id
    private Long isbn;

    private String titulo;

    private int ejemplares;

    @Temporal(TemporalType.DATE)
    private Date alta;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    private Editorial editorial;

    public Libro(){}

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
    public void setEjemplares(int ejemplares) {
        this.ejemplares = ejemplares;
    }
    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Date getAlta() {
        return alta;
    }
    public Autor getAutor() {
        return autor;
    }
    public Editorial getEditorial() {
        return editorial;
    }
    public int getEjemplares() {
        return ejemplares;
    }
    public long getIsbn() {
        return isbn;
    }
    public String getTitulo() {
        return titulo;
    }
    
}
