package pe.edu.upeu.segundaunidadfx.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "upeu_estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long idEstudiante;

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @NotNull(message = "El documento de identidad no puede estar vacío")
    @Size(min = 8, max = 20, message = "El documento de identidad debe tener entre 8 y 20 caracteres")
    @Column(name = "documento_identidad", nullable = false, unique = true, length = 20)
    private String documentoIdentidad;

    @NotNull(message = "La dirección no puede estar vacía")
    @Size(max = 150, message = "La dirección debe tener hasta 150 caracteres")
    @Column(name = "direccion", length = 150)
    private String direccion;

    @NotNull(message = "El correo electrónico no puede estar vacío")
    @Email(message = "Debe proporcionar un correo electrónico válido")
    @Size(max = 100, message = "El correo electrónico debe tener hasta 100 caracteres")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 15, message = "El número de teléfono debe tener hasta 15 caracteres")
    @Column(name = "telefono", length = 15)
    private String telefono;

    @NotNull(message = "La edad no puede estar vacía")
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotNull(message = "El nivel de idioma no puede estar vacío")
    @Size(max = 50, message = "El nivel de idioma debe tener hasta 50 caracteres")
    @Column(name = "nivel_idioma", nullable = false, length = 50)
    private String nivelIdioma;
}
