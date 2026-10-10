package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "polizas", uniqueConstraints = @UniqueConstraint(columnNames = { "numero_poliza", "id_aseguradora" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_poliza")
    private Integer idPoliza;

    @Column(name = "numero_poliza", nullable = false, length = 50)
    private String numeroPoliza;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aseguradora", nullable = false)
    private Aseguradora aseguradora;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_seguro", nullable = false)
    private TipoSeguro tipoSeguro;

    // Campos exclusivos SCTR
    @Column(name = "numero_poliza_salud", length = 50)
    private String numeroPolizaSalud;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aseguradora_salud_id")
    private Aseguradora aseguradoraSalud;

    @Column(name = "numero_poliza_pension", length = 50)
    private String numeroPolizaPension;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aseguradora_pension_id")
    private Aseguradora aseguradoraPension;

    @Enumerated(EnumType.STRING)
    private Moneda moneda = Moneda.PEN;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_general")
    private EstadoGeneral estadoGeneral = EstadoGeneral.VIGENTE;

    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PolizaVigencia> vigencias;

    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PolizaVehiculo> polizaVehiculos;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        if (this.activo == null)
            this.activo = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public enum Moneda {
        PEN, USD
    }

    public enum EstadoGeneral {
        VIGENTE, POR_VENCER, VENCIDA, CANCELADA, RENOVADA
    }
}