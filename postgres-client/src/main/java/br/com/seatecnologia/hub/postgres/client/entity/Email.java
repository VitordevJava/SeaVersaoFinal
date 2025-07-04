package br.com.seatecnologia.hub.postgres.client.entity;

import br.com.seatecnologia.hub.core.dto.EmailDTO;
import jakarta.persistence.*;

@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    // Construtor padrão exigido pelo JPA
    public Email() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Método para converter EmailDTO para a entidade Email (fromDTO)
    public static Email fromDTO(EmailDTO dto) {
        if (dto == null) {
            return null;
        }
        Email entity = new Email();
        // Não definimos o ID aqui, pois ele é gerado pelo banco para novas entidades
        entity.setEmail(dto.email());
        return entity;
    }

    // Método para converter entidade Email para EmailDTO (toDTO)
    public EmailDTO toDTO() {
        return new EmailDTO(this.email);
    }
}
