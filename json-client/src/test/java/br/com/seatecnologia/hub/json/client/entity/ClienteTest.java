package br.com.seatecnologia.hub.json.client.entity;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.core.dto.EmailDTO;
import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import br.com.seatecnologia.hub.core.dto.TelefoneDTO;
import br.com.seatecnologia.hub.core.dto.TipoTelefone;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Cliente.
 */
public class ClienteTest {

    @Test
    public void testFromDTO() {
        // Preparar dados de teste
        EnderecoDTO enderecoDTO = new EnderecoDTO(
            "12345678",
            "Rua Exemplo",
            "Bairro Teste",
            "Cidade Teste",
            "UF",
            "Complemento Teste"
        );
        
        List<TelefoneDTO> telefonesDTO = Arrays.asList(
            new TelefoneDTO(TipoTelefone.CELULAR, "11987654321"),
            new TelefoneDTO(TipoTelefone.RESIDENCIAL, "1123456789")
        );
        
        List<EmailDTO> emailsDTO = Arrays.asList(
            new EmailDTO("teste1@example.com"),
            new EmailDTO("teste2@example.com")
        );
        
        // Teste com DTO válido completo
        ClienteDTO dto = new ClienteDTO(
            1L,
            "João Silva",
            "12345678900",
            enderecoDTO,
            telefonesDTO,
            emailsDTO
        );
        
        Cliente entity = Cliente.fromDTO(dto);
        
        // Verificar conversão do cliente
        assertNotNull(entity, "A entidade não deve ser nula");
        assertEquals(1L, entity.getId(), "O ID deve ser igual ao do DTO");
        assertEquals("João Silva", entity.getNome(), "O nome deve ser igual ao do DTO");
        assertEquals("12345678900", entity.getCpf(), "O CPF deve ser igual ao do DTO");
        
        // Verificar conversão do endereço
        assertNotNull(entity.getEndereco(), "O endereço não deve ser nulo");
        assertEquals("12345678", entity.getEndereco().getCep(), "O CEP deve ser igual ao do DTO");
        
        // Verificar conversão dos telefones
        assertNotNull(entity.getTelefones(), "A lista de telefones não deve ser nula");
        assertEquals(2, entity.getTelefones().size(), "Deve ter 2 telefones");
        assertEquals("11987654321", entity.getTelefones().get(0).getNumero(), "O número do primeiro telefone deve ser igual ao do DTO");
        
        // Verificar conversão dos emails
        assertNotNull(entity.getEmails(), "A lista de emails não deve ser nula");
        assertEquals(2, entity.getEmails().size(), "Deve ter 2 emails");
        assertEquals("teste1@example.com", entity.getEmails().get(0).getEmail(), "O primeiro email deve ser igual ao do DTO");
        
        // Teste com DTO sem endereço
        ClienteDTO dtoSemEndereco = new ClienteDTO(
            1L,
            "João Silva",
            "12345678900",
            null,
            telefonesDTO,
            emailsDTO
        );
        
        Cliente entitySemEndereco = Cliente.fromDTO(dtoSemEndereco);
        
        assertNotNull(entitySemEndereco, "A entidade não deve ser nula");
        assertNull(entitySemEndereco.getEndereco(), "O endereço deve ser nulo");
        
        // Teste com DTO sem telefones
        ClienteDTO dtoSemTelefones = new ClienteDTO(
            1L,
            "João Silva",
            "12345678900",
            enderecoDTO,
            null,
            emailsDTO
        );
        
        Cliente entitySemTelefones = Cliente.fromDTO(dtoSemTelefones);
        
        assertNotNull(entitySemTelefones, "A entidade não deve ser nula");
        assertNotNull(entitySemTelefones.getTelefones(), "A lista de telefones não deve ser nula");
        assertTrue(entitySemTelefones.getTelefones().isEmpty(), "A lista de telefones deve estar vazia");
        
        // Teste com DTO sem emails
        ClienteDTO dtoSemEmails = new ClienteDTO(
            1L,
            "João Silva",
            "12345678900",
            enderecoDTO,
            telefonesDTO,
            null
        );
        
        Cliente entitySemEmails = Cliente.fromDTO(dtoSemEmails);
        
        assertNotNull(entitySemEmails, "A entidade não deve ser nula");
        assertNotNull(entitySemEmails.getEmails(), "A lista de emails não deve ser nula");
        assertTrue(entitySemEmails.getEmails().isEmpty(), "A lista de emails deve estar vazia");
        
        // Teste com DTO nulo
        assertNull(Cliente.fromDTO(null), "Deve retornar null quando o DTO for nulo");
    }
    
