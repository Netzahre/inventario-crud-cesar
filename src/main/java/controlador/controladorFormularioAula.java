package controlador;

import CRUD.CrudAula;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Aula;

public class controladorFormularioAula {
    private Aula aula;
    @FXML
    private Node formularioAula;
    @FXML
    private Spinner<Integer> spPabellon;
    @FXML
    private Spinner<Integer> spPiso;
    @FXML
    private Spinner<Integer> spAula;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextField tfIp;
    @FXML
    private Button btGuardar;
    @FXML
    private Button btLimpiar;

    /**
     * Recibe un aula y carga los datos en el formulario.
     *
     * @param nuevaAula Aula a cargar en el formulario.
     */
    public void recibirAula(Aula nuevaAula) {
        if (formularioAula != null) {
            spPabellon = (Spinner<Integer>) formularioAula.lookup("#spPabellon");
            spPiso = (Spinner<Integer>) formularioAula.lookup("#spPiso");
            spAula = (Spinner<Integer>) formularioAula.lookup("#spAula");
            taDescripcion = (TextArea) formularioAula.lookup("#taDescripcion");
            tfIp = (TextField) formularioAula.lookup("#tfIp");
            btGuardar = (Button) formularioAula.lookup("#btGuardar");
            btLimpiar = (Button) formularioAula.lookup("#btLimpiar");
        }

        spPabellon.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5));
        spPiso.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3));
        spAula.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));

        if (nuevaAula != null) {
            this.aula = nuevaAula;
            cargarDatos();
        }
    }

    /**
     * Carga los datos del aula en el formulario.
     */
    private void cargarDatos() {
        if (aula != null) {
            String numeracion = aula.getNumeracion();
            String[] partes = numeracion.split("\\.");
            spPabellon.getValueFactory().setValue(Integer.parseInt(partes[0]));
            spPiso.getValueFactory().setValue(Integer.parseInt(partes[1]));
            spAula.getValueFactory().setValue(Integer.parseInt(partes[2]));
            taDescripcion.setText(aula.getDescripcion());
            tfIp.setText(aula.getIp());
        }
    }

    /**
     * Guarda los datos del formulario en la base de datos.
     */

    @FXML
    private void guardarDatos() {
        CrudAula crudAula = new CrudAula();
        if (!verificarCampos()) {

            return;
        }

        if (aula == null) {
            aula = new Aula();
            aula.setNumeracion(spPabellon.getValue() + "." + spPiso.getValue() + "." + spAula.getValue());
            aula.setDescripcion(taDescripcion.getText());
            aula.setIp(tfIp.getText());
            crudAula.crearAula(aula);
        } else {
            aula.setNumeracion(spPabellon.getValue() + "." + spPiso.getValue() + "." + spAula.getValue());
            aula.setDescripcion(taDescripcion.getText());
            aula.setIp(tfIp.getText());
            crudAula.actualizarAula(aula.getId(), aula);
        }
        Stage stage = (Stage) spPabellon.getScene().getWindow();
        stage.close();
    }

    /**
     * Verifica que los campos obligatorios no estén vacíos.
     *
     * @return true si los campos obligatorios no están vacíos, false en caso contrario.
     */
    private boolean verificarCampos() {
        StringBuilder mensaje = new StringBuilder();
        if (taDescripcion.getText().isEmpty()) {
            mensaje.append("La descripción es obligatoria.\n");
        }
        if (tfIp.getText().isEmpty()) {
            mensaje.append("La dirección IP es obligatoria.\n");
        }
        if (tfIp.getText().length() > 15) {
            mensaje.append("La dirección IP no puede tener más de 15 caracteres.\n");
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
     * Limpia los campos del formulario.
     */
    @FXML
    private void limpiar() {
        System.out.println(aula);
        spPabellon.getValueFactory().setValue(1);
        spPiso.getValueFactory().setValue(1);
        spAula.getValueFactory().setValue(1);
        taDescripcion.setText("");
        tfIp.setText("");
    }

    /**
     * Muestra un mensaje de ayuda.
     */
    @FXML
    private void mostrarAyuda() {
        if (aula == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setContentText("En este formulario se puede crear un aula. \n"
                    + "Para crear un aula, se debe ingresar la numeración del pabellón, piso y aula, la descripción y la dirección IP. \n"
                    + "Para guardar los cambios, se debe hacer clic en el botón Guardar.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setContentText("En este formulario se puede modificar un aula. \n"
                    + "Para modificar un aula, se debe ingresar la numeración del pabellón, piso y aula, la descripción y la dirección IP. \n"
                    + "Para guardar los cambios, se debe hacer clic en el botón Guardar.");
            alert.showAndWait();
        }
    }

    /**
     * Cierra la ventana.
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
                Stage stage = (Stage) spPabellon.getScene().getWindow();
                stage.close();
            }
        });
    }
}
