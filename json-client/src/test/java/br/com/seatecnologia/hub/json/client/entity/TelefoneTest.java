package br.com.seatecnologia.hub.json.client.entity;

import br.com.seatecnologia.hub.core.dto.TelefoneDTO;
import br.com.seatecnologia.hub.core.dto.TipoTelefone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Telefone.
 */
public class TelefoneTest {

    @Test
    public void testFromDTO() {
        // Teste com DTO válido com tipo
        TelefoneDTO dto = new TelefoneDTO(TipoTelefone.CELULAR, "11987654321");
        Telefone entity = Telefone.fromDTO(dto);
        
        assertNotNull(entity, "A entidade não deve ser nula");
        assertEquals(TipoTelefone.CELULAR, entity.getTipo(), "O tipo deve ser igual ao do DTO");
        assertEquals("11987654321", entity.getNumero(), "O número deve ser igual ao do DTO");
        
        // Teste com DTO válido sem tipo (campo opcional)
        TelefoneDTO dtoSemTipo = new TelefoneDTO(null, "1123456789");
        Telefone entitySemTipo = Telefone.fromDTO(dtoSemTipo);
        
        assertNotNull(entitySemTipo, "A entidade não deve ser nula");
        assertNull(entitySemTipo.getTipo(), "O tipo deve ser nulo");
        assertEquals("1123456789", entitySemTipo.getNumero(), "O número deve ser igual ao do DTO");
        
        // Teste com DTO nulo
        assertNull(Telefone.fromDTO(null), "Deve retornar null quando o DTO for nulo");
    }
    
    @Test
    public void testToDTO() {
        // Teste com entidade válida com tipo
        Telefone entity = new Telefone();
        entity.setTipo(TipoTelefone.CELULAR);
        entity.setNumero("11987654321");
        
        TelefoneDTO dto = entity.toDTO();
        
        assertNotNull(dto, "O DTO não deve ser nulo");
        assertEquals(TipoTelefone.CELULAR, dto.tipo(), "O tipo no DTO deve ser igual ao da entidade");
        assertEquals("11987654321", dto.numero(), "O número no DTO deve ser igual ao da entidade");
        
        // Teste com entidade válida sem tipo
        Telefone entitySemTipo = new Telefone();
        entitySemTipo.setTipo(null);
        entitySemTipo.setNumero("1123456789");
        
        TelefoneDTO dtoSemTipo = entitySemTipo.toDTO();
        
        assertNotNull(dtoSemTipo, "O DTO não deve ser nulo");
        assertNull(dtoSemTipo.tipo(), "O tipo no DTO deve ser nulo");
        assertEquals("1123456789", dtoSemTipo.numero(), "O número no DTO deve ser igual ao da entidade");
    }
}