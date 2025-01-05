package controlador;

import CRUD.CrudAula;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Aula;

public class controladorVerAulas {
    @FXML
    private TableView<Aula> tvAulas;
    @FXML
    private TableColumn<Aula, String> colNumeracion;
    @FXML
    private TableColumn<Aula, String> colDescripcion;
    @FXML
    private TableColumn<Aula, String> colIp;
    @FXML
    private TableColumn<Aula, Integer> colId;

    /**
     * Método que inicializa la tabla y rellena las columnas con los datos de las aulas
     */
    @FXML
    public void initialize() {
        inicializarColumnas();
        rellenarTabla();
        tvAulas.getSelectionModel().clearSelection();

    }

    /**
     * Método que inicializa las columnas de la tabla
     */
    private void inicializarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumeracion.setCellValueFactory(new PropertyValueFactory<>("numeracion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colIp.setCellValueFactory(new PropertyValueFactory<>("ip"));
    }

    /**
     * Método que rellena la tabla con los datos de las aulas
     */
    private void rellenarTabla() {
        CrudAula crudAula = new CrudAula();
        tvAulas.getItems().clear();
        tvAulas.getItems().addAll(crudAula.verTodasAulas());
    }

    /**
     * Método que borra un aula de la base de datos
     */
    @FXML
    private void borrarAula() {
        Aula aula = tvAulas.getSelectionModel().getSelectedItem();
        if (aula != null) {
            CrudAula crudAula = new CrudAula();
            crudAula.borrarAula(aula.getId());
            rellenarTabla();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ninguna aula");
            alert.setContentText("Por favor, selecciona un aula de la tabla");
            alert.showAndWait();
        }
    }

    /**
     * Método que abre la ventana para crear un aula
     */
    @FXML
    private void crearAula() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/org/example/vistas/CrearNuevaAula.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Scene currentScene = tvAulas.getScene();
            if (currentScene != null) {
                for (String stylesheet : currentScene.getStylesheets()) {
                    scene.getStylesheets().add(stylesheet);
                }
            }
            controladorFormularioAula controlador = loader.getController();
            controlador.recibirAula(null);
            Stage stage = new Stage();
            stage.setTitle("Crear Aula");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            rellenarTabla();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que abre la ventana para modificar un aula
     */
    @FXML
    private void modificarAula(){
        Aula aula = tvAulas.getSelectionModel().getSelectedItem();
        if (aula != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/example/vistas/ModificarAula.fxml"));
            try {
                Scene scene = new Scene(loader.load());
                Scene currentScene = tvAulas.getScene();
                if (currentScene != null) {
                    for (String stylesheet : currentScene.getStylesheets()) {
                        scene.getStylesheets().add(stylesheet);
                    }
                }
                controladorFormularioAula controlador = loader.getController();
                controlador.recibirAula(aula);
                Stage stage = new Stage();
                stage.setTitle("Modificar Aula");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.showAndWait();
                rellenarTabla();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ninguna aula");
            alert.setContentText("Por favor, selecciona un aula de la tabla");
            alert.showAndWait();
        }
    }
}
