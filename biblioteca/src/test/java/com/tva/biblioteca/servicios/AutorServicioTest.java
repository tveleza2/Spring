package com.tva.biblioteca.servicios;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.tva.biblioteca.repositorios.AutorRepositorio;

public class AutorServicioTest {

    @Mock
    AutorRepositorio autorRepo;

    @InjectMocks
    AutorServicio autSer;

    @ParameterizedTest()
    @ValueSource(strings = {"   ",""})
    void testInvalidCreateAutor(String name) {
        
    }

    @Test
    void testFindById() {

    }

    @Test
    void testListarAutores() {

    }

    @Test
    void testModificarAutor() {

    }
}
