package br.com.seatecnologia.hub.service.client.service;

import br.com.seatecnologia.hub.core.Util.ClientUtil;
import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import br.com.seatecnologia.hub.persistence.api.repository.ClienteRepository;
import br.com.seatecnologia.hub.service.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementação da interface ClienteService.
 * Fornece a lógica de negócio para operações de Cliente.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public ClienteDTO criar(ClienteDTO cliente) {
        if (cliente.id() != null) {
            throw new IllegalArgumentException("Novo cliente não deve ter um ID");
        }

        // Desmascara o CPF (outros campos já são desmascarados em seus DTOs)
        String cpfUnmasked = ClientUtil.unmask(cliente.cpf());
        EnderecoDTO enderecoDTO = cliente.endereco();

        // Cria um novo ClienteDTO com o CPF desmascarado para persistência
        ClienteDTO clienteParaPersistir = new ClienteDTO(
            cliente.id(),
            cliente.nome(),
            cpfUnmasked,
            enderecoDTO,
            cliente.telefones(),
            cliente.emails()
        );

        return clienteRepository.salvar(clienteParaPersistir);
    }

    @Override
    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO cliente) {
        if (clienteRepository.buscarPorId(id).isEmpty()) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }

        // Desmascara o CPF (outros campos já são desmascarados em seus DTOs)
        String cpfUnmasked = ClientUtil.unmask(cliente.cpf());
        EnderecoDTO enderecoDTO = cliente.endereco();

        // Cria um novo DTO com o ID fornecido e o CPF desmascarado para atualização
        ClienteDTO clienteToUpdate = new ClienteDTO(
            id,
            cliente.nome(),
            cpfUnmasked,
            enderecoDTO,
            cliente.telefones(),
            cliente.emails()
        );

        return clienteRepository.salvar(clienteToUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.listarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (clienteRepository.buscarPorId(id).isEmpty()) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }

        clienteRepository.deletarPorId(id);
    }
}
