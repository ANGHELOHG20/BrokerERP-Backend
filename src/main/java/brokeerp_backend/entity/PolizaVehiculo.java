package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "poliza_vehiculos", uniqueConstraints = @UniqueConstraint(columnNames = { "id_poliza", "id_vehiculo" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolizaVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_poliza_vehiculo")
    private Integer idPolizaVehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_poliza", nullable = false)
    private Poliza poliza;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @Column(name = "fecha_asociacion")
    private LocalDateTime fechaAsociacion;

    @PrePersist
    public void prePersist() {
        this.fechaAsociacion = LocalDateTime.now();
    }
}