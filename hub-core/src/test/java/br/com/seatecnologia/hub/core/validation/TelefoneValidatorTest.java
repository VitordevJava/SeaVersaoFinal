package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o validador de Telefone.
 */
public class TelefoneValidatorTest {

    private TelefoneValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new TelefoneValidator();
    }

    @Test
    public void testNullTelefone() {
        assertTrue(validator.isValid(null, context), "Telefone nulo deve ser considerado válido (será tratado por @NotNull)");
    }

    @Test
    public void testEmptyTelefone() {
        assertTrue(validator.isValid("", context), "Telefone vazio deve ser considerado válido (será tratado por @NotEmpty)");
    }

    @Test
    public void testTelefoneComMenosDe10Digitos() {
        assertFalse(validator.isValid("123456789", context), "Telefone com menos de 10 dígitos deve ser inválido");
    }

    @Test
    public void testTelefoneComMaisDe11Digitos() {
        assertFalse(validator.isValid("123456789012", context), "Telefone com mais de 11 dígitos deve ser inválido");
    }

    @Test
    public void testTelefoneComDDDInvalido() {
        assertFalse(validator.isValid("0012345678", context), "Telefone com DDD 00 deve ser inválido");
        assertFalse(validator.isValid("0112345678", context), "Telefone com DDD 01 deve ser inválido");
    }

    @Test
    public void testTelefoneFixoValido() {
        assertTrue(validator.isValid("1123456789", context), "Telefone fixo com 8 dígitos deve ser válido");
        assertTrue(validator.isValid("11 2345-6789", context), "Telefone fixo formatado deve ser válido");
        assertTrue(validator.isValid("(11) 2345-6789", context), "Telefone fixo com parênteses deve ser válido");
    }

    @Test
    public void testTelefoneCelularValido() {
        assertTrue(validator.isValid("11987654321", context), "Telefone celular com 9 dígitos deve ser válido");
        assertTrue(validator.isValid("11 98765-4321", context), "Telefone celular formatado deve ser válido");
        assertTrue(validator.isValid("(11) 98765-4321", context), "Telefone celular com parênteses deve ser válido");
    }

    @Test
    public void testTelefoneComLetras() {
        assertFalse(validator.isValid("11a2345678", context), "Telefone com letras deve ser inválido");
        assertFalse(validator.isValid("11 abcd-efgh", context), "Telefone com apenas letras deve ser inválido");
    }

    @Test
    public void testTelefoneComCaracteresEspeciais() {
        assertTrue(validator.isValid("(11) 2345-6789", context), "Telefone com parênteses e hífen deve ser válido");
        assertTrue(validator.isValid("11.2345.6789", context), "Telefone com pontos deve ser válido");
        assertTrue(validator.isValid("11/2345-6789", context), "Telefone com barra deve ser válido");
    }

    @Test
    public void testTelefoneComEspacos() {
        assertTrue(validator.isValid("11 2345 6789", context), "Telefone com espaços deve ser válido");
    }
}