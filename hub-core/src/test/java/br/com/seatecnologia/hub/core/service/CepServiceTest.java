package br.com.seatecnologia.hub.core.service;

import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o serviço de consulta de CEP.
 */
@ExtendWith(MockitoExtension.class)
public class CepServiceTest {

    @InjectMocks
    private CepService cepService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        // Injetar o mock do RestTemplate no CepService
        ReflectionTestUtils.setField(cepService, "restTemplate", restTemplate);
    }

    @Test
    public void testConsultarCepNulo() {
        assertNull(cepService.consultarCep(null), "CEP nulo deve retornar null");
    }

    @Test
    public void testConsultarCepVazio() {
        assertNull(cepService.consultarCep(""), "CEP vazio deve retornar null");
    }

    @Test
    public void testConsultarCepInvalido() {
        assertNull(cepService.consultarCep("1234567"), "CEP com menos de 8 dígitos deve retornar null");
        assertNull(cepService.consultarCep("123456789"), "CEP com mais de 8 dígitos deve retornar null");
    }

    @Test
    public void testConsultarCepValido() {
        // Criar uma resposta simulada da API ViaCEP
        Object mockResponse = new Object() {
            public String getCep() { return "01001000"; }
            public String getLogradouro() { return "Praça da Sé"; }
            public String getBairro() { return "Sé"; }
            public String getLocalidade() { return "São Paulo"; }
            public String getUf() { return "SP"; }
            public Boolean getErro() { return false; }
        };

        // Configurar o mock do RestTemplate para retornar a resposta simulada
        when(restTemplate.getForObject(
                anyString(),
                any(),
                eq("01001000")
        )).thenReturn(mockResponse);

        // Executar o método a ser testado
        EnderecoDTO endereco = cepService.consultarCep("01001000");

        // Verificar o resultado
        assertNotNull(endereco, "Deve retornar um endereço para CEP válido");
        assertEquals("01001000", endereco.cep(), "CEP deve ser mantido sem formatação");
        assertEquals("Praça da Sé", endereco.logradouro(), "Logradouro deve ser preenchido corretamente");
        assertEquals("Sé", endereco.bairro(), "Bairro deve ser preenchido corretamente");
        assertEquals("São Paulo", endereco.cidade(), "Cidade deve ser preenchida corretamente");
        assertEquals("SP", endereco.uf(), "UF deve ser preenchida corretamente");
        assertNull(endereco.complemento(), "Complemento deve ser null");
    }

    @Test
    public void testConsultarCepComFormatacao() {
        // Criar uma resposta simulada da API ViaCEP
        Object mockResponse = new Object() {
            public String getCep() { return "01001000"; }
            public String getLogradouro() { return "Praça da Sé"; }
            public String getBairro() { return "Sé"; }
            public String getLocalidade() { return "São Paulo"; }
            public String getUf() { return "SP"; }
            public Boolean getErro() { return false; }
        };

        // Configurar o mock do RestTemplate para retornar a resposta simulada
        when(restTemplate.getForObject(
                anyString(),
                any(),
                eq("01001000")
        )).thenReturn(mockResponse);

        // Executar o método a ser testado com CEP formatado
        EnderecoDTO endereco = cepService.consultarCep("01001-000");

        // Verificar o resultado
        assertNotNull(endereco, "Deve retornar um endereço para CEP formatado");
        assertEquals("01001000", endereco.cep(), "CEP deve ser mantido sem formatação");
    }

    @Test
    public void testConsultarCepNaoEncontrado() {
        // Criar uma resposta simulada da API ViaCEP para CEP não encontrado
        Object mockResponse = new Object() {
            public String getCep() { return null; }
            public Boolean getErro() { return true; }
        };

        // Configurar o mock do RestTemplate para retornar a resposta simulada
        when(restTemplate.getForObject(
                anyString(),
                any(),
                eq("99999999")
        )).thenReturn(mockResponse);

        // Executar o método a ser testado
        EnderecoDTO endereco = cepService.consultarCep("99999999");

        // Verificar o resultado
        assertNull(endereco, "Deve retornar null para CEP não encontrado");
    }

    @Test
    public void testConsultarCepComErroAPI() {
        // Configurar o mock do RestTemplate para lançar uma exceção
        when(restTemplate.getForObject(
                anyString(),
                any(),
                eq("01001000")
        )).thenThrow(new RestClientException("Erro de conexão"));

        // Executar o método a ser testado
        EnderecoDTO endereco = cepService.consultarCep("01001000");

        // Verificar o resultado
        assertNull(endereco, "Deve retornar null em caso de erro na API");
    }
}