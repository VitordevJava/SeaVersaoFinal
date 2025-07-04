package br.com.seatecnologia.hub.postgres.client.repository;

import br.com.seatecnologia.hub.postgres.client.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
  Interface de repositório JPA para a entidade Cliente.
  Esta interface estende o JpaRepository do Spring Data JPA para fornecer
  operações de acesso ao banco de dados para a entidade Cliente.
 */
@Repository
public interface ClienteJpaRepository extends JpaRepository<Cliente, Long> {
}
