package br.com.seatecnologia.hub.service.api.service;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import java.util.List;
import java.util.Optional;

// Interface de serviço com operações de negócio para Cliente
public interface ClienteService {

    // Cria um novo cliente
    ClienteDTO criar(ClienteDTO cliente);

    // Atualiza um cliente existente
    ClienteDTO atualizar(Long id, ClienteDTO cliente);

    // Lista todos os clientes
    List<ClienteDTO> listarTodos();

    // Busca um cliente pelo ID
    Optional<ClienteDTO> buscarPorId(Long id);

    // Remove um cliente pelo ID
    void deletar(Long id);
}
