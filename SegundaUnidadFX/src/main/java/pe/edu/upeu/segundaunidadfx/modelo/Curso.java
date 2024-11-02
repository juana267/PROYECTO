package pe.edu.upeu.segundaunidadfx.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Cursos")
@EntityListeners(AuditingEntityListener.class)
public class Curso {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @OneToMany(mappedBy = "curso")
    private Set<Nivel> cursoNiveles;

    @OneToMany(mappedBy = "curso")
    private Set<Matricula> cursoMatriculas;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public Set<Nivel> getCursoNiveles() {
        return cursoNiveles;
    }

    public void setCursoNiveles(final Set<Nivel> cursoNiveles) {
        this.cursoNiveles = cursoNiveles;
    }

    public Set<Matricula> getCursoMatriculas() {
        return cursoMatriculas;
    }

    public void setCursoMatriculas(final Set<Matricula> cursoMatriculas) {
        this.cursoMatriculas = cursoMatriculas;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
