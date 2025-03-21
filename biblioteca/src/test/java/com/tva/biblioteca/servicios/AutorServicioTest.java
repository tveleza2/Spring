package com.tva.biblioteca.servicios;

// import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*; // Use it for the argument matchers and other utilities


// import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension; // Use it to ensure proper mock injection


import com.tva.biblioteca.repositorios.AutorRepositorio;
import com.tva.biblioteca.entidades.Autor;

@ExtendWith(MockitoExtension.class)
public class AutorServicioTest {

    @Mock
    public AutorRepositorio autorRepo;

    @InjectMocks
    public AutorServicio autSer;

    private Autor sampleAutor;
    private String validUUID;

    @BeforeAll
    public static void initAll(){
        
        
    }

    @BeforeEach
    public void setUp(){
        validUUID = UUID.randomUUID().toString();
        sampleAutor = new Autor();
        sampleAutor.setNombre("TestName");
        sampleAutor.setId(validUUID);
    }

    @ParameterizedTest()
    @ValueSource(strings = {"   ",""})
    void testInvalidCreateAutor(String name) {
        assertThrows(Exception.class,()->autSer.crearAutor(name));
    }

    @ParameterizedTest
    @ValueSource(strings={"1234","abcd","1a2b","*****"})
    public void testValidCreateAutor(String name) throws Exception{
        autSer.crearAutor(name);
        verify(autorRepo,times(1)).save(any(Autor.class));
    }

    @Test
    void testListarAutores() {
        // Arrange
        List<Autor> autorList = Arrays.asList(returnAutor("RRMartin"),returnAutor("RRTolkien"));
        when(autorRepo.findAll()).thenReturn(autorList);
        // Act
        List<Autor> observedList = autSer.listarAutores();
        // Assert
        assertNotNull(observedList);
        assertEquals(autorList,observedList);
    }



    @Test
    void testFindById() {
        // Arrange
        when(autorRepo.getReferenceById(UUID.fromString(validUUID))).thenReturn(sampleAutor);
        // Act
        Autor observedAutor = autSer.findById(validUUID);
        // Assert
        assertEquals(sampleAutor, observedAutor);
    }

   

    @Test
    void testModificarAutor() {

    }


    public static Autor returnAutor(String nombre){
        Autor autor = new Autor();
        autor.setNombre(nombre);
        return autor;
    }
}
