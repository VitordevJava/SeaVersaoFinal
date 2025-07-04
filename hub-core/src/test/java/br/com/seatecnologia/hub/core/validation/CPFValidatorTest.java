package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o validador de CPF.
 */
public class CPFValidatorTest {

    private CPFValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new CPFValidator();
    }

    @Test
    public void testNullCPF() {
        assertTrue(validator.isValid(null, context), "CPF nulo deve ser considerado válido (será tratado por @NotNull)");
    }

    @Test
    public void testEmptyCPF() {
        assertTrue(validator.isValid("", context), "CPF vazio deve ser considerado válido (será tratado por @NotEmpty)");
    }

    @Test
    public void testInvalidLengthCPF() {
        assertFalse(validator.isValid("123456", context), "CPF com menos de 11 dígitos deve ser inválido");
        assertFalse(validator.isValid("123456789012", context), "CPF com mais de 11 dígitos deve ser inválido");
    }

    @Test
    public void testAllSameDigitsCPF() {
        assertFalse(validator.isValid("00000000000", context), "CPF com todos os dígitos iguais deve ser inválido");
        assertFalse(validator.isValid("11111111111", context), "CPF com todos os dígitos iguais deve ser inválido");
        assertFalse(validator.isValid("99999999999", context), "CPF com todos os dígitos iguais deve ser inválido");
    }

    @Test
    public void testValidCPF() {
        assertTrue(validator.isValid("52998224725", context), "CPF válido deve ser aceito");
        assertTrue(validator.isValid("529.982.247-25", context), "CPF válido com formatação deve ser aceito");
    }

    @Test
    public void testInvalidCheckDigitsCPF() {
        assertFalse(validator.isValid("52998224726", context), "CPF com dígito verificador inválido deve ser rejeitado");
        assertFalse(validator.isValid("52998224715", context), "CPF com dígito verificador inválido deve ser rejeitado");
    }

    @Test
    public void testCPFWithLetters() {
        assertFalse(validator.isValid("529a9822472b", context), "CPF com letras deve ser inválido");
    }

    @Test
    public void testCPFWithSpecialCharacters() {
        assertTrue(validator.isValid("529.982.247-25", context), "CPF com formatação deve ser aceito");
        assertTrue(validator.isValid("529-982-247/25", context), "CPF com formatação alternativa deve ser aceito");
    }
}