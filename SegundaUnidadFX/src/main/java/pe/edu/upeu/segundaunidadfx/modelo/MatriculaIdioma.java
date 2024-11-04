package pe.edu.upeu.segundaunidadfx.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "upeu_matricula_idioma")
public class MatriculaIdioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Long idMatricula;

    @NotNull(message = "El estudiante no puede estar vacío")
    @ManyToOne
    @JoinColumn(name = "id_estudiante", referencedColumnName = "id_estudiante",
            nullable = false, foreignKey = @ForeignKey(name = "FK_ESTUDIANTE_MATRICULA"))
    private Estudiante estudiante;

    @NotNull(message = "El curso de idioma no puede estar vacío")
    @ManyToOne
    @JoinColumn(name = "id_curso_idioma", referencedColumnName = "id_curso_idioma",
            nullable = false, foreignKey = @ForeignKey(name = "FK_CURSOIDIOMA_MATRICULA"))
    private CursoIdioma cursoIdioma;

    @NotNull(message = "La fecha de inicio no puede estar vacía")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin no puede estar vacía")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotNull(message = "El nivel del curso no puede estar vacío")
    @Size(min = 1, max = 20, message = "El nivel debe tener entre 1 y 20 caracteres")
    @Column(name = "nivel", nullable = false, length = 20)
    private String nivel;

    @NotNull(message = "El estado de la matrícula no puede estar vacío")
    @Size(min = 1, max = 20, message = "El estado debe tener entre 1 y 20 caracteres")
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;
}
