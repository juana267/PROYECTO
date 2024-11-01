package pe.edu.upeu.segundaunidadfx.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String password;

    @Transient // Este campo no se almacenará en la base de datos; solo se usará en la interfaz de JavaFX
    private ObservableList<Rol> roles;

    // Constructores
    public Usuario(Long id, String nombre, String apellido, String email, String password, ObservableList<Rol> roles) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Usuario(String nombre, String apellido, String email, String password, ObservableList<Rol> roles) {
        this(null, nombre, apellido, email, password, roles);
    }

    public Usuario() {
        this.roles = FXCollections.observableArrayList();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ObservableList<Rol> getRoles() {
        return roles;
    }

    public void setRoles(ObservableList<Rol> roles) {
        this.roles = roles;
    }
}
