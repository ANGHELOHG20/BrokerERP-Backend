package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "poliza_vigencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolizaVigencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vigencia")
    private Integer idVigencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_poliza", nullable = false)
    private Poliza poliza;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "prima_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal primaTotal = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private EstadoVigencia estado = EstadoVigencia.VIGENTE;

    @Column(name = "fecha_renovacion")
    private LocalDate fechaRenovacion;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "vigencia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Cuota> cuotas;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public enum EstadoVigencia {
        PENDIENTE, VIGENTE, RENOVADA, VENCIDA, CANCELADA
    }
}