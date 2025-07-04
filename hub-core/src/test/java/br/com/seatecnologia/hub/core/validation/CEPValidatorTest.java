package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o validador de CEP.
 */
public class CEPValidatorTest {

    private CEPValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new CEPValidator();
    }

    @Test
    public void testNullCEP() {
        assertTrue(validator.isValid(null, context), "CEP nulo deve ser considerado válido (será tratado por @NotNull)");
    }

    @Test
    public void testEmptyCEP() {
        assertTrue(validator.isValid("", context), "CEP vazio deve ser considerado válido (será tratado por @NotEmpty)");
    }

    @Test
    public void testInvalidLengthCEP() {
        assertFalse(validator.isValid("1234567", context), "CEP com menos de 8 dígitos deve ser inválido");
        assertFalse(validator.isValid("123456789", context), "CEP com mais de 8 dígitos deve ser inválido");
    }

    @Test
    public void testValidCEP() {
        assertTrue(validator.isValid("12345678", context), "CEP válido deve ser aceito");
        assertTrue(validator.isValid("12345-678", context), "CEP válido com formatação deve ser aceito");
    }

    @Test
    public void testCEPWithLetters() {
        assertFalse(validator.isValid("1234567a", context), "CEP com letras deve ser inválido");
        assertFalse(validator.isValid("abcdefgh", context), "CEP com apenas letras deve ser inválido");
    }

    @Test
    public void testCEPWithSpecialCharacters() {
        assertTrue(validator.isValid("12345-678", context), "CEP com hífen deve ser aceito");
        assertTrue(validator.isValid("12.345.678", context), "CEP com pontos deve ser aceito");
        assertTrue(validator.isValid("12.345-678", context), "CEP com pontos e hífen deve ser aceito");
    }
}