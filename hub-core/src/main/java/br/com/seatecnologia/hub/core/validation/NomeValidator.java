package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para a anotação Nome.
 * <p>
 * Verifica se um nome:
 * <ul>
 *   <li>Contém entre 3 e 50 caracteres</li>
 *   <li>Não contém números</li>
 *   <li>Não contém caracteres especiais (exceto espaços, hífens e apóstrofos)</li>
 * </ul>
 */
public class NomeValidator implements ConstraintValidator<Nome, String> {

    @Override
    public void initialize(Nome constraintAnnotation) {
        // Não é necessário inicializar nada
    }

    @Override
    public boolean isValid(String nome, ConstraintValidatorContext context) {
        if (nome == null || nome.isEmpty()) {
            return true; // Deixe a validação @NotNull ou @NotEmpty lidar com isso
        }

        // Verifica o tamanho (entre 3 e 50 caracteres)
        if (nome.length() < 3 || nome.length() > 50) {
            return false;
        }

        // Verifica se não contém números
        if (nome.matches(".*\\d.*")) {
            return false;
        }

        // Verifica se não contém caracteres especiais (exceto espaços, hífens e apóstrofos)
        // Permite letras (incluindo acentuadas), espaços, hífens e apóstrofos
        return nome.matches("^[\\p{L}\\s\\-']+$");
    }
}