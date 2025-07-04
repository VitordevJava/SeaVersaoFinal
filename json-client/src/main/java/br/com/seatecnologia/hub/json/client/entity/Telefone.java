package br.com.seatecnologia.hub.json.client.entity;

import br.com.seatecnologia.hub.core.dto.TelefoneDTO;
import br.com.seatecnologia.hub.core.dto.TipoTelefone;

/**
 * Entidade Telefone para persistência em JSON.
 * Esta classe representa um telefone no sistema e é usada para serialização/deserialização JSON.
 */
public class Telefone {
    private TipoTelefone tipo;
    private String numero;

    // Construtor padrão necessário para deserialização JSON
    public Telefone() {}

    /**
     * Converte um TelefoneDTO para a entidade Telefone.
     * @param dto O DTO a ser convertido
     * @return A entidade Telefone correspondente
     */
    public static Telefone fromDTO(TelefoneDTO dto) {
        if (dto == null) {
            return null;
        }
        Telefone entity = new Telefone();
        entity.setTipo(dto.tipo());
        entity.setNumero(dto.numero());
        return entity;
    }

    /**
     * Converte a entidade Telefone para um TelefoneDTO.
     * @return O DTO correspondente
     */
    public TelefoneDTO toDTO() {
        return new TelefoneDTO(this.tipo, this.numero);
    }

    // --- GETTERS E SETTERS ---
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
}