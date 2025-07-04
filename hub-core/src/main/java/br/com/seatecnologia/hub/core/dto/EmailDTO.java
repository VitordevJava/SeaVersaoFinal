package br.com.seatecnologia.hub.core.dto;

import br.com.seatecnologia.hub.core.validation.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para representação de um email.
 * <p>
 * Contém o endereço de email que deve ter um formato válido.
 */
public record EmailDTO(
    @NotBlank(message = "O email é obrigatório")
    @Email
    String email
) {}
