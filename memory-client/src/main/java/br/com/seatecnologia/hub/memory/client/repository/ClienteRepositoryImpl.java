package br.com.seatecnologia.hub.memory.client.repository;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.persistence.api.repository.ClienteRepository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementação da interface ClienteRepository que armazena dados em memória.
 * Esta classe mantém os dados de clientes em um mapa na memória RAM.
 */
@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final Map<Long, ClienteDTO> clientesMap = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public ClienteDTO salvar(ClienteDTO cliente) {
        ClienteDTO clienteToSave;
        
        if (cliente.id() == null) {
            // Novo cliente - gera um novo ID
            Long newId = idSequence.incrementAndGet();
            clienteToSave = new ClienteDTO(
                newId,
                cliente.nome(),
                cliente.cpf(),
                cliente.endereco(),
                cliente.telefones(),
                cliente.emails()
            );
        } else {
            // Cliente existente - mantém o ID
            clienteToSave = cliente;
        }
        
        clientesMap.put(clienteToSave.id(), clienteToSave);
        return clienteToSave;
    }

    @Override
    public List<ClienteDTO> listarTodos() {
        return new ArrayList<>(clientesMap.values());
    }

    @Override
    public Optional<ClienteDTO> buscarPorId(Long id) {
        return Optional.ofNullable(clientesMap.get(id));
    }

    @Override
    public void deletarPorId(Long id) {
        clientesMap.remove(id);
    }
}