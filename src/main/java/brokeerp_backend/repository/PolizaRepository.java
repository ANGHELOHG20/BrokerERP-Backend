package brokeerp_backend.repository;

import brokeerp_backend.entity.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Integer> {
    Optional<Poliza> findByNumeroPolizaAndAseguradoraIdAseguradora(
            String numeroPoliza, Integer idAseguradora);

    List<Poliza> findByClienteIdCliente(Integer idCliente);

    List<Poliza> findByAseguradoraIdAseguradora(Integer idAseguradora);

    List<Poliza> findByTipoSeguroIdTipoSeguro(Integer idTipoSeguro);

    List<Poliza> findByActivoTrue();

    @Query("SELECT p FROM Poliza p WHERE " +
            "LOWER(p.numeroPoliza) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Poliza> buscarPorNumero(@Param("filtro") String filtro);
}