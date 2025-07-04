package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotação para validação de email.
 * <p>
 * Esta anotação valida se um email tem um formato válido.
 */
@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    String message() default "Email inválido. Deve ter um formato válido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}