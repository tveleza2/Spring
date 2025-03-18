package com.tva.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tva.demo.entities.Factory;
import com.tva.demo.exceptions.InconsistentAttributeException;
import com.tva.demo.repositories.FactoryRepository;

@ExtendWith(MockitoExtension.class)
public class FactoryServiceTest {

    @Mock
    private FactoryRepository factoryRepo;
    
    @InjectMocks
    private FactoryService factoryService;
    
    private UUID validUuid;
    private Factory sampleFactory;
    
    @BeforeEach
    public void setUp() {
        validUuid = UUID.randomUUID();
        sampleFactory = new Factory();
        sampleFactory.setFactoryName("Test Factory");
    }
    
    @Test
    @DisplayName("Successfull factory creation")
    public void testCreateFactory_Success() throws Exception {
        // Arrange
        String factoryName = "New Factory";
        
        // Act
        factoryService.createFactory(factoryName);
        
        // Assert
        verify(factoryRepo, times(1)).save(any(Factory.class));
    }
    
    @Test
    @DisplayName("Empty name factory creation")
    public void testCreateFactory_EmptyName() {
        // Arrange
        String emptyName = "";
        
        // Act & Assert
        InconsistentAttributeException exception = assertThrows(
            InconsistentAttributeException.class,
            () -> factoryService.createFactory(emptyName)
        );
        assertEquals("The name can not be empty or null", exception.getMessage());
        verify(factoryRepo, never()).save(any(Factory.class));
    }
    
    @Test
    @DisplayName("Blank name factory creation")
    public void testCreateFactory_BlankName() {
        // Arrange
        String blankName = "   ";
        
        // Act & Assert
        InconsistentAttributeException exception = assertThrows(
            InconsistentAttributeException.class,
            () -> factoryService.createFactory(blankName)
        );
        assertEquals("The name can not be empty or null", exception.getMessage());
        verify(factoryRepo, never()).save(any(Factory.class));
    }
    
    @Test
    @DisplayName("Fnding all factories")
    public void testReadFactories() {
        // Arrange
        List<Factory> expectedFactories = Arrays.asList(
            createFactory("Factory 1"),
            createFactory("Factory 2")
        );
        when(factoryRepo.findAll()).thenReturn(expectedFactories);
        
        // Act
        List<Factory> result = factoryService.readFactories();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Factory 1", result.get(0).getFactoryName());
        assertEquals("Factory 2", result.get(1).getFactoryName());
        verify(factoryRepo, times(1)).findAll();
    }
    
    @Test
    public void testFindFactory_Success() throws Exception {
        // Arrange
        String uuidString = validUuid.toString();
        when(factoryRepo.findById(validUuid)).thenReturn(Optional.of(sampleFactory));
        
        // Act
        Factory result = factoryService.findFactory(uuidString);
        
        // Assert
        assertNotNull(result);
        assertEquals("Test Factory", result.getFactoryName());
        verify(factoryRepo, times(1)).findById(validUuid);
    }
    
    @Test
    public void testFindFactory_NotFound() {
        // Arrange
        String uuidString = validUuid.toString();
        when(factoryRepo.findById(validUuid)).thenReturn(Optional.empty());
        
        // Act & Assert
        Exception exception = assertThrows(
            Exception.class,
            () -> factoryService.findFactory(uuidString)
        );
        assertEquals("No factory with id: " + uuidString, exception.getMessage());
        verify(factoryRepo, times(1)).findById(validUuid);
    }
    
    @Test
    public void testFindFactory_InvalidUUID() {
        // Arrange
        String invalidUuid = "not-a-uuid";
        
        // Act & Assert
        assertThrows(
            Exception.class,
            () -> factoryService.findFactory(invalidUuid)
        );
        verify(factoryRepo, never()).findById(any());
    }
    
    @Test
    public void testUpdateFactory_Success() throws Exception {
        // Arrange
        String uuidString = validUuid.toString();
        String newName = "Updated Factory";
        when(factoryRepo.findById(validUuid)).thenReturn(Optional.of(sampleFactory));
        
        // Act
        factoryService.updateFactory(uuidString, newName);
        
        // Assert
        assertEquals("Updated Factory", sampleFactory.getFactoryName());
        verify(factoryRepo, times(1)).findById(validUuid);
        verify(factoryRepo, times(1)).save(sampleFactory);
    }
    
