package br.com.seatecnologia.hub.json.client.repository;

import br.com.seatecnologia.hub.core.dto.ClienteDTO;
import br.com.seatecnologia.hub.json.client.entity.Cliente;
import br.com.seatecnologia.hub.persistence.api.repository.ClienteRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementação da interface ClienteRepository que usa arquivos JSON para persistência.
 * Esta classe salva e carrega dados de clientes de um arquivo JSON.
 */
@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final String dataFolder;
    private final String dataFile;
    private final ObjectMapper objectMapper;
    private final AtomicLong idSequence;

    /**
     * Construtor que inicializa o repositório com o caminho do arquivo JSON.
     * 
     * @param dataFolder O diretório onde o arquivo JSON será armazenado
     */
    public ClienteRepositoryImpl(
            @Value("${json.client.data.folder:./data}") String dataFolder,
            @Value("${json.client.data.file:clientes.json}") String dataFile) {
        this.dataFolder = dataFolder;
        this.dataFile = dataFile;
        this.objectMapper = new ObjectMapper();
        this.idSequence = new AtomicLong(0);
        
        // Cria o diretório de dados se não existir
        try {
            Path path = Paths.get(dataFolder);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            
            // Inicializa o arquivo JSON se não existir
            File file = new File(dataFolder, dataFile);
            if (!file.exists()) {
                saveClientes(new ArrayList<>());
            } else {
                // Encontra o maior ID para inicializar a sequência
                List<Cliente> clientes = loadClientes();
                if (!clientes.isEmpty()) {
                    long maxId = clientes.stream()
                            .map(Cliente::getId)
                            .filter(id -> id != null)
                            .mapToLong(Long::longValue)
                            .max()
                            .orElse(0);
                    idSequence.set(maxId);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao inicializar o repositório JSON", e);
        }
    }

    @Override
    public ClienteDTO salvar(ClienteDTO dto) {
        try {
            List<Cliente> clientes = loadClientes();
            
            Cliente cliente = Cliente.fromDTO(dto);
            
            if (cliente.getId() == null) {
                // Novo cliente - gera um novo ID
                cliente.setId(idSequence.incrementAndGet());
            } else {
                // Cliente existente - remove o antigo
                clientes.removeIf(c -> c.getId().equals(cliente.getId()));
            }
            
            clientes.add(cliente);
            saveClientes(clientes);
            
            return cliente.toDTO();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente no JSON", e);
        }
    }

    @Override
    public List<ClienteDTO> listarTodos() {
        try {
            return loadClientes().stream()
                    .map(Cliente::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar clientes do JSON", e);
        }
    }

    @Override
    public Optional<ClienteDTO> buscarPorId(Long id) {
        try {
            return loadClientes().stream()
                    .filter(cliente -> cliente.getId().equals(id))
                    .findFirst()
                    .map(Cliente::toDTO);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente por ID no JSON", e);
        }
    }

    @Override
    public void deletarPorId(Long id) {
        try {
            List<Cliente> clientes = loadClientes();
            clientes.removeIf(cliente -> cliente.getId().equals(id));
            saveClientes(clientes);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cliente por ID no JSON", e);
        }
    }

    /**
     * Carrega a lista de clientes do arquivo JSON.
     * 
     * @return Lista de clientes
     * @throws IOException Se ocorrer um erro ao ler o arquivo
     */
    private List<Cliente> loadClientes() throws IOException {
        File file = new File(dataFolder, dataFile);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        
        return objectMapper.readValue(file, new TypeReference<List<Cliente>>() {});
    }

    /**
     * Salva a lista de clientes no arquivo JSON.
     * 
     * @param clientes Lista de clientes a ser salva
     * @throws IOException Se ocorrer um erro ao escrever no arquivo
     */
    private void saveClientes(List<Cliente> clientes) throws IOException {
        File file = new File(dataFolder, dataFile);
        objectMapper.writeValue(file, clientes);
    }
}