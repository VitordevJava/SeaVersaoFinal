package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para a anotação CEP.
 * <p>
 * Verifica se um CEP tem exatamente 8 dígitos numéricos.
 */
public class CEPValidator implements ConstraintValidator<CEP, String> {

    @Override
    public void initialize(CEP constraintAnnotation) {
        // Não é necessário inicializar nada
    }

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if (cep == null || cep.isEmpty()) {
            return true; // Deixe a validação @NotNull ou @NotEmpty lidar com isso
        }

        // Remove qualquer formatação (hífens)
        String cepSemFormatacao = cep.replaceAll("[^0-9]", "");

        // Verifica se tem exatamente 8 dígitos
        return cepSemFormatacao.length() == 8;
    }
}