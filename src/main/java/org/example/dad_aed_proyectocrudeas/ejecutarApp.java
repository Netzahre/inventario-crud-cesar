package org.example.dad_aed_proyectocrudeas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controlador.controladorMenuSuperior;

import java.io.IOException;

public class ejecutarApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/vistas/pantallaInicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inicio");
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/org/example/css/diurno.css").toExternalForm());
        controladorMenuSuperior.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}