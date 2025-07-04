package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validador para a anotação Email.
 * <p>
 * Verifica se um email tem um formato válido.
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

    private static final String EMAIL_PATTERN = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public void initialize(Email constraintAnnotation) {
        // Não é necessário inicializar nada
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true; // Deixe a validação @NotNull ou @NotEmpty lidar com isso
        }

        return pattern.matcher(email).matches();
    }
}