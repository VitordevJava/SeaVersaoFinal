package br.com.seatecnologia.hub.persistence.api.repository;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import java.util.List;
import java.util.Optional;

/**
  Interface para operações de repositório de Cliente.
  Esta interface define o contrato para acessar e manipular dados de Cliente.
  É implementada por classes de repositório concretas na camada de persistência.
 */
public interface ClienteRepository {

    // Salva um cliente no repositório.
    ClienteDTO salvar(ClienteDTO cliente);

    // Lista todos os clientes no repositório.
     List<ClienteDTO> listarTodos();

    // Busca um cliente pelo seu ID.
	// parametro id O ID do cliente a ser encontrado
	// retorna Um Optional contendo o cliente se encontrado, ou vazio se não encontrado
	Optional<ClienteDTO> buscarPorId(Long id);

 // Remove um cliente pelo seu ID.
    void deletarPorId(Long id);
}