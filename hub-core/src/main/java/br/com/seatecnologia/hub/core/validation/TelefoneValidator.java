package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para a anotação Telefone.
 * <p>
 * Verifica se um telefone tem um DDD (2 dígitos) e um número (8 ou 9 dígitos).
 */
public class TelefoneValidator implements ConstraintValidator<Telefone, String> {

    @Override
    public void initialize(Telefone constraintAnnotation) {
        // Não é necessário inicializar nada
    }

    @Override
    public boolean isValid(String telefone, ConstraintValidatorContext context) {
        if (telefone == null || telefone.isEmpty()) {
            return true; // Deixe a validação @NotNull ou @NotEmpty lidar com isso
        }

        // Remove qualquer formatação (parênteses, espaços, hífens)
        String telefoneSemFormatacao = telefone.replaceAll("[^0-9]", "");

        // Verifica se tem entre 10 e 11 dígitos (DDD + número)
        if (telefoneSemFormatacao.length() < 10 || telefoneSemFormatacao.length() > 11) {
            return false;
        }

        // Verifica se os dois primeiros dígitos formam um DDD válido
        // (simplificado, na prática seria necessário verificar contra uma lista de DDDs válidos)
        String ddd = telefoneSemFormatacao.substring(0, 2);
        if (ddd.equals("00") || ddd.equals("01")) {
            return false;
        }

        // Verifica se o restante tem 8 ou 9 dígitos
        String numero = telefoneSemFormatacao.substring(2);
        return numero.length() == 8 || numero.length() == 9;
    }
}