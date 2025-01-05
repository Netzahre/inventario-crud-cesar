package controlador;

import CRUD.CrudAula;
import CRUD.CrudCategoria;
import CRUD.CrudProducto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Aula;
import modelo.Categoria;
import modelo.Producto;

public class controladorVerProductos {
    @FXML
    private TableView<Producto> tvProducto;
    @FXML
    private TableColumn<Producto, Integer> colId;
    @FXML
    private TableColumn<Aula, String> colDescripcion;
    @FXML
    private TableColumn<Producto, String> colEAN;
    @FXML
    private TableColumn<Producto, String> colKeyRFID;
    @FXML
    private TextField tfDescripcion;
    @FXML
    private TextField tfEAN;
    @FXML
    private TextField tfKeyRFID;
    @FXML
    private ComboBox<String> cbCategoria;

    /**
     * Método que se ejecuta al inicializar la ventana
     */
    @FXML
    public void initialize() {
        inicializarColumnas();
        rellenarTabla();
        tvProducto.getSelectionModel().clearSelection();
        CrudCategoria crudCategoria = new CrudCategoria();
        for (Categoria categoria : crudCategoria.verCategorias()) {
            cbCategoria.getItems().add(categoria.getNombre());
        }
        cbCategoria.getItems().add("Sin categoria");
        cbCategoria.setPromptText("Categoria");
    }

    /**
     * Método que inicializa las columnas de la tabla
     */
    private void inicializarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEAN.setCellValueFactory(new PropertyValueFactory<>("ean"));
        colKeyRFID.setCellValueFactory(new PropertyValueFactory<>("keyRFID"));
    }

    /**
     * Método que rellena la tabla con los productos de la base de datos
     */
    private void rellenarTabla() {
        CrudProducto crudProducto = new CrudProducto();
        tvProducto.getItems().clear();
        tvProducto.getItems().addAll(crudProducto.verTodosProductos());
    }

    /**
     * Método que borra un producto de la base de datos
     */
    @FXML
    private void borrarProducto() {
        Producto producto = tvProducto.getSelectionModel().getSelectedItem();
        if (producto != null) {
            CrudProducto crudAula = new CrudProducto();
            crudAula.borrarProducto(producto.getId());
            rellenarTabla();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ningun producto");
            alert.setContentText("Por favor, selecciona un producto de la tabla");
            alert.showAndWait();
        }
    }

    /**
     * Método que abre la ventana para crear un producto
     */
    @FXML
    private void crearProducto() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/org/example/vistas/crearNuevoProducto.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Scene currentScene = tvProducto.getScene();
            if (currentScene != null) {
                for (String stylesheet : currentScene.getStylesheets()) {
                    scene.getStylesheets().add(stylesheet);
                }
            }
            controladorFormularioProducto controlador = loader.getController();
            controlador.recibirProducto(null);
            Stage stage = new Stage();
            stage.setTitle("Crear Producto");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            rellenarTabla();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que abre la ventana para modificar un producto
     */
    @FXML
    private void modificarProducto(){
        Producto producto = tvProducto.getSelectionModel().getSelectedItem();
        if (producto != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/example/vistas/ModificarProducto.fxml"));
            try {
                Scene scene = new Scene(loader.load());
                Scene currentScene = tvProducto.getScene();
                if (currentScene != null) {
                    for (String stylesheet : currentScene.getStylesheets()) {
                        scene.getStylesheets().add(stylesheet);
                    }
                }
                controladorFormularioProducto controlador = loader.getController();
                controlador.recibirProducto(producto);
                Stage stage = new Stage();
                stage.setTitle("Modificar Producto");
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
            alert.setHeaderText("No se ha seleccionado ningun producto");
            alert.setContentText("Por favor, selecciona un producto de la tabla");
            alert.showAndWait();
        }
    }

    /**
     * Método que filtra productos por su descripción, EAN, keyRFID y categoría
     */
    @FXML
    private void buscarProducto(){
        CrudProducto crudProducto = new CrudProducto();
        tvProducto.getItems().clear();
        int ean = 0;
        if (!tfEAN.getText().isEmpty()) {
            if (tfEAN.getText().matches("[0-9]+")) {
                ean = Integer.parseInt(tfEAN.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("El EAN introducido no es valido");
                alert.setContentText("Por favor, introduce un EAN valido");
                alert.showAndWait();
                return;
            }
        }
        tvProducto.getItems().addAll(crudProducto.buscarProducto(tfDescripcion.getText(), ean, tfKeyRFID.getText(), cbCategoria.getSelectionModel().getSelectedItem()));
    }

    /**
     * Método que limpia los campos de búsqueda
     */
    @FXML
    private void limpiarBusqueda(){
        tfDescripcion.clear();
        tfEAN.clear();
        tfKeyRFID.clear();
        cbCategoria.getSelectionModel().clearSelection();
        cbCategoria.setPromptText("Categoria");
        rellenarTabla();
    }
}
