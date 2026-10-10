package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "aseguradoras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aseguradora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aseguradora")
    private Integer idAseguradora;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "contacto_comercial", length = 150)
    private String contactoComercial;

    private String telefono;
    private String email;
    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "aseguradora", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AseguradoraEjecutivo> ejecutivos;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.activo == null)
            this.activo = true;
    }
}