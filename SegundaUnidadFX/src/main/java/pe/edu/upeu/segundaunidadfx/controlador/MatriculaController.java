package pe.edu.upeu.segundaunidadfx.controlador;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.segundaunidadfx.componente.ColumnInfo;
import pe.edu.upeu.segundaunidadfx.componente.ComboBoxAutoComplete;
import pe.edu.upeu.segundaunidadfx.componente.TableViewHelper;
import pe.edu.upeu.segundaunidadfx.componente.Toast;
import pe.edu.upeu.segundaunidadfx.dto.ComboBoxOption;
import pe.edu.upeu.segundaunidadfx.modelo.MatriculaIdioma;
import pe.edu.upeu.segundaunidadfx.servicio.CursoIdiomaService;
import pe.edu.upeu.segundaunidadfx.servicio.EstudianteService;
import pe.edu.upeu.segundaunidadfx.servicio.MatriculaIdiomaService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class MatriculaController {

    @FXML
    private TextField txtNivel, txtFiltroDato;
    @FXML
    private ComboBox<ComboBoxOption> cbxEstudiante, cbxCursoIdioma, cbxEstado;
    @FXML
    private DatePicker dateFechaInicio, dateFechaFin;
    @FXML
    private TableView<MatriculaIdioma> tableView;
    @FXML
    private Label lbnMsg;
    @FXML
    private AnchorPane miContenedor;
    private Stage stage;

    @Autowired
    private EstudianteService estudianteService;
    @Autowired
    private CursoIdiomaService cursoIdiomaService;
    @Autowired
    private MatriculaIdiomaService matriculaIdiomaService;

    private Validator validator;
    private ObservableList<MatriculaIdioma> listaMatriculas;
    private MatriculaIdioma formulario;
    private Long idMatriculaCE = 0L;

    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), event -> {
            stage = (Stage) miContenedor.getScene().getWindow();
            if (stage != null) {
                System.out.println("El título del stage es: " + stage.getTitle());
            } else {
                System.out.println("Stage aún no disponible.");
            }
        }));
        timeline.setCycleCount(1);
        timeline.play();

        // Inicializar ComboBoxes con datos y autocompletado
        cbxEstudiante.setTooltip(new Tooltip());
        cbxEstudiante.getItems().addAll(estudianteService.listarCombobox());
        new ComboBoxAutoComplete<>(cbxEstudiante);

        cbxCursoIdioma.setTooltip(new Tooltip());
        cbxCursoIdioma.getItems().addAll(cursoIdiomaService.listarCombobox());
        new ComboBoxAutoComplete<>(cbxCursoIdioma);

        cbxEstado.setTooltip(new Tooltip());
        cbxEstado.getItems().addAll(
                FXCollections.observableArrayList(
                        new ComboBoxOption("1", "Activo"),
                        new ComboBoxOption("2", "Finalizado"),
                        new ComboBoxOption("3", "Anulado")
                )
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Configuración de las columnas de la tabla
        TableViewHelper<MatriculaIdioma> tableViewHelper = new TableViewHelper<>();
        LinkedHashMap<String, ColumnInfo> columns = new LinkedHashMap<>();
        columns.put("ID Mat.", new ColumnInfo("idMatricula", 60.0));
        columns.put("Estudiante", new ColumnInfo("estudiante.nombre", 200.0));
        columns.put("Curso", new ColumnInfo("cursoIdioma.nombre", 200.0));
        columns.put("Fecha Inicio", new ColumnInfo("fechaInicio", 100.0));
        columns.put("Fecha Fin", new ColumnInfo("fechaFin", 100.0));
        columns.put("Nivel", new ColumnInfo("nivel", 100.0));
        columns.put("Estado", new ColumnInfo("estado", 100.0));

        Consumer<MatriculaIdioma> updateAction = this::editForm;
        Consumer<MatriculaIdioma> deleteAction = matricula -> {
            matriculaIdiomaService.delete(matricula.getIdMatricula());
            Toast.showToast(stage, "Se eliminó correctamente!!", 2000, stage.getWidth() / 1.5, stage.getHeight() / 2);
            listar();
        };

        tableViewHelper.addColumnsInOrderWithSize(tableView, columns, updateAction, deleteAction);
        tableView.setTableMenuButtonVisible(true);
        listar();
    }
    @FXML
    public void guardarMatricula() {
        formulario = new MatriculaIdioma();
        formulario.setNivel(txtNivel.getText());
        formulario.setFechaInicio(dateFechaInicio.getValue());
        formulario.setFechaFin(dateFechaFin.getValue());

        String estudianteId = cbxEstudiante.getSelectionModel().getSelectedItem() == null ? "0" : cbxEstudiante.getSelectionModel().getSelectedItem().getKey();
        formulario.setEstudiante(estudianteService.searchById(Long.parseLong(estudianteId)));

        String cursoIdiomaId = cbxCursoIdioma.getSelectionModel().getSelectedItem() == null ? "0" : cbxCursoIdioma.getSelectionModel().getSelectedItem().getKey();
        formulario.setCursoIdioma(cursoIdiomaService.searchById(Long.parseLong(cursoIdiomaId)));

        formulario.setEstado(cbxEstado.getSelectionModel().getSelectedItem() == null ? "" : cbxEstado.getSelectionModel().getSelectedItem().getValue());

        Set<ConstraintViolation<MatriculaIdioma>> violaciones = validator.validate(formulario);
        List<ConstraintViolation<MatriculaIdioma>> violacionesOrdenadasPorPropiedad = violaciones.stream()
                .sorted((v1, v2) -> v1.getPropertyPath().toString().compareTo(v2.getPropertyPath().toString()))
                .collect(Collectors.toList());

        if (violacionesOrdenadasPorPropiedad.isEmpty()) {
            lbnMsg.setText("Formulario válido");
            lbnMsg.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            limpiarError();

            double width = stage.getWidth() / 1.5;
            double height = stage.getHeight() / 2;

            if (idMatriculaCE != 0L) {
                formulario.setIdMatricula(idMatriculaCE);
                matriculaIdiomaService.update(formulario);
                Toast.showToast(stage, "Se actualizó correctamente!!", 2000, width, height);
            } else {
                matriculaIdiomaService.save(formulario);
                Toast.showToast(stage, "Se guardó correctamente!!", 2000, width, height);
            }
            clearForm();
            listar();
        } else {
            validarCampos(violacionesOrdenadasPorPropiedad);
        }
    }


    public void listar() {
        try {
            tableView.getItems().clear();
            listaMatriculas = FXCollections.observableArrayList(matriculaIdiomaService.list());
            tableView.getItems().addAll(listaMatriculas);
            txtFiltroDato.textProperty().addListener((observable, oldValue, newValue) -> filtrarMatriculas(newValue));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void limpiarError() {
        txtNivel.getStyleClass().remove("text-field-error");
        cbxEstudiante.getStyleClass().remove("text-field-error");
        cbxCursoIdioma.getStyleClass().remove("text-field-error");
        dateFechaInicio.getStyleClass().remove("text-field-error");
        dateFechaFin.getStyleClass().remove("text-field-error");
        cbxEstado.getStyleClass().remove("text-field-error");
    }

    public void clearForm() {
        txtNivel.setText("");
        cbxEstudiante.getSelectionModel().select(null);
        cbxCursoIdioma.getSelectionModel().select(null);
        dateFechaInicio.setValue(null);
        dateFechaFin.setValue(null);
        cbxEstado.getSelectionModel().select(null);
        idMatriculaCE = 0L;
        limpiarError();
    }

    @FXML
    public void cancelarAccion() {
        clearForm();
        limpiarError();
    }

    void validarCampos(List<ConstraintViolation<MatriculaIdioma>> violacionesOrdenadasPorPropiedad) {
        LinkedHashMap<String, String> erroresOrdenados = new LinkedHashMap<>();
        for (ConstraintViolation<MatriculaIdioma> violacion : violacionesOrdenadasPorPropiedad) {
            String campo = violacion.getPropertyPath().toString();
            switch (campo) {
                case "nivel" -> {
                    erroresOrdenados.put("nivel", violacion.getMessage());
                    txtNivel.getStyleClass().add("text-field-error");
                }
                case "estudiante" -> {
                    erroresOrdenados.put("estudiante", violacion.getMessage());
                    cbxEstudiante.getStyleClass().add("text-field-error");
                }
                case "cursoIdioma" -> {
                    erroresOrdenados.put("cursoIdioma", violacion.getMessage());
                    cbxCursoIdioma.getStyleClass().add("text-field-error");
                }
                case "fechaInicio" -> {
                    erroresOrdenados.put("fechaInicio", violacion.getMessage());
                    dateFechaInicio.getStyleClass().add("text-field-error");
                }
                case "fechaFin" -> {
                    erroresOrdenados.put("fechaFin", violacion.getMessage());
                    dateFechaFin.getStyleClass().add("text-field-error");
                }
                case "estado" -> {
                    erroresOrdenados.put("estado", violacion.getMessage());
                    cbxEstado.getStyleClass().add("text-field-error");
                }
            }
        }
        Map.Entry<String, String> primerError = erroresOrdenados.entrySet().iterator().next();
        lbnMsg.setText(primerError.getValue());
        lbnMsg.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
    }

    @FXML
    public void validarFormulario() {
        formulario = new MatriculaIdioma();
        formulario.setNivel(txtNivel.getText());
        formulario.setFechaInicio(dateFechaInicio.getValue());
        formulario.setFechaFin(dateFechaFin.getValue());

        String estudianteId = cbxEstudiante.getSelectionModel().getSelectedItem() == null ? "0" : cbxEstudiante.getSelectionModel().getSelectedItem().getKey();
        formulario.setEstudiante(estudianteService.searchById(Long.parseLong(estudianteId)));

        String cursoIdiomaId = cbxCursoIdioma.getSelectionModel().getSelectedItem() == null ? "0" : cbxCursoIdioma.getSelectionModel().getSelectedItem().getKey();
        formulario.setCursoIdioma(cursoIdiomaService.searchById(Long.parseLong(cursoIdiomaId)));

        formulario.setEstado(cbxEstado.getSelectionModel().getSelectedItem() == null ? "" : cbxEstado.getSelectionModel().getSelectedItem().getValue());

        Set<ConstraintViolation<MatriculaIdioma>> violaciones = validator.validate(formulario);
        List<ConstraintViolation<MatriculaIdioma>> violacionesOrdenadasPorPropiedad = violaciones.stream()
                .sorted((v1, v2) -> v1.getPropertyPath().toString().compareTo(v2.getPropertyPath().toString()))
                .collect(Collectors.toList());

        if (violacionesOrdenadasPorPropiedad.isEmpty()) {
            // Si no hay violaciones, los datos son válidos
            lbnMsg.setText("Formulario válido");
            lbnMsg.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            limpiarError();

            double width = stage.getWidth() / 1.5;
            double height = stage.getHeight() / 2;

            if (idMatriculaCE != 0L) {
                formulario.setIdMatricula(idMatriculaCE);
                matriculaIdiomaService.update(formulario);
                Toast.showToast(stage, "Se actualizó correctamente!!", 2000, width, height);
            } else {
                matriculaIdiomaService.save(formulario);
                Toast.showToast(stage, "Se guardó correctamente!!", 2000, width, height);
            }
            clearForm();
            listar();
        } else {
            validarCampos(violacionesOrdenadasPorPropiedad);
        }
    }

    private void filtrarMatriculas(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tableView.getItems().clear();
            tableView.getItems().addAll(listaMatriculas);
        } else {
            String lowerCaseFilter = filtro.toLowerCase();
            List<MatriculaIdioma> matriculasFiltradas = listaMatriculas.stream()
                    .filter(matricula -> {
                        if (matricula.getEstudiante().getNombre().toLowerCase().contains(lowerCaseFilter)) return true;
                        if (matricula.getCursoIdioma().getNombre().toLowerCase().contains(lowerCaseFilter)) return true;
                        if (matricula.getNivel().toLowerCase().contains(lowerCaseFilter)) return true;
                        return matricula.getEstado().toLowerCase().contains(lowerCaseFilter);
                    })
                    .collect(Collectors.toList());

            tableView.getItems().clear();
            tableView.getItems().addAll(matriculasFiltradas);
        }
    }

    public void editForm(MatriculaIdioma matricula) {
        txtNivel.setText(matricula.getNivel());
        dateFechaInicio.setValue(matricula.getFechaInicio());
        dateFechaFin.setValue(matricula.getFechaFin());

        cbxEstudiante.getSelectionModel().select(
                cbxEstudiante.getItems().stream()
                        .filter(est -> Long.parseLong(est.getKey()) == matricula.getEstudiante().getIdEstudiante())
                        .findFirst()
                        .orElse(null)
        );

        cbxCursoIdioma.getSelectionModel().select(
                cbxCursoIdioma.getItems().stream()
                        .filter(curso -> Long.parseLong(curso.getKey()) == matricula.getCursoIdioma().getIdCursoIdioma())
                        .findFirst()
                        .orElse(null)
        );

        cbxEstado.getSelectionModel().select(
                cbxEstado.getItems().stream()
                        .filter(estado -> estado.getValue().equals(matricula.getEstado()))
                        .findFirst()
                        .orElse(null)
        );

        idMatriculaCE = matricula.getIdMatricula();
        limpiarError();
    }
}
