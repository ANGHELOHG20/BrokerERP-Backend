package brokeerp_backend.repository;

import brokeerp_backend.entity.PolizaVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PolizaVehiculoRepository extends JpaRepository<PolizaVehiculo, Integer> {
    List<PolizaVehiculo> findByPolizaIdPoliza(Integer idPoliza);

    List<PolizaVehiculo> findByVehiculoIdVehiculo(Integer idVehiculo);

    boolean existsByPolizaIdPolizaAndVehiculoIdVehiculo(Integer idPoliza, Integer idVehiculo);
}