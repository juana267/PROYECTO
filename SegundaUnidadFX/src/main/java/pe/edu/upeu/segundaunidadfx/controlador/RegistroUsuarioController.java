package pe.edu.upeu.segundaunidadfx.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.segundaunidadfx.modelo.Usuario;
import pe.edu.upeu.segundaunidadfx.modelo.Roles;
import pe.edu.upeu.segundaunidadfx.servicio.RolesService;
import pe.edu.upeu.segundaunidadfx.servicio.UsuariosService;

import java.io.IOException;

@Controller
public class RegistroUsuarioController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private ComboBox<String> rolComboBox;

    private UsuariosService usuarioService;
    private final RolesService rolesService;

    @Autowired
    public RegistroUsuarioController(UsuariosService usuarioService, RolesService rolesService) {
        this.usuarioService = usuarioService;
        this.rolesService = rolesService;
    }

    @FXML
    private void RegistroUsuario() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String email = emailField.getText();
        String contrasena = contrasenaField.getText();
        String rolNombre = rolComboBox.getValue();

        // Verificar si se seleccionó un rol
        if (rolNombre == null) {
            showAlert("Error de Registro", "Debe seleccionar un rol.");
            return;
        }

        // Validar que el correo sea de @gmail.com
        if (!email.endsWith("@gmail.com")) {
            showAlert("Error de Registro", "El email debe terminar en @gmail.com.");
            return;
        }

        // Obtener el rol
        Roles rol = rolesService.getRoleByName(rolNombre);
        if (rol == null) {
            showAlert("Error de Registro", "Rol no encontrado.");
            return;
        }

        // Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setContrasena(contrasena);
        usuario.setRol(String.valueOf(rol));

        // Guardar el usuario
        usuarioService.save(usuario);
        showAlert("Registro Exitoso", "Usuario registrado exitosamente.");
    }
    public void setUsuariosService(UsuariosService usuariosService) {
        this.usuarioService = usuariosService;
    }

    // Método para mostrar una alerta
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void cargarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) nombreField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inicio de Sesión");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



