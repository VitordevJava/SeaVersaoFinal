package br.com.seatecnologia.hub.postgres.client.repository;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.core.dto.EmailDTO;
import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import br.com.seatecnologia.hub.core.dto.TelefoneDTO;
import br.com.seatecnologia.hub.postgres.client.entity.Cliente;
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
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a implementação do repositório de Cliente.
 */
@ExtendWith(MockitoExtension.class)
public class ClienteRepositoryImplTest {

    @Mock
    private ClienteJpaRepository clienteJpaRepository;

    @InjectMocks
    private ClienteRepositoryImpl clienteRepositoryImpl;

    private Cliente clienteEntity;
    private ClienteDTO clienteDTO;

    @BeforeEach
    public void setUp() {
        // Configurar dados de teste para DTO
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
                1L,
                "João Silva",
                "12345678900",
                enderecoDTO,
                telefonesDTO,
                emailsDTO
        );

        // Configurar dados de teste para Entity
        clienteEntity = Cliente.fromDTO(clienteDTO);
    }

    @Test
    public void testSalvar() {
        // Configurar mock
        when(clienteJpaRepository.save(any(Cliente.class))).thenReturn(clienteEntity);

        // Executar método a ser testado
        ClienteDTO resultado = clienteRepositoryImpl.salvar(clienteDTO);

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("João Silva", resultado.nome());
        assertEquals("12345678900", resultado.getCpfUnmasked());
        verify(clienteJpaRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testListarTodos() {
        // Configurar mock
        Cliente clienteEntity2 = Cliente.fromDTO(new ClienteDTO(
                2L,
                "Maria Souza",
                "98765432100",
                new EnderecoDTO("02002000", "Rua Teste", "Bairro", "Cidade", "UF", null),
                Collections.singletonList(new TelefoneDTO(null, "11888888888")),
                Collections.singletonList(new EmailDTO("maria@example.com"))
        ));

        List<Cliente> clientesEntity = Arrays.asList(clienteEntity, clienteEntity2);
        when(clienteJpaRepository.findAll()).thenReturn(clientesEntity);

        // Executar método a ser testado
        List<ClienteDTO> resultado = clienteRepositoryImpl.listarTodos();

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("João Silva", resultado.get(0).nome());
        assertEquals("Maria Souza", resultado.get(1).nome());
        verify(clienteJpaRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId() {
        // Configurar mock
        when(clienteJpaRepository.findById(1L)).thenReturn(Optional.of(clienteEntity));

        // Executar método a ser testado
        Optional<ClienteDTO> resultado = clienteRepositoryImpl.buscarPorId(1L);

        // Verificar resultado
        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().nome());
        verify(clienteJpaRepository, times(1)).findById(1L);
    }

    @Test
    public void testBuscarPorIdNaoEncontrado() {
        // Configurar mock
        when(clienteJpaRepository.findById(999L)).thenReturn(Optional.empty());

        // Executar método a ser testado
        Optional<ClienteDTO> resultado = clienteRepositoryImpl.buscarPorId(999L);

        // Verificar resultado
        assertFalse(resultado.isPresent());
        verify(clienteJpaRepository, times(1)).findById(999L);
    }

    @Test
    public void testDeletarPorId() {
        // Configurar mock
        doNothing().when(clienteJpaRepository).deleteById(1L);

        // Executar método a ser testado
        clienteRepositoryImpl.deletarPorId(1L);

        // Verificar resultado
        verify(clienteJpaRepository, times(1)).deleteById(1L);
    }
}
