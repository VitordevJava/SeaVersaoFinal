package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotação para validação de nome.
 * <p>
 * Esta anotação valida se um nome:
 * <ul>
 *   <li>Contém entre 3 e 50 caracteres</li>
 *   <li>Não contém números</li>
 *   <li>Não contém caracteres especiais (exceto espaços, hífens e apóstrofos)</li>
 * </ul>
 */
@Documented
@Constraint(validatedBy = NomeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Nome {
    String message() default "Nome inválido. Deve conter entre 3 e 50 caracteres e não pode conter números ou caracteres especiais.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}