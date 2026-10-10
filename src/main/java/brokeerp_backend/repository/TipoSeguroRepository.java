package brokeerp_backend.repository;

import brokeerp_backend.entity.TipoSeguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TipoSeguroRepository extends JpaRepository<TipoSeguro, Integer> {
    Optional<TipoSeguro> findByNombre(String nombre);

    List<TipoSeguro> findByActivoTrue();

    List<TipoSeguro> findByRequiereVehiculoTrue();

    List<TipoSeguro> findByEsSctrTrue();
}