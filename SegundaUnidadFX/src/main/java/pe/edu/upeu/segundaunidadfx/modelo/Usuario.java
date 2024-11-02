package pe.edu.upeu.segundaunidadfx.modelo;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Usuarios")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {


    // Nombre de propiedad que coincide con el método findByEmailAndPassword
    private String password;
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String contraseA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id")
    private Roles rol;

    @OneToMany(mappedBy = "usuario")
    private Set<Matricula> usuarioMatriculas;


    // Configurar la fecha de creación para asignarse automáticamente
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(final String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getContraseA() {
        return contraseA;
    }

    public void setContraseA(final String contraseA) {
        this.contraseA = contraseA;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(final Roles rol) {
        this.rol = rol;
    }

    public Set<Matricula> getUsuarioMatriculas() {
        return usuarioMatriculas;
    }

    public void setUsuarioMatriculas(final Set<Matricula> usuarioMatriculas) {
        this.usuarioMatriculas = usuarioMatriculas;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
