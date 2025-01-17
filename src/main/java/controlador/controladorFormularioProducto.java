package controlador;

import CRUD.CrudAula;
import CRUD.CrudCategoria;
import CRUD.CrudProducto;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Aula;
import modelo.Categoria;
import modelo.Producto;

public class controladorFormularioProducto {
    private Producto producto;
    @FXML
    private Node formularioProducto;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextField tfEAN;
    @FXML
    private TextField tfKeyRFID;
    @FXML
    private ListView<Categoria> lvCategorias;

    /**
     * Método que recibe un producto y lo carga en el formulario.
     *
     * @param producto Producto a cargar en el formulario.
     */
    public void recibirProducto(Producto producto) {
        if (formularioProducto != null) {
            taDescripcion = (TextArea) formularioProducto.lookup("#taDescripcion");
            tfEAN = (TextField) formularioProducto.lookup("#tfEAN");
            tfKeyRFID = (TextField) formularioProducto.lookup("#tfKeyRFID");
            lvCategorias = (ListView<Categoria>) formularioProducto.lookup("#lvCategorias");
        }
        CrudCategoria crudCategoria = new CrudCategoria();
        lvCategorias.getItems().addAll(crudCategoria.verCategorias());
        if (producto != null) {
            this.producto = producto;
            cargarDatos();
        }
    }

    /**
     * Método que carga los datos del producto en el formulario.
     */
    private void cargarDatos() {
        if (producto != null) {
            taDescripcion.setText(producto.getDescripcion());
            tfEAN.setText(String.valueOf(producto.getEan()));
            tfKeyRFID.setText(producto.getKeyRFID());
            for (Categoria categoria : lvCategorias.getItems()) {
                for (Categoria categoriaProducto : producto.getCategorias()) {
                    if (categoria.getId().equals(categoriaProducto.getId())) {
                        lvCategorias.getSelectionModel().select(categoria);
                    }
                }
            }
        }
    }

    /**
     * Método que recibe un producto y lo carga en el formulario.
     *
     * @param /producto Producto a cargar en el formulario.
     */
    @FXML
    private void guardarDatos() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Guardar");
        alert.setContentText("¿Está seguro que desea guardar los cambios?");
        ButtonType botonAceptar = new ButtonType("Si");
        ButtonType botonCancelar = new ButtonType("No");
        alert.getButtonTypes().setAll(botonAceptar, botonCancelar);
        alert.showAndWait().ifPresent(boton -> {
            if (boton == botonAceptar) {
                CrudProducto crudProducto = new CrudProducto();
                if (!verificarCampos()) {
                    return;
                }

                if (producto == null) {
                    producto = new Producto();
                    producto.setDescripcion(taDescripcion.getText());
                    producto.setEan(Integer.parseInt(tfEAN.getText()));
                    producto.setKeyRFID(tfKeyRFID.getText());
                    producto.getCategorias().add(lvCategorias.getSelectionModel().getSelectedItem());
                    crudProducto.crearProducto(producto);
                } else {
                    producto.setDescripcion(taDescripcion.getText());
                    producto.setEan(Integer.parseInt(tfEAN.getText()));
                    producto.setKeyRFID(tfKeyRFID.getText());
                    producto.getCategorias().add(lvCategorias.getSelectionModel().getSelectedItem());
                    crudProducto.actualizarProducto(producto.getId(), producto);
                }
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Información");
                alert2.setContentText("Los cambios se han guardado correctamente.");
                alert2.showAndWait();
                Stage stage = (Stage) taDescripcion.getScene().getWindow();
                stage.close();
            }
        });

    }

    /**
     * Método que verifica que los campos obligatorios estén completos.
     *
     * @return true si los campos están completos, false si no.
     */
    private boolean verificarCampos() {
        StringBuilder mensaje = new StringBuilder();
        if (taDescripcion.getText().isEmpty()) {
            mensaje.append("La descripción es obligatoria.\n");
        }
        if (tfEAN.getText().isEmpty()) {
            mensaje.append("El EAN es obligatorio.\n");
        }
        if (tfKeyRFID.getText().isEmpty()) {
            mensaje.append("La clave RFID es obligatoria.\n");
        }
        if (tfEAN.getText().length() > 11) {
            mensaje.append("El EAN no puede tener más de 11 dígitos.\n");
        }
        if (tfKeyRFID.getText().length() > 10) {
            mensaje.append("La clave RFID no puede tener más de 10 caracteres.\n");
        }
        if (tfEAN != null && !tfEAN.getText().matches("[0-9]+")) {
            mensaje.append("El EAN solo puede contener números.\n");
        }

        if (mensaje.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos obligatorios");
            alert.setContentText(mensaje.toString());
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Método que limpia los campos del formulario.
     */
    @FXML
    private void limpiar() {
        taDescripcion.clear();
        tfEAN.clear();
        tfKeyRFID.clear();
        lvCategorias.getSelectionModel().clearSelection();
    }

    /**
     * Método que muestra un mensaje de ayuda.
     */
    @FXML
    private void mostrarAyuda() {
        if (producto == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setContentText("En este formulario se puede registrar un nuevo producto. \n"
                    + "Para registrar un producto, se debe ingresar la descripción, el EAN, la clave RFID y seleccionar una categoría. \n"
                    + "Para guardar el producto, se debe hacer clic en el botón Guardar.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setContentText("En este formulario se puede editar un producto. \n"
                    + "Para editar un producto, se debe modificar la descripción, el EAN, la clave RFID y seleccionar una categoría. \n"
                    + "Para guardar los cambios, se debe hacer clic en el botón Guardar.");
            alert.showAndWait();
        }
    }

    /**
     * Método que cierra la ventana.
     */
    @FXML
    private void cancelar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar");
        alert.setContentText("¿Está seguro que desea cancelar la operación?");
        ButtonType botonAceptar = new ButtonType("Si");
        ButtonType botonCancelar = new ButtonType("No");
        alert.getButtonTypes().setAll(botonAceptar, botonCancelar);
        alert.showAndWait().ifPresent(boton -> {
            if (boton == botonAceptar) {
                Stage stage = (Stage) tfEAN.getScene().getWindow();
                stage.close();
            }
        });
    }

    /**
     * Método que selecciona todos los elementos de un ListView.
     */
    @FXML
    private void seleccionarTodoListview() {
        lvCategorias.getSelectionModel().selectAll();
    }

    /**
     * Método que deselecciona todos los elementos de un ListView.
     */
    @FXML
    private void deseleccionarTodoListview() {
        lvCategorias.getSelectionModel().clearSelection();
    }
}
