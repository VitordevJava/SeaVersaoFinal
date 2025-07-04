package br.com.seatecnologia.hub.core.dto;

import br.com.seatecnologia.hub.core.Util.ClientUtil;
import br.com.seatecnologia.hub.core.validation.CEP;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para representação de um endereço.
 * <p>
 * Contém todas as informações de um endereço, incluindo CEP, logradouro,
 * bairro, cidade, UF e complemento.
 */
public record EnderecoDTO(
    @NotBlank(message = "O CEP é obrigatório")
    @CEP
    String cep,

    @NotBlank(message = "O logradouro é obrigatório")
    String logradouro,

    @NotBlank(message = "O bairro é obrigatório")
    String bairro,

    @NotBlank(message = "A cidade é obrigatória")
    String cidade,

    @NotBlank(message = "A UF é obrigatória")
    String uf,

    String complemento
) {
     
	
	//Construtor personalizado que garante que o CEP seja armazenado sem formatação.
    public EnderecoDTO {
        cep = ClientUtil.unmask(cep);
    }

 
    
     // Retorna o CEP formatado com hífen.
     // retrona o CEP formatado
    @Override
    public String cep() {
        return ClientUtil.maskCep(cep);
    }

    /**
     * Retorna o CEP sem formatação.
     * @return CEP sem formatação
     */
    public String getCepUnmasked() {
        return cep;
    }
}
