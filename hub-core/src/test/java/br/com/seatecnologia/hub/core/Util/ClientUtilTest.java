package br.com.seatecnologia.hub.core.Util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe ClientUtil.
 */
public class ClientUtilTest {

    @Test
    public void testUnmask() {
        // Teste com valor nulo
        assertNull(ClientUtil.unmask(null), "Valor nulo deve retornar nulo");
        
        // Teste com string vazia
        assertEquals("", ClientUtil.unmask(""), "String vazia deve retornar string vazia");
        
        // Teste com string sem caracteres especiais
        assertEquals("12345678900", ClientUtil.unmask("12345678900"), "String sem caracteres especiais deve permanecer inalterada");
        
        // Teste com string contendo caracteres especiais
        assertEquals("12345678900", ClientUtil.unmask("123.456.789-00"), "Deve remover pontos e hífen do CPF");
        assertEquals("12345678", ClientUtil.unmask("12345-678"), "Deve remover hífen do CEP");
        assertEquals("1122334455", ClientUtil.unmask("(11) 2233-4455"), "Deve remover parênteses, espaço e hífen do telefone");
    }

    @Test
    public void testMaskCpf() {
        // Teste com valor nulo
        assertNull(ClientUtil.maskCpf(null), "Valor nulo deve retornar nulo");
        
        // Teste com CPF já formatado
        assertEquals("123.456.789-00", ClientUtil.maskCpf("123.456.789-00"), "CPF já formatado deve ser reformatado corretamente");
        
        // Teste com CPF sem formatação
        assertEquals("123.456.789-00", ClientUtil.maskCpf("12345678900"), "CPF sem formatação deve ser formatado corretamente");
        
        // Teste com CPF incompleto (não deve lançar exceção, mas o resultado pode não ser o esperado)
        assertDoesNotThrow(() -> ClientUtil.maskCpf("123"), "Não deve lançar exceção para CPF incompleto");
    }

    @Test
    public void testMaskCep() {
        // Teste com valor nulo
        assertNull(ClientUtil.maskCep(null), "Valor nulo deve retornar nulo");
        
        // Teste com CEP já formatado
        assertEquals("12345-678", ClientUtil.maskCep("12345-678"), "CEP já formatado deve ser reformatado corretamente");
        
        // Teste com CEP sem formatação
        assertEquals("12345-678", ClientUtil.maskCep("12345678"), "CEP sem formatação deve ser formatado corretamente");
        
        // Teste com CEP incompleto (não deve lançar exceção, mas o resultado pode não ser o esperado)
        assertDoesNotThrow(() -> ClientUtil.maskCep("123"), "Não deve lançar exceção para CEP incompleto");
    }

    @Test
    public void testMaskTelefoneCelular() {
        // Teste com telefone celular
        assertEquals("(11) 98765-4321", ClientUtil.maskTelefone("11987654321", "CELULAR"), "Telefone celular deve ser formatado corretamente");
        assertEquals("(11) 98765-4321", ClientUtil.maskTelefone("(11) 98765-4321", "CELULAR"), "Telefone celular já formatado deve ser reformatado corretamente");
    }

    @Test
    public void testMaskTelefoneFixo() {
        // Teste com telefone fixo
        assertEquals("(11) 2345-6789", ClientUtil.maskTelefone("1123456789", "FIXO"), "Telefone fixo deve ser formatado corretamente");
        assertEquals("(11) 2345-6789", ClientUtil.maskTelefone("(11) 2345-6789", "FIXO"), "Telefone fixo já formatado deve ser reformatado corretamente");
    }
}