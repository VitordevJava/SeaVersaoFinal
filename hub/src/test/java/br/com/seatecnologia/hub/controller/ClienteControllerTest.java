package br.com.seatecnologia.hub.controller;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.service.api.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ClienteController class.
 */
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ClienteDTO clienteDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        // Create a sample ClienteDTO for testing
        clienteDTO = new ClienteDTO(1L, "João Silva", "12345678900", null, null, null);
    }

    @Test
    public void testCriar() {
        // Arrange
        when(clienteService.criar(any(ClienteDTO.class))).thenReturn(clienteDTO);
        
        // Act
        ResponseEntity<ClienteDTO> response = clienteController.criar(new ClienteDTO(null, "João Silva", "12345678900", null, null, null));
        
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
    }

    @Test
    public void testAtualizar() {
        // Arrange
        when(clienteService.atualizar(eq(1L), any(ClienteDTO.class))).thenReturn(clienteDTO);
        
        // Act
        ResponseEntity<ClienteDTO> response = clienteController.atualizar(1L, clienteDTO);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
    }

    @Test
    public void testAtualizarNotFound() {
        // Arrange
        when(clienteService.atualizar(eq(999L), any(ClienteDTO.class))).thenThrow(new RuntimeException("Cliente não encontrado"));
        
        // Act
        ResponseEntity<ClienteDTO> response = clienteController.atualizar(999L, clienteDTO);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testListarTodos() {
        // Arrange
        List<ClienteDTO> clientes = Arrays.asList(
            clienteDTO,
            new ClienteDTO(2L, "Maria Souza", "98765432100", null, null, null)
        );
        when(clienteService.listarTodos()).thenReturn(clientes);
        
        // Act
        ResponseEntity<List<ClienteDTO>> response = clienteController.listarTodos();
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testBuscarPorId() {
        // Arrange
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(clienteDTO));
        
        // Act
        ResponseEntity<ClienteDTO> response = clienteController.buscarPorId(1L);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
    }

    @Test
    public void testBuscarPorIdNotFound() {
        // Arrange
        when(clienteService.buscarPorId(999L)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<ClienteDTO> response = clienteController.buscarPorId(999L);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeletar() {
        // Act
        ResponseEntity<Void> response = clienteController.deletar(1L);
        
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeletarNotFound() {
        // Arrange
        doThrow(new RuntimeException("Cliente não encontrado")).when(clienteService).deletar(999L);
        
        // Act
        ResponseEntity<Void> response = clienteController.deletar(999L);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}