package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente", nullable = false)
    private TipoCliente tipoCliente;

    // Persona Natural
    @Column(unique = true, length = 15)
    private String dni;
    private String nombres;
    private String apellidos;

    // Persona Jurídica
    @Column(unique = true, length = 15)
    private String ruc;
    @Column(name = "razon_social", length = 200)
    private String razonSocial;
    @Column(name = "direccion_fiscal")
    private String direccionFiscal;
    @Column(name = "giro_actividad")
    private String giroActividad;
    @Column(name = "representante_legal")
    private String representanteLegal;

    // Comunes
    private String telefono;
    private String email;
    private String direccion;
    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculos;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Poliza> polizas;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.activo == null)
            this.activo = true;
    }

    public enum TipoCliente {
        NATURAL, JURIDICO
    }
}