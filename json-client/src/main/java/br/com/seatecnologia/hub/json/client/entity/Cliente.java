package br.com.seatecnologia.hub.json.client.entity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.core.dto.EmailDTO;
import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import br.com.seatecnologia.hub.core.dto.TelefoneDTO;

/**
 * Entidade Cliente para persistência em JSON.
 * Esta classe representa um cliente no sistema e é usada para serialização/deserialização JSON.
 */
public class Cliente {

    private Long id;
    private String nome;
    private String cpf;
    private Endereco endereco;
    private List<Telefone> telefones;
    private List<Email> emails;

    // Construtor padrão necessário para deserialização JSON
    public Cliente() {}

    // --- MÉTODOS DE CONVERSÃO ---

    /**
     * Converte um ClienteDTO para a entidade Cliente.
     * @param dto O DTO a ser convertido
     * @return A entidade Cliente correspondente
     */
    public static Cliente fromDTO(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }
        Cliente entity = new Cliente();
        entity.setId(dto.id());
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());

        // Converte EnderecoDTO para Endereco
        if (dto.endereco() != null) {
            entity.setEndereco(Endereco.fromDTO(dto.endereco()));
        } else {
            entity.setEndereco(null);
        }

        // Converte List<TelefoneDTO> para List<Telefone>
        entity.setTelefones((dto.telefones() != null) ?
            dto.telefones().stream()
                .map(Telefone::fromDTO)
                .collect(Collectors.toList()) :
            Collections.emptyList());

        // Converte List<EmailDTO> para List<Email>
        entity.setEmails((dto.emails() != null) ?
            dto.emails().stream()
                .map(Email::fromDTO)
                .collect(Collectors.toList()) :
            Collections.emptyList());

        return entity;
    }

    /**
     * Converte a entidade Cliente para um ClienteDTO.
     * @return O DTO correspondente
     */
    public ClienteDTO toDTO() {
        // Converte Endereco para EnderecoDTO
        EnderecoDTO enderecoDto = null;
        if (this.endereco != null) {
            enderecoDto = this.endereco.toDTO();
        }

        // Converte List<Telefone> para List<TelefoneDTO>
        List<TelefoneDTO> telefonesDto = (this.telefones != null) ?
            this.telefones.stream()
                .map(Telefone::toDTO)
                .collect(Collectors.toList()) :
            Collections.emptyList();

        // Converte List<Email> para List<EmailDTO>
        List<EmailDTO> emailsDto = (this.emails != null) ?
            this.emails.stream()
                .map(Email::toDTO)
                .collect(Collectors.toList()) :
            Collections.emptyList();

        return new ClienteDTO(
            this.id,
            this.nome,
            this.cpf,
            enderecoDto,
            telefonesDto,
            emailsDto
        );
    }

    // --- GETTERS E SETTERS ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
}