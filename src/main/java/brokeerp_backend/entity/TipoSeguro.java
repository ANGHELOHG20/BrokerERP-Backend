package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipos_seguro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoSeguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_seguro")
    private Integer idTipoSeguro;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    private String descripcion;

    @Column(name = "requiere_vehiculo")
    private Boolean requiereVehiculo = false;

    @Column(name = "es_sctr")
    private Boolean esSctr = false;

    private Boolean activo = true;
}