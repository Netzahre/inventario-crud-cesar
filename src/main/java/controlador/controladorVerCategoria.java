package controlador;

import CRUD.CrudAula;
import CRUD.CrudCategoria;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Aula;
import modelo.Categoria;

public class controladorVerCategoria {
    @FXML
    private TableView<Categoria> tvCategoria;
    @FXML
    private TableColumn<Categoria, Integer> colId;
    @FXML
    private TableColumn<Categoria, String> colNombre;
    @FXML
    private TableColumn<Categoria, String> colDescripcion;
    @FXML
    private TableColumn<Categoria, String> colEstado;
    @FXML
    private TableColumn<Categoria, String> colCantidad;
    /**
     * Método que inicializa la tabla y rellena las columnas con los datos de las categorias
     */
    @FXML
    public void initialize() {
        inicializarColumnas();
        rellenarTabla();
        tvCategoria.getSelectionModel().clearSelection();

    }

    /**
     * Método que inicializa las columnas de la tabla
     */
    private void inicializarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colCantidad.setCellValueFactory(cellData -> {
            Categoria categoria = cellData.getValue();
            return new SimpleStringProperty(String.valueOf(categoria.getCantidadProductos()));
        });
    }

    /**
     * Método que rellena la tabla con los datos de las categorias
     */
    private void rellenarTabla() {
        CrudCategoria crudCategoria = new CrudCategoria();
        tvCategoria.getItems().clear();
        tvCategoria.getItems().addAll(crudCategoria.verCategorias());
    }

    /**
     * Método que borra una categoria de la base de datos
     */
    @FXML
    private void borrarCategoria() {
        Categoria categoria = tvCategoria.getSelectionModel().getSelectedItem();
        if (categoria != null) {
            CrudCategoria crudCategoria = new CrudCategoria();
            crudCategoria.borrarCategoria(categoria.getId());
            rellenarTabla();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ninguna categoria");
            alert.setContentText("Por favor, selecciona una categoria de la tabla");
            alert.showAndWait();
        }
    }

    /**
     * Método que abre la ventana para crear una categoria
     */
    @FXML
    private void crearCategoria() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/org/example/vistas/crearNuevaCategoria.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Scene currentScene = tvCategoria.getScene();
            if (currentScene != null) {
                for (String stylesheet : currentScene.getStylesheets()) {
                    scene.getStylesheets().add(stylesheet);
                }
            }
            controladorFormularioCategoria controlador = loader.getController();
            controlador.recibirCategoria(null);
            Stage stage = new Stage();
            stage.setTitle("Crear Categoria");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            rellenarTabla();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que abre la ventana para modificar una categoria
     */
    @FXML
    private void modificarCategoria(){
        Categoria categoria = tvCategoria.getSelectionModel().getSelectedItem();
        if (categoria != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/example/vistas/ModificarCategoria.fxml"));
            try {
                Scene scene = new Scene(loader.load());
                Scene currentScene = tvCategoria.getScene();
                if (currentScene != null) {
                    for (String stylesheet : currentScene.getStylesheets()) {
                        scene.getStylesheets().add(stylesheet);
                    }
                }
                controladorFormularioCategoria controlador = loader.getController();
                controlador.recibirCategoria(categoria);
                Stage stage = new Stage();
                stage.setTitle("Modificar Categoria");
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
            alert.setHeaderText("No se ha seleccionado ninguna categoria");
            alert.setContentText("Por favor, selecciona una categoria de la tabla");
            alert.showAndWait();
        }
    }
}
