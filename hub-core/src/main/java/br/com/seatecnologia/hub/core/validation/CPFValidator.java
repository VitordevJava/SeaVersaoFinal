package br.com.seatecnologia.hub.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para a anotação CPF.
 * <p>
 * Verifica se um CPF tem exatamente 11 dígitos numéricos.
 */
public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public void initialize(CPF constraintAnnotation) {
        // Não é necessário inicializar nada
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isEmpty()) {
            return true; // Deixe a validação @NotNull ou @NotEmpty lidar com isso
        }

        // Remove qualquer formatação (pontos e hífen)
        String cpfSemFormatacao = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem exatamente 11 dígitos
        if (cpfSemFormatacao.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (CPF inválido)
        if (cpfSemFormatacao.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpfSemFormatacao.charAt(i)) * (10 - i);
        }
        int resto = soma % 11;
        int dv1 = (resto < 2) ? 0 : 11 - resto;

        // Verifica o primeiro dígito verificador
        if (Character.getNumericValue(cpfSemFormatacao.charAt(9)) != dv1) {
            return false;
        }

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpfSemFormatacao.charAt(i)) * (11 - i);
        }
        resto = soma % 11;
        int dv2 = (resto < 2) ? 0 : 11 - resto;

        // Verifica o segundo dígito verificador
        return Character.getNumericValue(cpfSemFormatacao.charAt(10)) == dv2;
    }
}