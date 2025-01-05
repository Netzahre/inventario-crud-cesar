package controlador;

import CRUD.CrudAula;
import CRUD.CrudMarcajes;
import CRUD.CrudProducto;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.Aula;
import modelo.Marcaje;
import modelo.Producto;
import java.util.List;

public class controladorCrearMarcaje {
    @FXML
    private ComboBox<String> cbAula;
    @FXML
    private ComboBox<String> cbProducto;
    @FXML
    private Slider sliderTipo;
    @FXML
    private Label labelTipo;
    @FXML
    private DatePicker fechaMarcaje;
    @FXML
    private CheckBox validar;

    /**
     * Método que se ejecuta al cargar la ventana
     */
    @FXML
    private void initialize() {
        List<Aula> aulas = rellenarListAulas();
        for (Aula aula : aulas) {
            cbAula.getItems().add(aula.getNumeracion());
        }

        List<Producto> productos = rellenarListProductos();
        for (Producto producto : productos) {
            cbProducto.getItems().add(producto.getDescripcion());
        }

        cbAula.getSelectionModel().selectFirst();
        cbProducto.getSelectionModel().selectFirst();

        sliderTipo.setValue(0);
        labelTipo.setText("Entrada");

        sliderTipo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                labelTipo.setText("Entrada");
            } else {
                labelTipo.setText("Salida");
            }
        });

        fechaMarcaje.setValue(java.time.LocalDate.now());
    }

    /**
     * Método que rellena la lista de aulas
     * @return lista de aulas
     */
    private List<Aula> rellenarListAulas() {
        CrudAula crudAula = new CrudAula();
        return crudAula.verTodasAulas();
    }

    /**
     * Método que rellena la lista de productos
     * @return lista de productos
     */
    private List<Producto> rellenarListProductos() {
        CrudProducto crudProducto = new CrudProducto();
        return crudProducto.verTodosProductos();
    }

    /**
     * Método que crea un marcaje
     */
    @FXML
    private void crearMarcaje() {
        if (!validar.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debes confirmar que entiendes que los datos introducidos son correctos y que no se pueden modificar una vez creado el marcaje.");
            alert.showAndWait();
            return;
        }

        String numeracionAula = cbAula.getValue();
        Aula aulaSeleccionada = null;
        for (Aula aula : rellenarListAulas()) {
            if (aula.getNumeracion().equals(numeracionAula)) {
                aulaSeleccionada = aula;
                break;
            }
        }

        String descripcionProducto = cbProducto.getValue();
        Producto productoSeleccionado = null;
        for (Producto producto : rellenarListProductos()) {
            if (producto.getDescripcion().equals(descripcionProducto)) {
                productoSeleccionado = producto;
                break;
            }
        }

        int tipoMarcaje = (int) sliderTipo.getValue();
        java.time.LocalDate fecha = fechaMarcaje.getValue();

        java.time.LocalTime horaActual = java.time.LocalTime.now();

        java.time.LocalDateTime localDateTime = java.time.LocalDateTime.of(fecha, horaActual);

        Marcaje marcaje = new Marcaje();
        marcaje.setIdAula(aulaSeleccionada);
        marcaje.setIdProducto(productoSeleccionado);
        marcaje.setTipo(tipoMarcaje);

        marcaje.setTimeStamp(localDateTime);

        CrudMarcajes crudMarcaje = new CrudMarcajes();
        crudMarcaje.crearMarcaje(marcaje);
        Stage stage = (Stage) cbAula.getScene().getWindow();
        stage.close();
    }

    /**
     * Método que cancela la operación
     */
    @FXML
    private void cancelar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar");
        alert.setHeaderText("¿Estás seguro de que quieres cancelar la operación?");
        alert.setContentText("Si cancelas la operación, perderás los datos introducidos.");
        ButtonType buttonTypeSi = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeSi) {
                Stage stage = (Stage) cbAula.getScene().getWindow();
                stage.close();
            }
        });
    }

    /**
     * Método que vacía los campos
     */
    @FXML
    private void vaciarCampos() {
        cbAula.getSelectionModel().selectFirst();
        cbProducto.getSelectionModel().selectFirst();
        sliderTipo.setValue(0);
        labelTipo.setText("Entrada");
        validar.setSelected(false);
        fechaMarcaje.setValue(java.time.LocalDate.now());
    }

    /**
     * Método que muestra la ayuda
     */
    @FXML
    private void mostrarAyuda() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ayuda");
        alert.setHeaderText("Crear marcaje");
        alert.setContentText("En este formulario se puede crear un marcaje. \n"
                + "Para crear un marcaje, se debe seleccionar el aula, el producto, el tipo de marcaje, la fecha y confirmar que los datos son correctos. \n"
                + "Para guardar los cambios, se debe hacer clic en el botón Guardar.");
        alert.showAndWait();
    }
}
