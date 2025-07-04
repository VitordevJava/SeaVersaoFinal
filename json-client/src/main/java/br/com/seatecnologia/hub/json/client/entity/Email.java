package br.com.seatecnologia.hub.json.client.entity;

import br.com.seatecnologia.hub.core.dto.EmailDTO;

/**
 * Entidade Email para persistência em JSON.
 * Esta classe representa um email no sistema e é usada para serialização/deserialização JSON.
 */
public class Email {
    private String email;

    // Construtor padrão necessário para deserialização JSON
    public Email() {}

    /**
     * Converte um EmailDTO para a entidade Email.
     * @param dto O DTO a ser convertido
     * @return A entidade Email correspondente
     */
    public static Email fromDTO(EmailDTO dto) {
        if (dto == null) {
            return null;
        }
        Email entity = new Email();
        entity.setEmail(dto.email());
        return entity;
    }

    /**
     * Converte a entidade Email para um EmailDTO.
     * @return O DTO correspondente
     */
    public EmailDTO toDTO() {
        return new EmailDTO(this.email);
    }

    // --- GETTERS E SETTERS ---
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}