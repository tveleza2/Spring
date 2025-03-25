package com.tva.biblioteca.servicios;

// import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*; // Use it for the argument matchers and other utilities


// import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension; // Use it to ensure proper mock injection


import com.tva.biblioteca.repositorios.AutorRepositorio;
import com.tva.biblioteca.entidades.Autor;
import com.tva.biblioteca.excepciones.LibraryException;

@ExtendWith(MockitoExtension.class)
public class AutorServicioTest {

    @Mock
    public AutorRepositorio autorRepositorio;

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
        verify(autorRepositorio,times(1)).save(any(Autor.class));
    }

    @Test
    void testListarAutores() throws LibraryException{
        // Arrange
        List<Autor> autorList = Arrays.asList(autSer.returnAutor("RRMartin"),autSer.returnAutor("RRTolkien"));
        when(autorRepositorio.findAll()).thenReturn(autorList);
        // Act
        List<Autor> observedList = autSer.listarAutores();
        // Assert
        assertNotNull(observedList);
        assertEquals(autorList,observedList);
    }

   

    @Test
    void testModificarAutorValid() throws Exception{
        // Arrange
        when(autorRepositorio.findById(UUID.fromString(validUUID))).thenReturn(Optional.of(sampleAutor));
        String expectedName = "Modified Autor";
        // Act
        autSer.modificarAutor(expectedName, validUUID);
        // Assert
        assertEquals(expectedName, sampleAutor.getNombre());
        verify(autorRepositorio,times(1)).save(any(Autor.class));
        verify(autorRepositorio,times(1)).findById(UUID.fromString(validUUID));
    }


    @ParameterizedTest
    @CsvSource({"d98880b7-9734-4608-9b86-abaee2d31824,    ","1234,validName"})
    void testModificarAutorInvalid(String id, String name) throws LibraryException{
        // Assert
        assertThrows(Exception.class, ()->autSer.modificarAutor(name, id));
    }

    @ParameterizedTest
    @ValueSource(strings={"Pepito","*123Juan",".*!&"})
    void returnAutor(String name) throws LibraryException{
        // Arrange and Act
        Autor outputAutor = autSer.returnAutor(name);
        // Assert
        assertEquals(name, outputAutor.getNombre());
        assertInstanceOf(Autor.class, outputAutor);
    }
}
