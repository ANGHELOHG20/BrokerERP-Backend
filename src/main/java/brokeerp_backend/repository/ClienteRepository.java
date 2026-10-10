package brokeerp_backend.repository;

import brokeerp_backend.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByDni(String dni);

    Optional<Cliente> findByRuc(String ruc);

    boolean existsByDni(String dni);

    boolean existsByRuc(String ruc);

    List<Cliente> findByActivoTrue();

    @Query("SELECT c FROM Cliente c WHERE " +
            "LOWER(c.razonSocial) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
            "LOWER(c.nombres) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
            "LOWER(c.apellidos) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
            "c.dni LIKE CONCAT('%', :filtro, '%') OR " +
            "c.ruc LIKE CONCAT('%', :filtro, '%')")
    List<Cliente> buscarPorFiltro(@Param("filtro") String filtro);
}