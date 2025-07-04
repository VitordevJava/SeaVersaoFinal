package br.com.seatecnologia.hub.postgres.client.entity;

import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import jakarta.persistence.*;

@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;

    // Construtor padrão exigido pelo JPA
    public Endereco() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    // Método para converter EnderecoDTO para a entidade Endereco (fromDTO)
    public static Endereco fromDTO(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }
        Endereco entity = new Endereco();
        // Não definimos o ID aqui
        entity.setCep(dto.cep());
        entity.setLogradouro(dto.logradouro());
        entity.setBairro(dto.bairro());
        entity.setCidade(dto.cidade());
        entity.setUf(dto.uf());
        entity.setComplemento(dto.complemento());
        return entity;
    }

    // Método para converter entidade Endereco para EnderecoDTO (toDTO)
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
}
