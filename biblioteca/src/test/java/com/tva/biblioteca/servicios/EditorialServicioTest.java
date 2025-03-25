package com.tva.biblioteca.servicios;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tva.biblioteca.entidades.Editorial;
import com.tva.biblioteca.repositorios.EditorialRepositorio;

@ExtendWith(MockitoExtension.class)
public class EditorialServicioTest{
    @Mock
    public EditorialRepositorio editorialRepositorio;

    @InjectMocks
    public EditorialServicio editorialServicio;

    public UUID validUUID;
    public Editorial sampleEditorial;
    
    @BeforeEach
    void setUp(){
        validUUID = UUID.randomUUID();
        sampleEditorial = new Editorial();
        sampleEditorial.setId(validUUID.toString());
        sampleEditorial.setNombre("Test Editorial");
    }

    @Test
    void testCrearEditorial() {

    }

    @Test
    void testListarEditoriales() {

    }

    @Test
    void testModificarEditorial() {

    }
}
