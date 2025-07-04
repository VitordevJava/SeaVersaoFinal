package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o validador de Nome.
 */
public class NomeValidatorTest {

    private NomeValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new NomeValidator();
    }

    @Test
    public void testNullNome() {
        assertTrue(validator.isValid(null, context), "Nome nulo deve ser considerado válido (será tratado por @NotNull)");
    }

    @Test
    public void testEmptyNome() {
        assertTrue(validator.isValid("", context), "Nome vazio deve ser considerado válido (será tratado por @NotEmpty)");
    }

    @Test
    public void testNomeComMenosDe3Caracteres() {
        assertFalse(validator.isValid("Jo", context), "Nome com menos de 3 caracteres deve ser inválido");
    }

    @Test
    public void testNomeComMaisDe50Caracteres() {
        String nomeLongo = "João da Silva Sauro Oliveira Pereira Santos Costa Lima" +
                           " Rodrigues Albuquerque Cavalcanti Mendes";
        assertFalse(validator.isValid(nomeLongo, context), "Nome com mais de 50 caracteres deve ser inválido");
    }

    @Test
    public void testNomeComNumeros() {
        assertFalse(validator.isValid("João123", context), "Nome com números deve ser inválido");
        assertFalse(validator.isValid("Maria 2", context), "Nome com números deve ser inválido");
    }

    @Test
    public void testNomeComCaracteresEspeciais() {
        assertFalse(validator.isValid("João@Silva", context), "Nome com @ deve ser inválido");
        assertFalse(validator.isValid("Maria#Santos", context), "Nome com # deve ser inválido");
        assertFalse(validator.isValid("Pedro!Lima", context), "Nome com ! deve ser inválido");
        assertFalse(validator.isValid("Ana*Costa", context), "Nome com * deve ser inválido");
    }

    @Test
    public void testNomeValido() {
        assertTrue(validator.isValid("João", context), "Nome simples deve ser válido");
        assertTrue(validator.isValid("Maria da Silva", context), "Nome com espaços deve ser válido");
        assertTrue(validator.isValid("José-Maria", context), "Nome com hífen deve ser válido");
        assertTrue(validator.isValid("D'Alessandro", context), "Nome com apóstrofo deve ser válido");
        assertTrue(validator.isValid("João da Silva Sauro", context), "Nome composto deve ser válido");
    }

    @Test
    public void testNomeComAcentos() {
        assertTrue(validator.isValid("João", context), "Nome com acentos deve ser válido");
        assertTrue(validator.isValid("José", context), "Nome com acentos deve ser válido");
        assertTrue(validator.isValid("María", context), "Nome com acentos deve ser válido");
        assertTrue(validator.isValid("Ângela", context), "Nome com acentos deve ser válido");
        assertTrue(validator.isValid("Luís", context), "Nome com acentos deve ser válido");
    }
}