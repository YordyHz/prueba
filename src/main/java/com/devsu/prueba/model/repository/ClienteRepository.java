package com.devsu.prueba.model.repository;

import com.devsu.prueba.model.entity.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByIdAndEstadoTrue(Long clienteId);

    List<Cliente> findByEstadoTrue();
}
