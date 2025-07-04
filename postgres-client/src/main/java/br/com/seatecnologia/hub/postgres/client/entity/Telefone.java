package br.com.seatecnologia.hub.postgres.client.entity;

import br.com.seatecnologia.hub.core.dto.TelefoneDTO;
import br.com.seatecnologia.hub.core.dto.TipoTelefone; // Importar o Enum do DTO
import jakarta.persistence.*;

@Entity
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // Armazenar o enum como String no banco
    private TipoTelefone tipo;

    private String numero;

    // Construtor padrão exigido pelo JPA
    public Telefone() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    // Método para converter TelefoneDTO para a entidade Telefone (fromDTO)
    public static Telefone fromDTO(TelefoneDTO dto) {
        if (dto == null) {
            return null;
        }
        Telefone entity = new Telefone();
        // Não definimos o ID aqui
        entity.setTipo(dto.tipo());
        entity.setNumero(dto.numero());
        return entity;
    }

    // Método para converter entidade Telefone para TelefoneDTO (toDTO)
    public TelefoneDTO toDTO() {
        return new TelefoneDTO(this.tipo, this.numero);
    }
}
