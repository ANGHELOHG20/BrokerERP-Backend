package brokeerp_backend.repository;

import brokeerp_backend.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    Optional<Vehiculo> findByPlaca(String placa);

    boolean existsByPlaca(String placa);

    List<Vehiculo> findByClienteIdCliente(Integer idCliente);

    List<Vehiculo> findByActivoTrue();
}