    @Test
    public void testToDTO() {
        // Preparar dados de teste
        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setLogradouro("Rua Exemplo");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setUf("UF");
        endereco.setComplemento("Complemento Teste");
        
        Telefone telefone1 = new Telefone();
        telefone1.setTipo(TipoTelefone.CELULAR);
        telefone1.setNumero("11987654321");
        
        Telefone telefone2 = new Telefone();
        telefone2.setTipo(TipoTelefone.RESIDENCIAL);
        telefone2.setNumero("1123456789");
        
        Email email1 = new Email();
        email1.setEmail("teste1@example.com");
        
        Email email2 = new Email();
        email2.setEmail("teste2@example.com");
        
        // Teste com entidade válida completa
        Cliente entity = new Cliente();
        entity.setId(1L);
        entity.setNome("João Silva");
        entity.setCpf("12345678900");
        entity.setEndereco(endereco);
        entity.setTelefones(Arrays.asList(telefone1, telefone2));
        entity.setEmails(Arrays.asList(email1, email2));
        
        ClienteDTO dto = entity.toDTO();
        
        // Verificar conversão do cliente
        assertNotNull(dto, "O DTO não deve ser nulo");
        assertEquals(1L, dto.id(), "O ID no DTO deve ser igual ao da entidade");
        assertEquals("João Silva", dto.nome(), "O nome no DTO deve ser igual ao da entidade");
        assertEquals("12345678900", dto.cpf(), "O CPF no DTO deve ser igual ao da entidade");
        
        // Verificar conversão do endereço
        assertNotNull(dto.endereco(), "O endereço no DTO não deve ser nulo");
        assertEquals("12345678", dto.endereco().cep(), "O CEP no DTO deve ser igual ao da entidade");
        
        // Verificar conversão dos telefones
        assertNotNull(dto.telefones(), "A lista de telefones no DTO não deve ser nula");
        assertEquals(2, dto.telefones().size(), "Deve ter 2 telefones no DTO");
        assertEquals("11987654321", dto.telefones().get(0).numero(), "O número do primeiro telefone no DTO deve ser igual ao da entidade");
        
        // Verificar conversão dos emails
        assertNotNull(dto.emails(), "A lista de emails no DTO não deve ser nula");
        assertEquals(2, dto.emails().size(), "Deve ter 2 emails no DTO");
        assertEquals("teste1@example.com", dto.emails().get(0).email(), "O primeiro email no DTO deve ser igual ao da entidade");
        
        // Teste com entidade sem endereço
        Cliente entitySemEndereco = new Cliente();
        entitySemEndereco.setId(1L);
        entitySemEndereco.setNome("João Silva");
        entitySemEndereco.setCpf("12345678900");
        entitySemEndereco.setEndereco(null);
        entitySemEndereco.setTelefones(Arrays.asList(telefone1, telefone2));
        entitySemEndereco.setEmails(Arrays.asList(email1, email2));
        
        ClienteDTO dtoSemEndereco = entitySemEndereco.toDTO();
        
        assertNotNull(dtoSemEndereco, "O DTO não deve ser nulo");
        assertNull(dtoSemEndereco.endereco(), "O endereço no DTO deve ser nulo");
        
        // Teste com entidade sem telefones
        Cliente entitySemTelefones = new Cliente();
        entitySemTelefones.setId(1L);
        entitySemTelefones.setNome("João Silva");
        entitySemTelefones.setCpf("12345678900");
        entitySemTelefones.setEndereco(endereco);
        entitySemTelefones.setTelefones(Collections.emptyList());
        entitySemTelefones.setEmails(Arrays.asList(email1, email2));
        
        ClienteDTO dtoSemTelefones = entitySemTelefones.toDTO();
        
        assertNotNull(dtoSemTelefones, "O DTO não deve ser nulo");
        assertNotNull(dtoSemTelefones.telefones(), "A lista de telefones no DTO não deve ser nula");
        assertTrue(dtoSemTelefones.telefones().isEmpty(), "A lista de telefones no DTO deve estar vazia");
        
        // Teste com entidade sem emails
        Cliente entitySemEmails = new Cliente();
        entitySemEmails.setId(1L);
        entitySemEmails.setNome("João Silva");
        entitySemEmails.setCpf("12345678900");
        entitySemEmails.setEndereco(endereco);
        entitySemEmails.setTelefones(Arrays.asList(telefone1, telefone2));
        entitySemEmails.setEmails(Collections.emptyList());
        
        ClienteDTO dtoSemEmails = entitySemEmails.toDTO();
        
        assertNotNull(dtoSemEmails, "O DTO não deve ser nulo");
        assertNotNull(dtoSemEmails.emails(), "A lista de emails no DTO não deve ser nula");
        assertTrue(dtoSemEmails.emails().isEmpty(), "A lista de emails no DTO deve estar vazia");
    }
}