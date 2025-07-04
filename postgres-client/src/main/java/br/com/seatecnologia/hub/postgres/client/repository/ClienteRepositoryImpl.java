package br.com.seatecnologia.hub.postgres.client.repository;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.persistence.api.repository.ClienteRepository;
import br.com.seatecnologia.hub.postgres.client.entity.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
  Implementação da interface ClienteRepository que usa JPA para acessar o banco de dados.
  Esta classe atua como um adaptador entre a API de persistência e o repositório JPA real.
 */
@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository clienteJpaRepository;

    @Autowired
    public ClienteRepositoryImpl(ClienteJpaRepository clienteJpaRepository) {
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public ClienteDTO salvar(ClienteDTO dto) {
        Cliente entity = Cliente.fromDTO(dto);
        Cliente saved = clienteJpaRepository.save(entity);
        return saved.toDTO();
    }

    @Override
    public List<ClienteDTO> listarTodos() {
        return clienteJpaRepository.findAll()
                .stream()
                .map(Cliente::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clienteJpaRepository.findById(id)
                .map(Cliente::toDTO);
    }

    @Override
    public void deletarPorId(Long id) {
        clienteJpaRepository.deleteById(id);
    }
}
