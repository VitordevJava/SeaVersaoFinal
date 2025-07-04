package br.com.seatecnologia.hub.memory.client.repository;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.core.dto.EmailDTO;
import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import br.com.seatecnologia.hub.core.dto.TelefoneDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a implementação do repositório de Cliente baseado em memória.
 */
public class ClienteRepositoryImplTest {

    private ClienteRepositoryImpl clienteRepository;
    private ClienteDTO clienteDTO;

    @BeforeEach
    public void setUp() {
        // Inicializar o repositório
        clienteRepository = new ClienteRepositoryImpl();
        
        // Configurar dados de teste
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "01001000",
                "Praça da Sé",
                "Sé",
                "São Paulo",
                "SP",
                null
        );

        List<TelefoneDTO> telefonesDTO = Collections.singletonList(
                new TelefoneDTO(null, "11999999999")
        );

        List<EmailDTO> emailsDTO = Collections.singletonList(
                new EmailDTO("teste@example.com")
        );

        clienteDTO = new ClienteDTO(
                null, // ID será gerado pelo repositório
                "João Silva",
                "12345678900",
                enderecoDTO,
                telefonesDTO,
                emailsDTO
        );
    }

    @Test
    public void testSalvarNovoCliente() {
        // Executar método a ser testado
        ClienteDTO resultado = clienteRepository.salvar(clienteDTO);

        // Verificar resultado
        assertNotNull(resultado);
        assertNotNull(resultado.id());
        assertEquals("João Silva", resultado.nome());
        assertEquals("12345678900", resultado.cpf());
    }

    @Test
    public void testSalvarClienteExistente() {
        // Salvar cliente primeiro para obter um ID
        ClienteDTO clienteSalvo = clienteRepository.salvar(clienteDTO);
        
        // Criar uma versão atualizada do cliente
        ClienteDTO clienteAtualizado = new ClienteDTO(
                clienteSalvo.id(),
                "João Silva Atualizado",
                clienteSalvo.cpf(),
                clienteSalvo.endereco(),
                clienteSalvo.telefones(),
                clienteSalvo.emails()
        );
        
        // Executar método a ser testado
        ClienteDTO resultado = clienteRepository.salvar(clienteAtualizado);

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(clienteSalvo.id(), resultado.id());
        assertEquals("João Silva Atualizado", resultado.nome());
    }

    @Test
    public void testListarTodos() {
        // Verificar que o repositório está vazio inicialmente
        assertTrue(clienteRepository.listarTodos().isEmpty());
        
        // Salvar alguns clientes para testar
        clienteRepository.salvar(clienteDTO);
        
        ClienteDTO outroCliente = new ClienteDTO(
                null,
                "Maria Souza",
                "98765432100",
                clienteDTO.endereco(),
                clienteDTO.telefones(),
                clienteDTO.emails()
        );
        clienteRepository.salvar(outroCliente);
        
        // Executar método a ser testado
        List<ClienteDTO> resultado = clienteRepository.listarTodos();

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(c -> "João Silva".equals(c.nome())));
        assertTrue(resultado.stream().anyMatch(c -> "Maria Souza".equals(c.nome())));
    }

    @Test
    public void testBuscarPorId() {
        // Salvar cliente primeiro para obter um ID
        ClienteDTO clienteSalvo = clienteRepository.salvar(clienteDTO);
        
        // Executar método a ser testado
        Optional<ClienteDTO> resultado = clienteRepository.buscarPorId(clienteSalvo.id());

        // Verificar resultado
        assertTrue(resultado.isPresent());
        assertEquals(clienteSalvo.id(), resultado.get().id());
        assertEquals("João Silva", resultado.get().nome());
    }

    @Test
    public void testBuscarPorIdNaoEncontrado() {
        // Executar método a ser testado com um ID que não existe
        Optional<ClienteDTO> resultado = clienteRepository.buscarPorId(999L);

        // Verificar resultado
        assertFalse(resultado.isPresent());
    }

    @Test
    public void testDeletarPorId() {
        // Salvar cliente primeiro para obter um ID
        ClienteDTO clienteSalvo = clienteRepository.salvar(clienteDTO);
        
        // Verificar que o cliente existe
        assertTrue(clienteRepository.buscarPorId(clienteSalvo.id()).isPresent());
        
        // Executar método a ser testado
        clienteRepository.deletarPorId(clienteSalvo.id());

        // Verificar que o cliente foi removido
        assertFalse(clienteRepository.buscarPorId(clienteSalvo.id()).isPresent());
    }

    @Test
    public void testDeletarPorIdNaoEncontrado() {
        // Executar método a ser testado com um ID que não existe
        // Não deve lançar exceção
        assertDoesNotThrow(() -> clienteRepository.deletarPorId(999L));
    }

    @Test
    public void testIdSequence() {
        // Salvar vários clientes e verificar que os IDs são incrementados
        ClienteDTO cliente1 = clienteRepository.salvar(clienteDTO);
        
        ClienteDTO outroClienteDTO = new ClienteDTO(
                null,
                "Maria Souza",
                "98765432100",
                clienteDTO.endereco(),
                clienteDTO.telefones(),
                clienteDTO.emails()
        );
        ClienteDTO cliente2 = clienteRepository.salvar(outroClienteDTO);
        
        // Verificar que os IDs são diferentes e incrementais
        assertNotEquals(cliente1.id(), cliente2.id());
        assertEquals(cliente1.id() + 1, cliente2.id());
    }
}