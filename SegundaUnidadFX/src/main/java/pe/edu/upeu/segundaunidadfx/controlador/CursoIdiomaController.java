package pe.edu.upeu.segundaunidadfx.controlador;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.segundaunidadfx.modelo.CursoIdioma;
import pe.edu.upeu.segundaunidadfx.servicio.CursoIdiomaService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CursoIdiomaController {

    @FXML
    private TextField txtNombreCurso, txtNivel, txtDuracion, txtCosto, txtFiltroDato;
    @FXML
    private TableView<CursoIdioma> tableViewCursos;
    @FXML
    private TableColumn<CursoIdioma, String> colNombre, colNivel, colDuracion;
    @FXML
    private TableColumn<CursoIdioma, Double> colCosto;
    @FXML
    private TableColumn<CursoIdioma, Void> colAccion; // Columna para botones de acción
    @FXML
    private Label lbnMsg;
    @FXML
    private AnchorPane miContenedor;
    private Stage stage;

    @Autowired
    private CursoIdiomaService cursoIdiomaService;

    private Validator validator;
    private ObservableList<CursoIdioma> listaCursos;
    private CursoIdioma formulario;
    private Long idCursoCE = 0L;

    public void initialize() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Configurar columnas de la tabla
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colNivel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNivel()));
        colDuracion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuracion()));
        colCosto.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCosto()).asObject());

        // Añadir botones de acción a la columna de acción
        addButtonToTable();
        listar();
    }

    public void listar() {
        try {
            tableViewCursos.getItems().clear();
            listaCursos = FXCollections.observableArrayList(cursoIdiomaService.list());
            tableViewCursos.setItems(listaCursos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void limpiarError() {
        txtNombreCurso.getStyleClass().remove("text-field-error");
        txtNivel.getStyleClass().remove("text-field-error");
        txtDuracion.getStyleClass().remove("text-field-error");
        txtCosto.getStyleClass().remove("text-field-error");
    }

    public void clearForm() {
        txtNombreCurso.setText("");
        txtNivel.setText("");
        txtDuracion.setText("");
        txtCosto.setText("");
        idCursoCE = 0L;
        limpiarError();
    }

    @FXML
    public void cancelarAccion() {
        clearForm();
        limpiarError();
        tableViewCursos.getSelectionModel().clearSelection();  // Deselecciona la fila en la tabla
    }

    private void validarCampos(Set<ConstraintViolation<CursoIdioma>> violaciones) {
        violaciones.forEach(violacion -> {
            String campo = violacion.getPropertyPath().toString();
            switch (campo) {
                case "nombre" -> txtNombreCurso.getStyleClass().add("text-field-error");
                case "nivel" -> txtNivel.getStyleClass().add("text-field-error");
                case "duracion" -> txtDuracion.getStyleClass().add("text-field-error");
                case "costo" -> txtCosto.getStyleClass().add("text-field-error");
            }
            lbnMsg.setText(violacion.getMessage());
            lbnMsg.setStyle("-fx-text-fill: red;");
        });
    }

    @FXML
    public void guardarCurso() {
        formulario = new CursoIdioma();
        formulario.setNombre(txtNombreCurso.getText().trim());
        formulario.setNivel(txtNivel.getText().trim());
        formulario.setDuracion(txtDuracion.getText().trim());
        formulario.setCosto(Double.parseDouble(txtCosto.getText().isEmpty() ? "0" : txtCosto.getText().trim()));

        Set<ConstraintViolation<CursoIdioma>> violaciones = validator.validate(formulario);

        if (violaciones.isEmpty()) {
            lbnMsg.setText("Formulario válido");
            lbnMsg.setStyle("-fx-text-fill: green;");
            limpiarError();

            if (idCursoCE != 0L) {  // Si el curso tiene un ID asignado, actualizamos
                formulario.setIdCursoIdioma(idCursoCE);
                cursoIdiomaService.update(formulario);
                lbnMsg.setText("Curso actualizado correctamente.");
            } else {  // Si el curso no tiene ID, es un nuevo registro
                cursoIdiomaService.save(formulario);
                lbnMsg.setText("Curso guardado correctamente.");
            }
            clearForm();
            listar();
        } else {
            validarCampos(violaciones);
        }
    }

    private void addButtonToTable() {
        Callback<TableColumn<CursoIdioma, Void>, TableCell<CursoIdioma, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEdit = new Button("Editar");
            private final Button btnDelete = new Button("Eliminar");
            private final HBox pane = new HBox(btnEdit, btnDelete);

            {
                btnEdit.setOnAction(event -> {
                    CursoIdioma curso = getTableView().getItems().get(getIndex());
                    editForm(curso);
                });

                btnDelete.setOnAction(event -> {
                    CursoIdioma curso = getTableView().getItems().get(getIndex());
                    cursoIdiomaService.delete(curso.getIdCursoIdioma());
                    listar();
                    lbnMsg.setText("Curso eliminado correctamente.");
                    lbnMsg.setStyle("-fx-text-fill: green;");
                });

                pane.setSpacing(5);
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
        };

        colAccion.setCellFactory(cellFactory);
    }

    public void editForm(CursoIdioma curso) {
        txtNombreCurso.setText(curso.getNombre());
        txtNivel.setText(curso.getNivel());
        txtDuracion.setText(curso.getDuracion());
        txtCosto.setText(String.valueOf(curso.getCosto()));
        idCursoCE = curso.getIdCursoIdioma();
        limpiarError();
    }
}
