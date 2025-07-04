package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotação para validação de CPF.
 * <p>
 * Esta anotação valida se um CPF tem exatamente 11 dígitos e é válido.
 */
@Documented
@Constraint(validatedBy = CPFValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CPF {
    String message() default "CPF inválido. Deve conter exatamente 11 dígitos numéricos.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}