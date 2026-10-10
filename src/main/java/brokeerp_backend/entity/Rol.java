package brokeerp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "nombre_rol", nullable = false, unique = true, length = 50)
    private String nombreRol;

    @Column(length = 200)
    private String descripcion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}