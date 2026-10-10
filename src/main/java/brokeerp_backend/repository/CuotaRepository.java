package brokeerp_backend.repository;

import brokeerp_backend.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Integer> {
    List<Cuota> findByVigenciaIdVigencia(Integer idVigencia);

    List<Cuota> findByEstado(Cuota.EstadoCuota estado);

    @Query("SELECT c FROM Cuota c WHERE c.estado = 'PENDIENTE' " +
            "AND c.fechaVencimiento BETWEEN :desde AND :hasta " +
            "ORDER BY c.fechaVencimiento ASC")
    List<Cuota> findPorVencerEntre(@Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);

    @Query("SELECT c FROM Cuota c WHERE c.estado = 'PENDIENTE' " +
            "AND c.fechaVencimiento < :hoy")
    List<Cuota> findVencidas(@Param("hoy") LocalDate hoy);
}