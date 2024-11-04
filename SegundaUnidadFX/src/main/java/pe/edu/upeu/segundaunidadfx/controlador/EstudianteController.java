package pe.edu.upeu.segundaunidadfx.controlador;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.segundaunidadfx.modelo.Estudiante;
import pe.edu.upeu.segundaunidadfx.servicio.EstudianteService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EstudianteController {

    @FXML
    private TextField txtNombre, txtApellido, txtEdad, txtDocumentoIdentidad, txtDireccion, txtEmail, txtTelefono, txtFiltroDato;
    @FXML
    private ComboBox<String> cbxNivelIdioma;
    @FXML
    private TableView<Estudiante> tableViewEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> colNombre, colApellido, colDocumentoIdentidad, colDireccion, colEmail, colTelefono, colNivel;
    @FXML
    private TableColumn<Estudiante, Integer> colEdad;
    @FXML
    private Label lbnMsg;
    @FXML
    private AnchorPane miContenedor;
    private Stage stage;

    @Autowired
    private EstudianteService estudianteService;

    private Validator validator;
    private ObservableList<Estudiante> listaEstudiantes;
    private Estudiante formulario;
    private Long idEstudianteCE = 0L;


    @FXML
    private TableColumn<Estudiante, Void> colAccion;

    public void initialize() {
        cbxNivelIdioma.getItems().addAll("Básico", "Intermedio", "Avanzado");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Configurar columnas de la tabla para usar los atributos directamente
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        colEdad.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEdad()).asObject());
        colDocumentoIdentidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocumentoIdentidad()));
        colDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colNivel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNivelIdioma()));

        // Añadir la columna de acciones
        addButtonToTable();
        listar();

        // Configurar el evento para seleccionar un estudiante en la tabla y cargar sus datos en el formulario
        tableViewEstudiantes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedEstudiante) -> {
            if (selectedEstudiante != null) {
                editForm(selectedEstudiante);
            }
        });
    }

    private void addButtonToTable() {
        colAccion.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button("Editar");
            private final Button btnDelete = new Button("Eliminar");
            private final HBox pane = new HBox(btnEdit, btnDelete);

            {
                btnEdit.setOnAction(event -> {
                    Estudiante estudiante = getTableView().getItems().get(getIndex());
                    editForm(estudiante);
                });

                btnDelete.setOnAction(event -> {
                    Estudiante estudiante = getTableView().getItems().get(getIndex());
                    estudianteService.delete(estudiante.getIdEstudiante());
                    listar();
                    lbnMsg.setText("Estudiante eliminado correctamente.");
                    lbnMsg.setStyle("-fx-text-fill: green;");
                });

                pane.setSpacing(5);
                pane.setPadding(new Insets(5, 0, 5, 0));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }

   public void listar() {
        try {
            tableViewEstudiantes.getItems().clear();
            listaEstudiantes = FXCollections.observableArrayList(estudianteService.list());
            tableViewEstudiantes.setItems(listaEstudiantes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void limpiarError() {
        txtNombre.getStyleClass().remove("text-field-error");
        txtApellido.getStyleClass().remove("text-field-error");
        txtEdad.getStyleClass().remove("text-field-error");
        txtDocumentoIdentidad.getStyleClass().remove("text-field-error");
        txtDireccion.getStyleClass().remove("text-field-error");
        txtEmail.getStyleClass().remove("text-field-error");
        txtTelefono.getStyleClass().remove("text-field-error");
        cbxNivelIdioma.getStyleClass().remove("text-field-error");
    }

    public void clearForm() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtDocumentoIdentidad.setText("");
        txtDireccion.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        cbxNivelIdioma.getSelectionModel().clearSelection();
        idEstudianteCE = 0L;
        limpiarError();
    }

    @FXML
    public void cancelarAccion() {
        clearForm();
        limpiarError();
        tableViewEstudiantes.getSelectionModel().clearSelection();  // Deselecciona la fila en la tabla
    }

    private void validarCampos(Set<ConstraintViolation<Estudiante>> violaciones) {
        violaciones.forEach(violacion -> {
            String campo = violacion.getPropertyPath().toString();
            switch (campo) {
                case "nombre" -> txtNombre.getStyleClass().add("text-field-error");
                case "apellido" -> txtApellido.getStyleClass().add("text-field-error");
                case "edad" -> txtEdad.getStyleClass().add("text-field-error");
                case "documentoIdentidad" -> txtDocumentoIdentidad.getStyleClass().add("text-field-error");
                case "direccion" -> txtDireccion.getStyleClass().add("text-field-error");
                case "email" -> txtEmail.getStyleClass().add("text-field-error");
                case "telefono" -> txtTelefono.getStyleClass().add("text-field-error");
                case "nivelIdioma" -> cbxNivelIdioma.getStyleClass().add("text-field-error");
            }
            lbnMsg.setText(violacion.getMessage());
            lbnMsg.setStyle("-fx-text-fill: red;");
        });
    }

    @FXML
    public void guardarEstudiante() {
        formulario = new Estudiante();
        formulario.setNombre(txtNombre.getText().trim());
        formulario.setApellido(txtApellido.getText().trim());
        formulario.setEdad(Integer.parseInt(txtEdad.getText().isEmpty() ? "0" : txtEdad.getText().trim()));
        formulario.setDocumentoIdentidad(txtDocumentoIdentidad.getText().trim());
        formulario.setDireccion(txtDireccion.getText().trim());
        formulario.setEmail(txtEmail.getText().trim());
        formulario.setTelefono(txtTelefono.getText().trim());
        formulario.setNivelIdioma(cbxNivelIdioma.getValue());

        Set<ConstraintViolation<Estudiante>> violaciones = validator.validate(formulario);

        if (violaciones.isEmpty()) {
            lbnMsg.setText("Formulario válido");
            lbnMsg.setStyle("-fx-text-fill: green;");
            limpiarError();

            if (idEstudianteCE != 0L) {  // Si el estudiante tiene un ID asignado, actualizamos
                formulario.setIdEstudiante(idEstudianteCE);
                estudianteService.update(formulario);
                lbnMsg.setText("Estudiante actualizado correctamente.");
            } else {  // Si el estudiante no tiene ID, es un nuevo registro
                estudianteService.save(formulario);
                lbnMsg.setText("Estudiante guardado correctamente.");
            }
            clearForm();
            listar();
        } else {
            validarCampos(violaciones);
        }
    }

    @FXML
    private void filtrarEstudiantes() {
        String filtro = txtFiltroDato.getText().toLowerCase();
        List<Estudiante> estudiantesFiltrados = listaEstudiantes.stream()
                .filter(est -> est.getNombre().toLowerCase().contains(filtro)
                        || est.getApellido().toLowerCase().contains(filtro)
                        || est.getNivelIdioma().toLowerCase().contains(filtro)
                        || est.getDocumentoIdentidad().toLowerCase().contains(filtro)
                        || est.getDireccion().toLowerCase().contains(filtro)
                        || est.getEmail().toLowerCase().contains(filtro)
                        || est.getTelefono().toLowerCase().contains(filtro))
                .collect(Collectors.toList());
        tableViewEstudiantes.setItems(FXCollections.observableArrayList(estudiantesFiltrados));
    }

    public void editForm(Estudiante estudiante) {
        txtNombre.setText(estudiante.getNombre());
        txtApellido.setText(estudiante.getApellido());
        txtEdad.setText(String.valueOf(estudiante.getEdad()));
        txtDocumentoIdentidad.setText(estudiante.getDocumentoIdentidad());
        txtDireccion.setText(estudiante.getDireccion());
        txtEmail.setText(estudiante.getEmail());
        txtTelefono.setText(estudiante.getTelefono());
        cbxNivelIdioma.setValue(estudiante.getNivelIdioma());
        idEstudianteCE = estudiante.getIdEstudiante();
        limpiarError();
    }
}
