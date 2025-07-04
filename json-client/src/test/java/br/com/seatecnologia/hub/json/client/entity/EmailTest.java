package br.com.seatecnologia.hub.json.client.entity;

import br.com.seatecnologia.hub.core.dto.EmailDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Email.
 */
public class EmailTest {

    @Test
    public void testFromDTO() {
        // Teste com DTO válido
        EmailDTO dto = new EmailDTO("teste@example.com");
        Email entity = Email.fromDTO(dto);
        
        assertNotNull(entity, "A entidade não deve ser nula");
        assertEquals("teste@example.com", entity.getEmail(), "O email deve ser igual ao do DTO");
        
        // Teste com DTO nulo
        assertNull(Email.fromDTO(null), "Deve retornar null quando o DTO for nulo");
    }
    
    @Test
    public void testToDTO() {
        // Teste com entidade válida
        Email entity = new Email();
        entity.setEmail("teste@example.com");
        
        EmailDTO dto = entity.toDTO();
        
        assertNotNull(dto, "O DTO não deve ser nulo");
        assertEquals("teste@example.com", dto.email(), "O email no DTO deve ser igual ao da entidade");
    }
}