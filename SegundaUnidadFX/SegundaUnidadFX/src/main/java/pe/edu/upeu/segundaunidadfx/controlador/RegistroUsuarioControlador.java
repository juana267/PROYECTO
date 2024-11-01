package pe.edu.upeu.segundaunidadfx.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.segundaunidadfx.dto.UsuarioRegistroDTO;
import pe.edu.upeu.segundaunidadfx.servicio.UsuarioServicio;

import java.io.IOException;

@Component
public class RegistroUsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnIniciarSesion; // Botón de iniciar sesión

    // Método para manejar el registro del usuario
    @FXML
    public void registrarUsuario() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        // Validación básica
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        // Crear el DTO para el nuevo usuario
        UsuarioRegistroDTO nuevoUsuario = new UsuarioRegistroDTO(nombre, apellido, email, password);

        // Guardar el usuario utilizando el servicio
        usuarioServicio.guardar(nuevoUsuario);
        mostrarAlerta("Éxito", "Usuario registrado con éxito", Alert.AlertType.INFORMATION);

        // Limpiar campos
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtPassword.clear();
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Método para manejar el inicio de sesión y abrir la ventana de login
    @FXML
    private void iniciarSesion() {
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage loginStage = new Stage();
            loginStage.setScene(loginScene);
            loginStage.setTitle("Iniciar Sesión");
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de inicio de sesión", Alert.AlertType.ERROR);
        }
    }
}