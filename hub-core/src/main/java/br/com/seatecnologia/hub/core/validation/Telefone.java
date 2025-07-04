package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotação para validação de telefone.
 * <p>
 * Esta anotação valida se um telefone tem um DDD (2 dígitos) e um número (8 ou 9 dígitos).
 */
@Documented
@Constraint(validatedBy = TelefoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Telefone {
    String message() default "Telefone inválido. Deve conter DDD (2 dígitos) e número (8 ou 9 dígitos).";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}