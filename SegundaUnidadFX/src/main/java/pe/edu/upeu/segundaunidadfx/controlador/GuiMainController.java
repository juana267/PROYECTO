package pe.edu.upeu.segundaunidadfx.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class GuiMainController {

    @FXML
    private StackPane contenidoPane;

    // Cargar vista de usuarios
    @FXML
    private void mostrarUsuarios() {
        cargarVista("/view/usuarios.fxml");
    }

    // Cargar vista de gestión de matrículas
    @FXML
    private void mostrarMatriculas() {
        cargarVista("/view/matriculas.fxml");
    }

    // Cargar vista de notas
    @FXML
    private void mostrarNotas() {
        cargarVista("/view/notas.fxml");
    }

    // Cargar vista de gestión de notas
    @FXML
    private void gestionarNotas() {
        cargarVista("/view/gestionarNotas.fxml");
    }

    // Método para cargar las diferentes vistas en el StackPane
    private void cargarVista(String ruta) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource(ruta));
            contenidoPane.getChildren().setAll(vista);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista: " + ruta);
        }
    }

    // Mostrar una alerta en caso de error
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Cerrar sesión
    @FXML
    private void cerrarSesion() {
        // Implementar la lógica para cerrar sesión y regresar a la pantalla de login
    }

    // Salir de la aplicación
    @FXML
    private void salir() {
        System.exit(0);
    }

    // Mostrar información de "Acerca de"
    @FXML
    private void mostrarAcercaDe() {
        mostrarAlerta("Acerca de", "Sistema de Gestión de Usuarios y Notas v1.0");
    }
}
