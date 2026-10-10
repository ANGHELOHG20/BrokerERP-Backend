package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacion tipo;

    @Column(name = "id_referencia", nullable = false)
    private Integer idReferencia;

    @Column(nullable = false, length = 255)
    private String mensaje;

    private Boolean leida = false;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.leida == null)
            this.leida = false;
    }

    public enum TipoNotificacion {
        RENOVACION, CUOTA_VENCIDA, CUOTA_POR_VENCER, POLIZA_VENCIDA
    }
}