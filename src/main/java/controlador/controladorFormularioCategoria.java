package controlador;

import CRUD.CrudCategoria;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Categoria;

public class controladorFormularioCategoria {
    private Categoria categoria;

    @FXML
    private Node formularioCategoria;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextArea taDescripcion;

    @FXML
    private RadioButton rbActivo;

    @FXML
    private RadioButton rbDesactivado;

    /**
     * Método que recibe una categoría y la carga en el formulario
     * @param nuevaCategoria
     */
    public void recibirCategoria(Categoria nuevaCategoria) {
        if (formularioCategoria != null) {
            tfNombre = (TextField) formularioCategoria.lookup("#tfNombre");
            taDescripcion = (TextArea) formularioCategoria.lookup("#taDescripcion");
            rbActivo = (RadioButton) formularioCategoria.lookup("#rbActivo");
            rbDesactivado = (RadioButton) formularioCategoria.lookup("#rbDesactivado");
        }

        if (nuevaCategoria != null) {
            this.categoria = nuevaCategoria;
            cargarDatos();
        }
    }

    /**
     * Método que carga los datos de la categoría en el formulario
     */
    private void cargarDatos() {
        if (categoria != null) {
            tfNombre.setText(categoria.getNombre());
            taDescripcion.setText(categoria.getDescripcion());
            if (categoria.getEstado().equals("Activo")) {
                rbActivo.setSelected(true);
            } else {
                rbDesactivado.setSelected(true);
            }
        }
    }

    /**
     * Método que guarda los datos de la categoría en la base de datos
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
                CrudCategoria crudCategoria = new CrudCategoria();
                if (!verificarCampos()) {
                    return;
                }
                if (categoria == null) {
                    Categoria nuevaCategoria = new Categoria();
                    nuevaCategoria.setNombre(tfNombre.getText());
                    nuevaCategoria.setDescripcion(taDescripcion.getText());
                    if (rbActivo.isSelected()) {
                        nuevaCategoria.setEstado("Activo");
                    } else {
                        nuevaCategoria.setEstado("Desactivado");
                    }
                    crudCategoria.crearCategoria(nuevaCategoria);
                } else {
                    Categoria nuevaCategoria = new Categoria();
                    nuevaCategoria.setNombre(tfNombre.getText());
                    nuevaCategoria.setDescripcion(taDescripcion.getText());
                    if (rbActivo.isSelected()) {
                        nuevaCategoria.setEstado("Activo");
                    } else {
                        nuevaCategoria.setEstado("Desactivado");
                    }
                    crudCategoria.actualizarCategoria(categoria.getId(), nuevaCategoria);
                }
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Guardado");
                alert1.setHeaderText("Cambios guardados");
                alert1.setContentText("Los cambios se han guardado correctamente.");
                alert1.showAndWait();
                Stage stage = (Stage) tfNombre.getScene().getWindow();
                stage.close();
            }
        });

    }

    /**
     * Método que verifica si los campos del formulario están completos
     * @return boolean
     */
    private boolean verificarCampos() {
        StringBuilder mensaje = new StringBuilder();
        if (tfNombre.getText().isEmpty()) {
            mensaje.append("El campo nombre es obligatorio\n");
        }
        if (taDescripcion.getText().isEmpty()) {
            mensaje.append("El campo descripción es obligatorio\n");
        }
        if (!rbActivo.isSelected() && !rbDesactivado.isSelected()) {
            mensaje.append("El campo estado es obligatorio\n");
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
     * Método que limpia los campos del formulario
     */
    @FXML
    private void limpiar() {
        tfNombre.setText("");
        taDescripcion.setText("");
        rbActivo.setSelected(false);
        rbDesactivado.setSelected(false);
    }

    /**
     * Método que muestra la ayuda del formulario
     */
    @FXML
    private void mostrarAyuda() {
        if (categoria == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setContentText("En este formulario se puede crear una categoría. \n"
                    + "Para crear una categoría, se debe ingresar el nombre, la descripción y seleccionar el estado. \n"
                    + "Para guardar la categoría, se debe hacer clic en el botón Guardar.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setContentText("En este formulario se puede editar una categoría. \n"
                    + "Para editar una categoría, se debe modificar el nombre, la descripción y seleccionar el estado. \n"
                    + "Para guardar los cambios, se debe hacer clic en el botón Guardar.");
            alert.showAndWait();
        }
    }

    /**
     * Método que cierra el formulario
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
                Stage stage = (Stage) tfNombre.getScene().getWindow();
                stage.close();
            }
        });
    }

}