    @Test
    public void testUpdateFactory_NotFound() {
        // Arrange
        String uuidString = validUuid.toString();
        String newName = "Updated Factory";
        when(factoryRepo.findById(validUuid)).thenReturn(Optional.empty());
        
        // Act & Assert
        Exception exception = assertThrows(
            Exception.class,
            () -> factoryService.updateFactory(uuidString, newName)
        );
        assertEquals("There is no factory in the database with id: [" + uuidString + "]", exception.getMessage());
        verify(factoryRepo, times(1)).findById(validUuid);
        verify(factoryRepo, never()).save(any());
    }
    
    @Test
    public void testUpdateFactory_EmptyName() {
        // Arrange
        String uuidString = validUuid.toString();
        String emptyName = "";
        
        // Act & Assert
        InconsistentAttributeException exception = assertThrows(
            InconsistentAttributeException.class,
            () -> factoryService.updateFactory(uuidString, emptyName)
        );
        assertEquals("The name can not be empty or null", exception.getMessage());
        verify(factoryRepo, never()).findById(any());
        verify(factoryRepo, never()).save(any());
    }
    
    @Test
    public void testUpdateFactory_InvalidUUID() {
        // Arrange
        String invalidUuid = "not-a-uuid";
        String newName = "Updated Factory";
        
        // Act & Assert
        assertThrows(
            Exception.class,
            () -> factoryService.updateFactory(invalidUuid, newName)
        );
        verify(factoryRepo, never()).findById(any());
        verify(factoryRepo, never()).save(any());
    }
    
    @Test
    public void testDeleteFactory_Success() throws Exception {
        // Arrange
        String uuidString = validUuid.toString();
        when(factoryRepo.findById(validUuid)).thenReturn(Optional.of(sampleFactory));
        
        // Act
        factoryService.deleteFactory(uuidString);
        
        // Assert
        verify(factoryRepo, times(1)).findById(validUuid);
        verify(factoryRepo, times(1)).deleteById(validUuid);
    }
    
    @Test
    public void testDeleteFactory_NotFound() {
        // Arrange
        String uuidString = validUuid.toString();
        when(factoryRepo.findById(validUuid)).thenReturn(Optional.empty());
        
        // Act & Assert
        Exception exception = assertThrows(
            Exception.class,
            () -> factoryService.deleteFactory(uuidString)
        );
        assertEquals("There is no factory in the database with id: [" + uuidString + "]", exception.getMessage());
        verify(factoryRepo, times(1)).findById(validUuid);
        verify(factoryRepo, never()).deleteById(any());
    }
    
    @Test
    public void testDeleteFactory_InvalidUUID() {
        // Arrange
        String invalidUuid = "not-a-uuid";
        
        // Act & Assert
        assertThrows(
            Exception.class,
            () -> factoryService.deleteFactory(invalidUuid)
        );
        verify(factoryRepo, never()).findById(any());
        verify(factoryRepo, never()).deleteById(any());
    }
    
    @Test
    public void testValidate_ValidName() throws InconsistentAttributeException {
        // Act
        factoryService.validate("Valid Name");
        
        // No exception should be thrown
    }
    
    @Test
    public void testValidate_EmptyName() {
        // Act & Assert
        InconsistentAttributeException exception = assertThrows(
            InconsistentAttributeException.class,
            () -> factoryService.validate("")
        );
        assertEquals("The name can not be empty or null", exception.getMessage());
    }
    
    @Test
    public void testValidate_BlankName() {
        // Act & Assert
        InconsistentAttributeException exception = assertThrows(
            InconsistentAttributeException.class,
            () -> factoryService.validate("   ")
        );
        assertEquals("The name can not be empty or null", exception.getMessage());
    }
    
    // Helper method to create factory instances
    private Factory createFactory(String name) {
        Factory factory = new Factory();
        factory.setFactoryName(name);
        return factory;
    }
}