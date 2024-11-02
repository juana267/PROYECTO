package pe.edu.upeu.segundaunidadfx.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.segundaunidadfx.servicio.UsuariosService;

import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private ApplicationContext context;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField contrasenaField;

    private UsuariosService usuarioService;

    public LoginController() {
        // Constructor sin argumentos requerido por JavaFX
    }

    @Autowired
    public LoginController(UsuariosService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @FXML
    private void iniciarSesion() {
        String email = emailField.getText();
        String contrasena = contrasenaField.getText();

        if (usuarioService != null && usuarioService.validarCredenciales(email, contrasena) != null) {
            showAlert("Inicio de sesión exitoso", "Bienvenido, " + email + "!");
            cargarPantallaPrincipal();
        } else {
            showAlert("Error", "Correo o contraseña incorrectos. Si no tienes cuenta, regístrate.");
        }
    }

    private void cargarPantallaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/guimainfx.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pantalla Principal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la pantalla principal.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void irARegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registro.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registro de Usuario");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
