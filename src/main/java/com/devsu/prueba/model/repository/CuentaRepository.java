package com.devsu.prueba.model.repository;

import com.devsu.prueba.model.entity.Cuenta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByIdAndEstadoTrue(Long id);

    List<Cuenta> findByEstadoTrue();
}
