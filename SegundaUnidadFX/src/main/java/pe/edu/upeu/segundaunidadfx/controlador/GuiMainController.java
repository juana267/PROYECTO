package pe.edu.upeu.segundaunidadfx.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Controller;

import java.io.IOException;


public class GuiMainController {

    @FXML
    private StackPane contenidoPane;

    @FXML
    private Menu archivoMenu;

    @FXML
    private Menu ayudaMenu;

    @FXML
    private Menu usuariosMenu;

    @FXML
    private Menu matriculasMenu;

    @FXML
    private Menu notasMenu;

    private String rolUsuario;

    public void setRolUsuario(String rol) {
        this.rolUsuario = rol;
        System.out.println("Rol recibido en setRolUsuario: " + rol); // Verificar si el rol llega
        configurarAccesos();
    }

    private void configurarAccesos() {
        System.out.println("Configurando accesos para el rol: " + rolUsuario);
        switch (rolUsuario) {
            case "1":
                archivoMenu.setVisible(true);
                ayudaMenu.setVisible(true);
                usuariosMenu.setVisible(true);
                matriculasMenu.setVisible(true);
                notasMenu.setVisible(true);
                break;
            case "2":
                archivoMenu.setVisible(true);
                ayudaMenu.setVisible(true);
                usuariosMenu.setVisible(false);
                matriculasMenu.setVisible(true);
                notasMenu.setVisible(false); // Sin acceso a notas
                break;
            case "3":
                archivoMenu.setVisible(true);
                ayudaMenu.setVisible(true);
                usuariosMenu.setVisible(false); // Sin acceso a usuarios
                matriculasMenu.setVisible(false); // Sin acceso a matrículas
                notasMenu.setVisible(true);
                break;
            default:
                archivoMenu.setVisible(false);
                ayudaMenu.setVisible(false);
                usuariosMenu.setVisible(false);
                matriculasMenu.setVisible(false);
                notasMenu.setVisible(false);
                break;
        }
        System.out.println("Configuración de accesos completada"); // Confirmación de ejecución
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
