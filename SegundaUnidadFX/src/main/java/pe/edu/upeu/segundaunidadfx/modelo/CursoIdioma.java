package pe.edu.upeu.segundaunidadfx.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "upeu_curso_idioma")
public class CursoIdioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso_idioma")
    private Long idCursoIdioma;

    @NotNull(message = "El nombre del curso no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "El nivel del curso no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nivel debe tener entre 2 y 50 caracteres")
    @Column(name = "nivel", nullable = false, length = 50)
    private String nivel;

    @NotNull(message = "La duración del curso no puede estar vacía")
    @Size(max = 20, message = "La duración debe tener hasta 20 caracteres")
    @Column(name = "duracion", nullable = false, length = 20)
    private String duracion;

    @NotNull(message = "El costo del curso no puede estar vacío")
    @Column(name = "costo", nullable = false)
    private Double costo;
}
