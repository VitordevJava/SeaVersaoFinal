package br.com.seatecnologia.hub.postgres.client.entity;

import jakarta.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// Importar o ClienteDTO para uso nos métodos de conversão
import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.core.dto.EmailDTO;
import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
// Não precisamos importar EnderecoDTO, TelefoneDTO, EmailDTO aqui,
// pois as entidades Endereco, Telefone e Email já têm seus próprios métodos toDTO/fromDTO
import br.com.seatecnologia.hub.core.dto.TelefoneDTO;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    // @JoinColumn é para relacionamentos uni-direcionais no lado "Many" ou no "One" em OneToMany
    // Se você quiser um relacionamento bidirecional, use mappedBy no Cliente
    // Caso contrário, @JoinColumn aqui é para uma tabela de junção implícita ou coluna no lado "Many"
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_id") // Esta anotação cria uma coluna cliente_id na tabela Telefone
    private List<Telefone> telefones;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_id") // Esta anotação cria uma coluna cliente_id na tabela Email
    private List<Email> emails;

    // Construtor padrão exigido pelo JPA
    public Cliente() {}

    // --- MÉTODOS DE CONVERSÃO DENTRO DA ENTIDADE ---

    // Método para converter ClienteDTO para a entidade Cliente (fromDTO)
    // Este método é estático porque cria uma nova instância da entidade.
    public static Cliente fromDTO(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }
        Cliente entity = new Cliente();
        entity.setId(dto.id()); // O ID pode vir do DTO se for uma atualização
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());

        // Converte EnderecoDTO para Endereco usando o fromDTO da entidade Endereco
        if (dto.endereco() != null) {
            entity.setEndereco(Endereco.fromDTO(dto.endereco()));
        } else {
            entity.setEndereco(null);
        }

        // Converte List<TelefoneDTO> para List<Telefone> usando o fromDTO da entidade Telefone
        entity.setTelefones((dto.telefones() != null) ?
            dto.telefones().stream()
                .map(Telefone::fromDTO)
                .collect(Collectors.toList()) :
            Collections.emptyList());

        // Converte List<EmailDTO> para List<Email> usando o fromDTO da entidade Email
        entity.setEmails((dto.emails() != null) ?
            dto.emails().stream()
                .map(Email::fromDTO)
                .collect(Collectors.toList()) :
            Collections.emptyList());

        return entity;
    }

    // Método para converter a entidade Cliente para ClienteDTO (toDTO)
    // Este método é de instância porque opera sobre uma instância existente da entidade.
    public ClienteDTO toDTO() {
        // Converte Endereco para EnderecoDTO usando o toDTO da entidade Endereco
        EnderecoDTO enderecoDto = null;
        if (this.endereco != null) {
            enderecoDto = this.endereco.toDTO();
        }

        // Converte List<Telefone> para List<TelefoneDTO> usando o toDTO da entidade Telefone
        List<TelefoneDTO> telefonesDto = (this.telefones != null) ?
            this.telefones.stream()
                .map(Telefone::toDTO) // Aqui usamos o método de instância toDTO do Telefone
                .collect(Collectors.toList()) :
            Collections.emptyList();

        // Converte List<Email> para List<EmailDTO> usando o toDTO da entidade Email
        List<EmailDTO> emailsDto = (this.emails != null) ?
            this.emails.stream()
                .map(Email::toDTO) // Aqui usamos o método de instância toDTO do Email
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
