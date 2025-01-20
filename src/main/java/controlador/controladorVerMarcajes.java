package controlador;

import CRUD.CrudAula;
import CRUD.CrudMarcajes;
import CRUD.CrudProducto;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Aula;
import modelo.Marcaje;
import modelo.Producto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class controladorVerMarcajes {
    @FXML
    private TableView<Marcaje> tbMarcaje;
    @FXML
    private TableColumn<Marcaje, Integer> colId;
    @FXML
    private TableColumn<Marcaje, String> colProducto;
    @FXML
    private TableColumn<Marcaje, String> colAula;
    @FXML
    private TableColumn<Marcaje, String> colTipo;
    @FXML
    private TableColumn<Marcaje, String> colTimeStamp;
    @FXML
    private TableColumn<Marcaje, String> colCategoria;
    @FXML
    private DatePicker fInicio;
    @FXML
    private DatePicker fFin;
    @FXML
    private ComboBox<Aula> listaAulas;
    @FXML
    private ComboBox<Producto> listaProd;
    @FXML
    private Spinner<Integer> spPabellon;


    /**
     * Método que se ejecuta al cargar la vista
     */
    @FXML
    public void initialize() {
        inicializarColumnas();
        rellenarTabla();
        tbMarcaje.getSelectionModel().clearSelection();
        spPabellon.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0));
        listaAulas.getItems().addAll(new CrudAula().verTodasAulas());
        listaAulas.setPromptText("aula");
        listaProd.getItems().addAll(new CrudProducto().verTodosProductos());
        listaProd.setPromptText("producto");
    }

    /**
     * Método que inicializa las columnas de la tabla
     */
    private void inicializarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProducto.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getIdProducto().getDescripcion()));
        colCategoria.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getIdProducto().getCategoria().getNombre()));

        colAula.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getIdAula().getNumeracion()));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoTexto"));
        colTimeStamp.setCellValueFactory(cellData -> {
            LocalDateTime timeStamp = cellData.getValue().getTimeStamp();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return new ReadOnlyObjectWrapper<>(timeStamp.format(formatter));
        });

    }

    /**
     * Método que rellena la tabla con los marcajes
     */
    private void rellenarTabla() {
        CrudMarcajes crudMarcajes = new CrudMarcajes();
        tbMarcaje.getItems().clear();
        tbMarcaje.getItems().addAll(crudMarcajes.verMarcajes());
    }

    /**
     * Método que se ejecuta al pulsar el botón de borrar marcaje
     */
    @FXML
    private void borrarMarcaje() {
        Marcaje marcaje = tbMarcaje.getSelectionModel().getSelectedItem();
        if (marcaje != null) {
            CrudMarcajes crudMarcajes = new CrudMarcajes();
            crudMarcajes.borrarMarcaje(marcaje.getId());
            rellenarTabla();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ningún marcaje");
            alert.setContentText("Por favor, selecciona un marcaje de la tabla");
            alert.showAndWait();
        }
    }

    /**
     * Método que se ejecuta al pulsar el botón de crear marcaje
     */
    @FXML
    private void crearMarcaje() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/org/example/vistas/crearMarcaje.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Scene currentScene = tbMarcaje.getScene();
            if (currentScene != null) {
                for (String stylesheet : currentScene.getStylesheets()) {
                    scene.getStylesheets().add(stylesheet);
                }
            }
            Stage stage = new Stage();
            stage.setTitle("Crear Marcaje");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            rellenarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que se ejecuta al pulsar el botón de filtrar marcaje
     */
    @FXML
    private void filtrarMarcajes() {
        LocalDateTime fechaInicioVal = fInicio.getValue() != null ? fInicio.getValue().atStartOfDay() : null;
        LocalDateTime fechaFinVal = fFin.getValue() != null ? fFin.getValue().atTime(23, 59, 59) : null;

        //Si no se ha seleccionado un producto o un aula, se asigna null
        Producto productoSeleccionado = listaProd.getValue();
        Aula aulaSeleccionada = listaAulas.getValue();
        int pabellon = spPabellon.getValue();

        CrudMarcajes crudMarcajes = new CrudMarcajes();
        tbMarcaje.getItems().clear();
        tbMarcaje.getItems().addAll(crudMarcajes.filtrarMarcajes(fechaInicioVal, fechaFinVal, productoSeleccionado, aulaSeleccionada, pabellon));
    }

    /**
     * Método que se ejecuta al pulsar el botón de limpiar filtros
     */
    @FXML
    private void limpiarFiltros() {
        fInicio.setValue(null);
        fFin.setValue(null);
        listaProd.setValue(null);
        listaProd.setPromptText("producto");
        listaAulas.setValue(null);
        listaAulas.setPromptText("aula");
        spPabellon.getValueFactory().setValue(0);
        rellenarTabla();
    }
}
