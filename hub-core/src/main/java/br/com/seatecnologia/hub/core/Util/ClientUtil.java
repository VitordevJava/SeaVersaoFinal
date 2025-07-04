package br.com.seatecnologia.hub.core.Util;

public class ClientUtil {

    // Construtor privado para impedir a instanciação da classe utilitária
    private ClientUtil() {
    }

    // Remove todos os caracteres não numéricos de uma string
    public static String unmask(String valor) {
        if (valor == null || valor.isEmpty()) {
            return valor;
        }
        return valor.replaceAll("\\D", "");
    }

    // Aplica máscara de CPF no formato XXX.XXX.XXX-XX
    public static String maskCpf(String cpf) {
        cpf = unmask(cpf);
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    // Aplica máscara de CEP no formato XXXXX-XXX
    public static String maskCep(String cep) {
        cep = unmask(cep);
        if (cep == null || cep.length() != 8) {
            return cep;
        }
        return cep.replaceFirst("(\\d{5})(\\d{3})", "$1-$2");
    }

    // Aplica máscara em telefone fixo ou celular com base no tipo ou tamanho
    public static String maskTelefone(String telefone, String tipo) {
        telefone = unmask(telefone);
        if (telefone == null || telefone.isEmpty()) {
            return telefone;
        }

        // Máscara com base no tipo informado
        if (tipo != null) {
            if (tipo.equalsIgnoreCase("CELULAR") && telefone.length() == 11) {
                return telefone.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
            } else if (tipo.equalsIgnoreCase("FIXO") && telefone.length() == 10) {
                return telefone.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
            }
        }

        // Inferência automática pelo tamanho
        if (telefone.length() == 11) {
            return telefone.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else if (telefone.length() == 10) {
            return telefone.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        }

        
        return telefone;
    }
}
