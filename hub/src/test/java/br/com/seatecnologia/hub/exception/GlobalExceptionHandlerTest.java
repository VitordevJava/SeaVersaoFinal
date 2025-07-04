package br.com.seatecnologia.hub.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Testes unitários para o manipulador global de exceções.
 */
public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleValidationExceptions() {
        // Arrange
        MethodArgumentNotValidException mockException = mock(MethodArgumentNotValidException.class);
        BindingResult mockBindingResult = mock(BindingResult.class);

        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("cliente", "nome", "O nome é obrigatório"));
        fieldErrors.add(new FieldError("cliente", "cpf", "O CPF deve ter 11 dígitos"));

        when(mockException.getBindingResult()).thenReturn(mockBindingResult);
        when(mockBindingResult.getAllErrors()).thenReturn(new ArrayList<>(fieldErrors));

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidationExceptions(mockException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("O nome é obrigatório", response.getBody().get("nome"));
        assertEquals("O CPF deve ter 11 dígitos", response.getBody().get("cpf"));
    }

    @Test
    public void testHandleAccessDeniedException() {
        // Arrange
        AccessDeniedException exception = new AccessDeniedException("Acesso negado");

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleAccessDeniedException(exception);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Acesso negado. Você não tem permissão para acessar este recurso.", response.getBody().get("mensagem"));
    }

    @Test
    public void testHandleGeneralExceptions() {
        // Arrange
        Exception exception = new RuntimeException("Erro interno");

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleGeneralExceptions(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ocorreu um erro interno no servidor: Erro interno", response.getBody().get("mensagem"));
    }

    @Test
    public void testHandleCPFUniqueException() {
        // Arrange
        CPFUniqueException exception = new CPFUniqueException();

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleGeneralExceptions(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ocorreu um erro interno no servidor: CPF já cadastrado no sistema.", response.getBody().get("mensagem"));
    }
}
