package br.com.seatecnologia.hub.json.client.entity;

import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Endereco.
 */
public class EnderecoTest {

    @Test
    public void testFromDTO() {
        // Teste com DTO válido completo
        EnderecoDTO dto = new EnderecoDTO(
            "12345678",
            "Rua Exemplo",
            "Bairro Teste",
            "Cidade Teste",
            "UF",
            "Complemento Teste"
        );
        
        Endereco entity = Endereco.fromDTO(dto);
        
        assertNotNull(entity, "A entidade não deve ser nula");
        assertEquals("12345678", entity.getCep(), "O CEP deve ser igual ao do DTO");
        assertEquals("Rua Exemplo", entity.getLogradouro(), "O logradouro deve ser igual ao do DTO");
        assertEquals("Bairro Teste", entity.getBairro(), "O bairro deve ser igual ao do DTO");
        assertEquals("Cidade Teste", entity.getCidade(), "A cidade deve ser igual à do DTO");
        assertEquals("UF", entity.getUf(), "A UF deve ser igual à do DTO");
        assertEquals("Complemento Teste", entity.getComplemento(), "O complemento deve ser igual ao do DTO");
        
        // Teste com DTO sem complemento (campo opcional)
        EnderecoDTO dtoSemComplemento = new EnderecoDTO(
            "12345678",
            "Rua Exemplo",
            "Bairro Teste",
            "Cidade Teste",
            "UF",
            null
        );
        
        Endereco entitySemComplemento = Endereco.fromDTO(dtoSemComplemento);
        
        assertNotNull(entitySemComplemento, "A entidade não deve ser nula");
        assertNull(entitySemComplemento.getComplemento(), "O complemento deve ser nulo");
        
        // Teste com DTO nulo
        assertNull(Endereco.fromDTO(null), "Deve retornar null quando o DTO for nulo");
    }
    
    @Test
    public void testToDTO() {
        // Teste com entidade válida completa
        Endereco entity = new Endereco();
        entity.setCep("12345678");
        entity.setLogradouro("Rua Exemplo");
        entity.setBairro("Bairro Teste");
        entity.setCidade("Cidade Teste");
        entity.setUf("UF");
        entity.setComplemento("Complemento Teste");
        
        EnderecoDTO dto = entity.toDTO();
        
        assertNotNull(dto, "O DTO não deve ser nulo");
        assertEquals("12345678", dto.cep(), "O CEP no DTO deve ser igual ao da entidade");
        assertEquals("Rua Exemplo", dto.logradouro(), "O logradouro no DTO deve ser igual ao da entidade");
        assertEquals("Bairro Teste", dto.bairro(), "O bairro no DTO deve ser igual ao da entidade");
        assertEquals("Cidade Teste", dto.cidade(), "A cidade no DTO deve ser igual à da entidade");
        assertEquals("UF", dto.uf(), "A UF no DTO deve ser igual à da entidade");
        assertEquals("Complemento Teste", dto.complemento(), "O complemento no DTO deve ser igual ao da entidade");
        
        // Teste com entidade sem complemento
        Endereco entitySemComplemento = new Endereco();
        entitySemComplemento.setCep("12345678");
        entitySemComplemento.setLogradouro("Rua Exemplo");
        entitySemComplemento.setBairro("Bairro Teste");
        entitySemComplemento.setCidade("Cidade Teste");
        entitySemComplemento.setUf("UF");
        entitySemComplemento.setComplemento(null);
        
        EnderecoDTO dtoSemComplemento = entitySemComplemento.toDTO();
        
        assertNotNull(dtoSemComplemento, "O DTO não deve ser nulo");
        assertNull(dtoSemComplemento.complemento(), "O complemento no DTO deve ser nulo");
    }
}