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
	public void init() throws Exception {
		context = new SpringApplicationBuilder(SegundaUnidadFxApplication.class).run();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
		loader.setControllerFactory(context::getBean); // Configura el controlador con Spring
		Parent root = loader.load();

		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Inicio de Sesión");
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		context.close();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
