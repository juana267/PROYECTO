package pe.edu.upeu.segundaunidadfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SegundaUnidadFxApplication extends Application {

	private ConfigurableApplicationContext context;

	@Override
	public void init() {
		// Iniciar el contexto de Spring Boot
		context = new SpringApplicationBuilder(SegundaUnidadFxApplication.class).run();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Cargar el archivo FXML
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/registro.fxml"));
		fxmlLoader.setControllerFactory(context::getBean); // Permitir que Spring gestione los controladores
		Parent root = fxmlLoader.load();

		// Configurar la escena de JavaFX
		primaryStage.setTitle("Registro de Usuarios");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	@Override
	public void stop() {
		// Cerrar el contexto de Spring cuando se cierra la aplicación JavaFX
		context.close();
	}

	public static void main(String[] args) {
		launch(args); // Iniciar la aplicación JavaFX
	}
}
