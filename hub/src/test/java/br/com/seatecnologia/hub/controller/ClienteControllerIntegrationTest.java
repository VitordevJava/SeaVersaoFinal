package br.com.seatecnologia.hub.controller;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.core.dto.EmailDTO;
import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import br.com.seatecnologia.hub.core.dto.TelefoneDTO;
import br.com.seatecnologia.hub.service.api.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para o controlador de Cliente.
 * <p>
 * Estes testes verificam se os endpoints da API funcionam corretamente,
 * incluindo a autorização baseada em papéis.
 */
@WebMvcTest(ClienteController.class)
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClienteDTO clienteDTO;
    private EnderecoDTO enderecoDTO;
    private List<TelefoneDTO> telefonesDTO;
    private List<EmailDTO> emailsDTO;

    @BeforeEach
    public void setUp() {
        // Configurar dados de teste
        enderecoDTO = new EnderecoDTO(
                "01001000", // CEP sem formatação
                "Praça da Sé",
                "Sé",
                "São Paulo",
                "SP",
                null
        );

        telefonesDTO = Collections.singletonList(
                new TelefoneDTO(null, "11999999999") // Telefone sem formatação
        );

        emailsDTO = Collections.singletonList(
                new EmailDTO("teste@example.com")
        );

        clienteDTO = new ClienteDTO(
                1L,
                "João Silva",
                "12345678900", // CPF sem formatação
                enderecoDTO,
                telefonesDTO,
                emailsDTO
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCriar() throws Exception {
        // Configurar mock
        when(clienteService.criar(any(ClienteDTO.class))).thenReturn(clienteDTO);

        // Criar JSON manualmente com CPF sem formatação
        String requestJson = "{"
                + "\"id\":1,"
                + "\"nome\":\"João Silva\","
                + "\"cpf\":\"12345678900\","
                + "\"endereco\":{"
                + "\"cep\":\"01001000\","
                + "\"logradouro\":\"Praça da Sé\","
                + "\"bairro\":\"Sé\","
                + "\"cidade\":\"São Paulo\","
                + "\"uf\":\"SP\","
                + "\"complemento\":null"
                + "},"
                + "\"telefones\":["
                + "{"
                + "\"tipo\":null,"
                + "\"numero\":\"11999999999\""
                + "}"
                + "],"
                + "\"emails\":["
                + "{"
                + "\"email\":\"teste@example.com\""
                + "}"
                + "]"
                + "}";

        // Executar requisição e verificar resultado
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("João Silva")))
                .andExpect(jsonPath("$.cpf", is("123.456.789-00")));

        verify(clienteService, times(1)).criar(any(ClienteDTO.class));
    }

    @Test
    @WithMockUser(roles = "USER") // Usuário sem permissão para criar
    public void testCriarSemPermissao() throws Exception {
        // Usar o mesmo JSON do método testCriar
        String requestJson = "{"
                + "\"id\":1,"
                + "\"nome\":\"João Silva\","
                + "\"cpf\":\"12345678900\","
                + "\"endereco\":{"
                + "\"cep\":\"01001000\","
                + "\"logradouro\":\"Praça da Sé\","
                + "\"bairro\":\"Sé\","
                + "\"cidade\":\"São Paulo\","
                + "\"uf\":\"SP\","
                + "\"complemento\":null"
                + "},"
                + "\"telefones\":["
                + "{"
                + "\"tipo\":null,"
                + "\"numero\":\"11999999999\""
                + "}"
                + "],"
                + "\"emails\":["
                + "{"
                + "\"email\":\"teste@example.com\""
                + "}"
                + "]"
                + "}";

        // Executar requisição e verificar resultado
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .with(csrf()))
                .andExpect(status().isForbidden());

        verify(clienteService, never()).criar(any(ClienteDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAtualizar() throws Exception {
        // Configurar mock
        when(clienteService.atualizar(eq(1L), any(ClienteDTO.class))).thenReturn(clienteDTO);

        // Usar o mesmo JSON dos métodos anteriores
        String requestJson = "{"
                + "\"id\":1,"
                + "\"nome\":\"João Silva\","
                + "\"cpf\":\"12345678900\","
                + "\"endereco\":{"
                + "\"cep\":\"01001000\","
                + "\"logradouro\":\"Praça da Sé\","
                + "\"bairro\":\"Sé\","
                + "\"cidade\":\"São Paulo\","
                + "\"uf\":\"SP\","
                + "\"complemento\":null"
                + "},"
                + "\"telefones\":["
                + "{"
                + "\"tipo\":null,"
                + "\"numero\":\"11999999999\""
                + "}"
                + "],"
                + "\"emails\":["
                + "{"
                + "\"email\":\"teste@example.com\""
                + "}"
                + "]"
                + "}";

        // Executar requisição e verificar resultado
        mockMvc.perform(put("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("João Silva")))
                .andExpect(jsonPath("$.cpf", is("123.456.789-00")));

        verify(clienteService, times(1)).atualizar(eq(1L), any(ClienteDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAtualizarNaoEncontrado() throws Exception {
        // Configurar mock
        when(clienteService.atualizar(eq(999L), any(ClienteDTO.class)))
                .thenThrow(new RuntimeException("Cliente não encontrado"));

        // Usar o mesmo JSON dos métodos anteriores
        String requestJson = "{"
                + "\"id\":1,"
                + "\"nome\":\"João Silva\","
                + "\"cpf\":\"12345678900\","
                + "\"endereco\":{"
                + "\"cep\":\"01001000\","
                + "\"logradouro\":\"Praça da Sé\","
                + "\"bairro\":\"Sé\","
                + "\"cidade\":\"São Paulo\","
                + "\"uf\":\"SP\","
                + "\"complemento\":null"
                + "},"
                + "\"telefones\":["
                + "{"
                + "\"tipo\":null,"
                + "\"numero\":\"11999999999\""
                + "}"
                + "],"
                + "\"emails\":["
                + "{"
                + "\"email\":\"teste@example.com\""
                + "}"
                + "]"
                + "}";

        // Executar requisição e verificar resultado
        mockMvc.perform(put("/api/clientes/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .with(csrf()))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).atualizar(eq(999L), any(ClienteDTO.class));
    }

    @Test
    @WithMockUser(roles = "USER") // Usuário sem permissão para atualizar
    public void testAtualizarSemPermissao() throws Exception {
        // Usar o mesmo JSON dos métodos anteriores
        String requestJson = "{"
                + "\"id\":1,"
                + "\"nome\":\"João Silva\","
                + "\"cpf\":\"12345678900\","
                + "\"endereco\":{"
                + "\"cep\":\"01001000\","
                + "\"logradouro\":\"Praça da Sé\","
                + "\"bairro\":\"Sé\","
                + "\"cidade\":\"São Paulo\","
                + "\"uf\":\"SP\","
                + "\"complemento\":null"
                + "},"
                + "\"telefones\":["
                + "{"
                + "\"tipo\":null,"
                + "\"numero\":\"11999999999\""
                + "}"
                + "],"
                + "\"emails\":["
                + "{"
                + "\"email\":\"teste@example.com\""
                + "}"
                + "]"
                + "}";

        // Executar requisição e verificar resultado
        mockMvc.perform(put("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .with(csrf()))
                .andExpect(status().isForbidden());

        verify(clienteService, never()).atualizar(anyLong(), any(ClienteDTO.class));
    }

    @Test
    @WithMockUser(roles = "USER") // Usuário com permissão para listar
    public void testListarTodos() throws Exception {
        // Configurar mock
        List<ClienteDTO> clientes = Arrays.asList(
                clienteDTO,
                new ClienteDTO(2L, "Maria Souza", "98765432100", enderecoDTO, telefonesDTO, emailsDTO)
        );
        when(clienteService.listarTodos()).thenReturn(clientes);

        // Executar requisição e verificar resultado
        mockMvc.perform(get("/api/clientes")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("João Silva")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nome", is("Maria Souza")));

        verify(clienteService, times(1)).listarTodos();
    }

    @Test
    @WithMockUser(roles = "USER") // Usuário com permissão para buscar
    public void testBuscarPorId() throws Exception {
        // Configurar mock
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(clienteDTO));

        // Executar requisição e verificar resultado
        mockMvc.perform(get("/api/clientes/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("João Silva")))
                .andExpect(jsonPath("$.cpf", is("123.456.789-00")));

        verify(clienteService, times(1)).buscarPorId(1L);
    }

    @Test
    @WithMockUser(roles = "USER") // Usuário com permissão para buscar
    public void testBuscarPorIdNaoEncontrado() throws Exception {
        // Configurar mock
        when(clienteService.buscarPorId(999L)).thenReturn(Optional.empty());

        // Executar requisição e verificar resultado
        mockMvc.perform(get("/api/clientes/999")
                .with(csrf()))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).buscarPorId(999L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeletar() throws Exception {
        // Configurar mock
        doNothing().when(clienteService).deletar(1L);

        // Executar requisição e verificar resultado
        mockMvc.perform(delete("/api/clientes/1")
                .with(csrf()))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).deletar(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeletarNaoEncontrado() throws Exception {
        // Configurar mock
        doThrow(new RuntimeException("Cliente não encontrado")).when(clienteService).deletar(999L);

        // Executar requisição e verificar resultado
        mockMvc.perform(delete("/api/clientes/999")
                .with(csrf()))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).deletar(999L);
    }

    @Test
    @WithMockUser(roles = "USER") // Usuário sem permissão para deletar
    public void testDeletarSemPermissao() throws Exception {
        // Executar requisição e verificar resultado
        mockMvc.perform(delete("/api/clientes/1")
                .with(csrf()))
                .andExpect(status().isForbidden());

        verify(clienteService, never()).deletar(anyLong());
    }
}
