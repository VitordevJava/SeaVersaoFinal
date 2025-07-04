package br.com.seatecnologia.hub.ClientUtil; // Sugestão de pacote para utilitários

/**
 * Classe utilitária para mascarar e desmascarar strings como CPF, CEP e telefone.
 */
public final class ClientUtil {

    // Construtor privado para evitar a instanciação da classe utilitária
    private ClientUtil() {
        // Não faz nada
    }

    /**
     * Remove todos os caracteres não numéricos de uma string.
     *
     * @param valor A string a ser desmascarada.
     * @return A string contendo apenas dígitos, ou null se a entrada for null.
     */
    public static String unmask(String valor) {
        if (valor == null || valor.isEmpty()) { // Adicionado isEmpty() check
            return valor; // Retorna null ou string vazia se a entrada for assim
        }
        // CORREÇÃO CRÍTICA: Use \\D para remover qualquer caractere que NÃO seja um dígito.
        return valor.replaceAll("\\D", "");
    }

    /**
     * Mascara um CPF (Cadastro de Pessoa Física) no formato XXX.XXX.XXX-XX.
     *
     * @param cpf O CPF a ser mascarado (pode conter caracteres não numéricos).
     * @return O CPF mascarado, ou null se a entrada for null, ou o CPF desmascarado
     * se não tiver 11 dígitos.
     */
    public static String maskCpf(String cpf) {
        cpf = unmask(cpf);
        // Valida se o CPF desmascarado tem 11 dígitos
        if (cpf == null || cpf.length() != 11) {
            return cpf; // Retorna o valor original desmascarado se o comprimento for inválido
        }
        return cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    /**
     * Mascara um CEP (Código de Endereçamento Postal) no formato XXXXX-XXX.
     *
     * @param cep O CEP a ser mascarado (pode conter caracteres não numéricos).
     * @return O CEP mascarado, ou null se a entrada for null, ou o CEP desmascarado
     * se não tiver 8 dígitos.
     */
    public static String maskCep(String cep) {
        cep = unmask(cep);
        // Valida se o CEP desmascarado tem 8 dígitos
        if (cep == null || cep.length() != 8) {
            return cep; // Retorna o valor original desmascarado se o comprimento for inválido
        }
        return cep.replaceFirst("(\\d{5})(\\d{3})", "$1-$2");
    }

    /**
     * Mascara um número de telefone no formato (XX) XXXXX-XXXX (celular) ou (XX) XXXX-XXXX (fixo).
     * Tenta inferir o tipo se não for especificado ou se o comprimento não corresponder ao tipo.
     *
     * @param telefone O número de telefone a ser mascarado (pode conter caracteres não numéricos).
     * @param tipo     O tipo de telefone ("CELULAR" ou "FIXO"). Pode ser null.
     * @return O telefone mascarado, ou null se a entrada for null, ou o telefone desmascarado
     * se não corresponder a um formato conhecido.
     */
    public static String maskTelefone(String telefone, String tipo) {
        telefone = unmask(telefone);
        if (telefone == null || telefone.isEmpty()) {
            return telefone;
        }

        // Tenta mascarar com base no tipo fornecido e no comprimento esperado
        if (tipo != null) {
            if (tipo.equalsIgnoreCase("CELULAR") && telefone.length() == 11) {
                return telefone.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
            } else if (tipo.equalsIgnoreCase("FIXO") && telefone.length() == 10) {
                return telefone.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
            }
        }

        // Se o tipo não foi especificado, ou não correspondeu, tenta inferir pelo comprimento
        if (telefone.length() == 11) { // Geralmente celular no Brasil (com 9º dígito)
            return telefone.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else if (telefone.length() == 10) { // Geralmente fixo ou celular antigo (sem 9º dígito)
            return telefone.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        }

        // Se não corresponder a nenhum formato conhecido, retorna o número desmascarado
        return telefone;
    }
}