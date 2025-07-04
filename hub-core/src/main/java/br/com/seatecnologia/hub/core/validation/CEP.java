package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotação para validação de CEP.
 * <p>
 * Esta anotação valida se um CEP tem exatamente 8 dígitos numéricos.
 */
@Documented
@Constraint(validatedBy = CEPValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CEP {
    String message() default "CEP inválido. Deve conter exatamente 8 dígitos numéricos.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}