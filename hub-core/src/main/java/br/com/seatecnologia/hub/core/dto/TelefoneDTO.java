package br.com.seatecnologia.hub.core.dto;

import br.com.seatecnologia.hub.core.Util.ClientUtil;
import br.com.seatecnologia.hub.core.validation.Telefone;
import jakarta.validation.constraints.NotBlank;

// DTO para representar telefone (tipo e número)
public record TelefoneDTO(

    TipoTelefone tipo, // Tipo de telefone (FIXO ou CELULAR)

    @NotBlank(message = "O número de telefone é obrigatório") // Valida se o número foi informado
    @Telefone // Valida o formato do telefone
    String numero
) {
    // Garante que o telefone seja salvo sem máscara
    public TelefoneDTO {
        numero = ClientUtil.unmask(numero);
    }

    // Retorna o número de telefone formatado (ex: (61) 99999-9999)
    @Override
    public String numero() {
        return ClientUtil.maskTelefone(numero, tipo != null ? tipo.name() : "FIXO");
    }

    // Retorna o número sem formatação (apenas dígitos)
    public String getNumeroUnmasked() {
        return numero;
    }
}
