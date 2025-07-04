package br.com.seatecnologia.hub.json.client.entity;

import br.com.seatecnologia.hub.core.dto.EnderecoDTO;

/**
 * Entidade Endereco para persistência em JSON.
 * Esta classe representa um endereço no sistema e é usada para serialização/deserialização JSON.
 */
public class Endereco {
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;

    // Construtor padrão necessário para deserialização JSON
    public Endereco() {}

    /**
     * Converte um EnderecoDTO para a entidade Endereco.
     * @param dto O DTO a ser convertido
     * @return A entidade Endereco correspondente
     */
    public static Endereco fromDTO(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }
        Endereco entity = new Endereco();
        entity.setCep(dto.cep());
        entity.setLogradouro(dto.logradouro());
        entity.setBairro(dto.bairro());
        entity.setCidade(dto.cidade());
        entity.setUf(dto.uf());
        entity.setComplemento(dto.complemento());
        return entity;
    }

    /**
     * Converte a entidade Endereco para um EnderecoDTO.
     * @return O DTO correspondente
     */
    public EnderecoDTO toDTO() {
        return new EnderecoDTO(
            this.cep,
            this.logradouro,
            this.bairro,
            this.cidade,
            this.uf,
            this.complemento
        );
    }

    // --- GETTERS E SETTERS ---
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}