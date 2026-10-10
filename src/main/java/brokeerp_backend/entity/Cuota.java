package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuotas", uniqueConstraints = @UniqueConstraint(columnNames = { "id_vigencia", "numero_cuota" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuota")
    private Integer idCuota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vigencia", nullable = false)
    private PolizaVigencia vigencia;

    @Column(name = "numero_cuota", nullable = false)
    private Integer numeroCuota;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal importe;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Enumerated(EnumType.STRING)
    private EstadoCuota estado = EstadoCuota.PENDIENTE;

    @Column(name = "numero_factura", length = 50)
    private String numeroFactura;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public enum EstadoCuota {
        PENDIENTE, PAGADA, VENCIDA
    }
}