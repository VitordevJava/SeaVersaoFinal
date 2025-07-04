package br.com.seatecnologia.hub.service.client.service;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.core.dto.EmailDTO;
import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import br.com.seatecnologia.hub.core.dto.TelefoneDTO;
import br.com.seatecnologia.hub.persistence.api.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a implementação do serviço de Cliente.
 */
@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private ClienteDTO clienteDTO;
    private EnderecoDTO enderecoDTO;
    private List<TelefoneDTO> telefonesDTO;
    private List<EmailDTO> emailsDTO;

    @BeforeEach
    public void setUp() {
        // Configurar dados de teste
        enderecoDTO = new EnderecoDTO(
                "01001000",
                "Praça da Sé",
                "Sé",
                "São Paulo",
                "SP",
                null
        );

        telefonesDTO = Collections.singletonList(
                new TelefoneDTO(null, "11999999999")
        );

        emailsDTO = Collections.singletonList(
                new EmailDTO("teste@example.com")
        );

        clienteDTO = new ClienteDTO(
                1L,
                "João Silva",
                "12345678900",
                enderecoDTO,
                telefonesDTO,
                emailsDTO
        );
    }

    @Test
    public void testCriar() {
        // Configurar mock
        ClienteDTO clienteSemId = new ClienteDTO(
                null,
                "João Silva",
                "12345678900",
                enderecoDTO,
                telefonesDTO,
                emailsDTO
        );

        when(clienteRepository.salvar(any(ClienteDTO.class))).thenReturn(clienteDTO);

        // Executar método a ser testado
        ClienteDTO resultado = clienteService.criar(clienteSemId);

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("João Silva", resultado.nome());
        assertEquals("12345678900", resultado.cpf());
        verify(clienteRepository, times(1)).salvar(any(ClienteDTO.class));
    }

    @Test
    public void testCriarComIdExistente() {
        // Executar método a ser testado e verificar exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.criar(clienteDTO); // clienteDTO já tem ID
        });

        assertEquals("Novo cliente não deve ter um ID", exception.getMessage());
        verify(clienteRepository, never()).salvar(any(ClienteDTO.class));
    }

    @Test
    public void testAtualizar() {
        // Configurar mock
        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(clienteDTO));
        
        ClienteDTO clienteAtualizado = new ClienteDTO(
                null, // ID será definido pelo método atualizar
                "João Silva Atualizado",
                "12345678900",
                enderecoDTO,
                telefonesDTO,
                emailsDTO
        );
        
        ClienteDTO clienteAtualizadoComId = new ClienteDTO(
                1L,
                "João Silva Atualizado",
                "12345678900",
                enderecoDTO,
                telefonesDTO,
                emailsDTO
        );
        
        when(clienteRepository.salvar(any(ClienteDTO.class))).thenReturn(clienteAtualizadoComId);

        // Executar método a ser testado
        ClienteDTO resultado = clienteService.atualizar(1L, clienteAtualizado);

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("João Silva Atualizado", resultado.nome());
        verify(clienteRepository, times(1)).buscarPorId(1L);
        verify(clienteRepository, times(1)).salvar(any(ClienteDTO.class));
    }

    @Test
    public void testAtualizarClienteNaoEncontrado() {
        // Configurar mock
        when(clienteRepository.buscarPorId(999L)).thenReturn(Optional.empty());

        // Executar método a ser testado e verificar exceção
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.atualizar(999L, clienteDTO);
        });

        assertEquals("Cliente não encontrado com ID: 999", exception.getMessage());
        verify(clienteRepository, times(1)).buscarPorId(999L);
        verify(clienteRepository, never()).salvar(any(ClienteDTO.class));
    }

    @Test
    public void testListarTodos() {
        // Configurar mock
        List<ClienteDTO> clientes = Arrays.asList(
                clienteDTO,
                new ClienteDTO(2L, "Maria Souza", "98765432100", enderecoDTO, telefonesDTO, emailsDTO)
        );
        when(clienteRepository.listarTodos()).thenReturn(clientes);

        // Executar método a ser testado
        List<ClienteDTO> resultado = clienteService.listarTodos();

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("João Silva", resultado.get(0).nome());
        assertEquals("Maria Souza", resultado.get(1).nome());
        verify(clienteRepository, times(1)).listarTodos();
    }

    @Test
    public void testBuscarPorId() {
        // Configurar mock
        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(clienteDTO));

        // Executar método a ser testado
        Optional<ClienteDTO> resultado = clienteService.buscarPorId(1L);

        // Verificar resultado
        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().nome());
        verify(clienteRepository, times(1)).buscarPorId(1L);
    }

    @Test
    public void testBuscarPorIdNaoEncontrado() {
        // Configurar mock
        when(clienteRepository.buscarPorId(999L)).thenReturn(Optional.empty());

        // Executar método a ser testado
        Optional<ClienteDTO> resultado = clienteService.buscarPorId(999L);

        // Verificar resultado
        assertFalse(resultado.isPresent());
        verify(clienteRepository, times(1)).buscarPorId(999L);
    }

    @Test
    public void testDeletar() {
        // Configurar mock
        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(clienteDTO));
        doNothing().when(clienteRepository).deletarPorId(1L);

        // Executar método a ser testado
        clienteService.deletar(1L);

        // Verificar resultado
        verify(clienteRepository, times(1)).buscarPorId(1L);
        verify(clienteRepository, times(1)).deletarPorId(1L);
    }

    @Test
    public void testDeletarClienteNaoEncontrado() {
        // Configurar mock
        when(clienteRepository.buscarPorId(999L)).thenReturn(Optional.empty());

        // Executar método a ser testado e verificar exceção
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.deletar(999L);
        });

        assertEquals("Cliente não encontrado com ID: 999", exception.getMessage());
        verify(clienteRepository, times(1)).buscarPorId(999L);
        verify(clienteRepository, never()).deletarPorId(anyLong());
    }
}