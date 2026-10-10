package brokeerp_backend.repository;

import brokeerp_backend.entity.AseguradoraEjecutivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AseguradoraEjecutivoRepository extends JpaRepository<AseguradoraEjecutivo, Integer> {
    List<AseguradoraEjecutivo> findByAseguradoraIdAseguradora(Integer idAseguradora);

    List<AseguradoraEjecutivo> findByAreaEspecialidad(String areaEspecialidad);

    List<AseguradoraEjecutivo> findByAseguradoraIdAseguradoraAndAreaEspecialidad(
            Integer idAseguradora, String areaEspecialidad);

    List<AseguradoraEjecutivo> findByActivoTrue();
}