package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Integer idVehiculo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, unique = true, length = 15)
    private String placa;

    private String marca;
    private String modelo;

    @Column(name = "anio")
    private Integer anio;

    private String motor;
    private String serie;
    private String uso;
    private String clase;

    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.activo == null)
            this.activo = true;
    }
}