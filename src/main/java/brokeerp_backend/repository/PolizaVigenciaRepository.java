package brokeerp_backend.repository;

import brokeerp_backend.entity.PolizaVigencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PolizaVigenciaRepository extends JpaRepository<PolizaVigencia, Integer> {
    List<PolizaVigencia> findByPolizaIdPoliza(Integer idPoliza);

    @Query("SELECT pv FROM PolizaVigencia pv WHERE pv.fechaFin BETWEEN :desde AND :hasta " +
            "AND pv.estado = 'VIGENTE' ORDER BY pv.fechaFin ASC")
    List<PolizaVigencia> findPorVencerEntre(@Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);

    @Query("SELECT pv FROM PolizaVigencia pv WHERE pv.fechaFin < :hoy " +
            "AND pv.estado = 'VIGENTE'")
    List<PolizaVigencia> findVencidas(@Param("hoy") LocalDate hoy);
}