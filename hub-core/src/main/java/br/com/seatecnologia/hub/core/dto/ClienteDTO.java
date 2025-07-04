package br.com.seatecnologia.hub.core.dto;

import br.com.seatecnologia.hub.core.Util.ClientUtil;
import br.com.seatecnologia.hub.core.validation.CPF;
import br.com.seatecnologia.hub.core.validation.Nome;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * DTO para representação de um cliente.
 * <p>
 * Contém todas as informações de um cliente, incluindo dados pessoais,
 * endereço, telefones e emails.
 */
public record ClienteDTO(
    Long id,

    @NotBlank(message = "O nome é obrigatório")
    @Nome
    String nome,

    @NotBlank(message = "O CPF é obrigatório")
    @CPF
    String cpf, // Este campo agora espera o CPF já desmascarado para persistência

    @NotNull(message = "O endereço é obrigatório")
    @Valid
    EnderecoDTO endereco,

    @NotEmpty(message = "Pelo menos um telefone é obrigatório")
    @Size(min = 1, message = "Pelo menos um telefone é obrigatório")
    @Valid
    List<TelefoneDTO> telefones,

    @NotEmpty(message = "Pelo menos um email é obrigatório")
    @Size(min = 1, message = "Pelo menos um email é obrigatório")
    @Valid
    List<EmailDTO> emails
) {
    // O construtor compacto foi removido/simplificado, pois o desmascaramento
    // será feito antes da criação do DTO.
    // Opcional: Você pode manter o construtor compacto para validações,
    // mas não para atribuição de campos.

    /*
     Retorna o CPF formatado com pontos e hífen.
     retorna o CPF formatado
    */
    @Override
    public String cpf() {
        // O campo 'cpf' já deve estar desmascarado aqui.
        // A máscara é aplicada apenas para exibição.
        return ClientUtil.maskCpf(cpf);
    }

    /*
     Retorna o CPF sem formatação.
     retorna CPF sem formatação
    */
    public String getCpfUnmasked() {
        return cpf; // Retorna o CPF como ele foi armazenado no record (desmascarado)
    }
}