package brokeerp_backend.repository;

import brokeerp_backend.entity.Aseguradora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AseguradoraRepository extends JpaRepository<Aseguradora, Integer> {
    Optional<Aseguradora> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<Aseguradora> findByActivoTrue();
}