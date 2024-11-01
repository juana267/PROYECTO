package pe.edu.upeu.segundaunidadfx.controlador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.segundaunidadfx.modelo.Usuario;
import pe.edu.upeu.segundaunidadfx.servicio.UsuarioServicio;

@Controller
public class RegistroControlador {

    @Autowired
    private UsuarioServicio servicio;

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, Long> columnaId;

    @FXML
    private TableColumn<Usuario, String> columnaNombre;

    @FXML
    private TableColumn<Usuario, String> columnaApellido;

    @FXML
    private TableColumn<Usuario, String> columnaEmail;

    // Método para inicializar el controlador de JavaFX
    @FXML
    public void initialize() {
        // Configuración de las columnas
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Cargar datos en la tabla
        cargarDatosEnTabla();
    }

    // Método para cargar los datos de usuarios en la tabla
    private void cargarDatosEnTabla() {
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList(servicio.listarUsuarios());
        tablaUsuarios.setItems(usuarios);
    }
}
