package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aseguradora_ejecutivos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AseguradoraEjecutivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejecutivo")
    private Integer idEjecutivo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aseguradora", nullable = false)
    private Aseguradora aseguradora;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    private String telefono;
    private String email;

    @Column(name = "area_especialidad", length = 100)
    private String areaEspecialidad;

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