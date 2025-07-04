package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o validador de Email.
 */
public class EmailValidatorTest {

    private EmailValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new EmailValidator();
    }

    @Test
    public void testNullEmail() {
        assertTrue(validator.isValid(null, context), "Email nulo deve ser considerado válido (será tratado por @NotNull)");
    }

    @Test
    public void testEmptyEmail() {
        assertTrue(validator.isValid("", context), "Email vazio deve ser considerado válido (será tratado por @NotEmpty)");
    }

    @Test
    public void testValidEmails() {
        assertTrue(validator.isValid("usuario@dominio.com", context), "Email simples deve ser válido");
        assertTrue(validator.isValid("usuario.nome@dominio.com", context), "Email com ponto no local-part deve ser válido");
        assertTrue(validator.isValid("usuario+tag@dominio.com", context), "Email com + no local-part deve ser válido");
        assertTrue(validator.isValid("usuario@sub.dominio.com", context), "Email com subdomínio deve ser válido");
        assertTrue(validator.isValid("usuario-nome@dominio.com", context), "Email com hífen no local-part deve ser válido");
        assertTrue(validator.isValid("usuario_nome@dominio.com", context), "Email com underscore no local-part deve ser válido");
        assertTrue(validator.isValid("usuario123@dominio.com", context), "Email com números no local-part deve ser válido");
    }

    @Test
    public void testInvalidEmails() {
        assertFalse(validator.isValid("usuario@", context), "Email sem domínio deve ser inválido");
        assertFalse(validator.isValid("@dominio.com", context), "Email sem local-part deve ser inválido");
        assertFalse(validator.isValid("usuario@dominio", context), "Email sem TLD deve ser inválido");
        assertFalse(validator.isValid("usuario@.com", context), "Email com domínio iniciando com ponto deve ser inválido");
        assertFalse(validator.isValid("usuario@dominio..com", context), "Email com pontos consecutivos no domínio deve ser inválido");
        assertFalse(validator.isValid("usuario..nome@dominio.com", context), "Email com pontos consecutivos no local-part deve ser inválido");
        assertFalse(validator.isValid("usuario@dominio.c", context), "Email com TLD de apenas 1 caractere deve ser inválido");
        assertFalse(validator.isValid("usuário@dominio.com", context), "Email com caracteres acentuados deve ser inválido");
        assertFalse(validator.isValid("usuario@domínio.com", context), "Email com domínio contendo caracteres acentuados deve ser inválido");
        assertFalse(validator.isValid("usuario nome@dominio.com", context), "Email com espaço no local-part deve ser inválido");
        assertFalse(validator.isValid("usuario@dominio com", context), "Email com espaço no domínio deve ser inválido");
    }
}