package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class controladorMenuSuperior {
    private static Stage stage;

    /**
     * Método para establecer la ventana principal de la aplicación
     *
     * @param Newstage Ventana principal de la aplicación
     */
    public static void setStage(Stage Newstage) {
        stage = Newstage;
    }

    /**
     * Método para cargar la vista de las aulas
     */
    @FXML
    private void verAulas() {
        cargarVista("VerAulas.fxml", "Ver Aulas");
    }

    /**
     * Método para cargar la vista de crear una nueva aula
     */
    @FXML
    private void crearNuevaAula() {
        abrirModal("CrearNuevaAula.fxml", "Crear Nueva Aula", 1);
    }

    /**
     * Método para cargar la vista de los productos
     */
    @FXML
    private void verProductos() {
        cargarVista("VerProductos.fxml", "Ver Productos");
    }

    /**
     * Método para cargar la vista de crear un nuevo producto
     */
    @FXML
    private void crearNuevoProducto() {
        abrirModal("crearNuevoProducto.fxml", "Crear Nuevo Producto", 4);
    }

    /**
     * Método para cargar la vista de los marcajes
     */
    @FXML
    private void verMarcajes() {
        cargarVista("VerMarcajes.fxml", "Ver Marcajes");
    }

    /**
     * Método para cargar la vista de crear un nuevo marcaje
     */
    @FXML
    private void crearNuevoMarcaje() {
        abrirModal("crearMarcaje.fxml", "Crear Nuevo Marcaje", 2);
    }

    /**
     * Método para cargar la vista de las categorías
     */
    @FXML
    private void verCategoria() {
        cargarVista("VerCategoria.fxml", "Ver Categorías");
    }

    /**
     * Método para cargar la vista de crear una nueva categoría
     */
    @FXML
    private void crearNuevaCategoria() {
        abrirModal("CrearNuevaCategoria.fxml", "Crear Nueva Categoría", 3);
    }

    @FXML
    private void modificarCategoria() {
        abrirModal("ModificarCategoria.fxml", "Modificar Categoría", 3);
    }

    /**
     * Método para cargar la vista de los usuarios
     */
    private void cargarVista(String archivoFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/vistas/" + archivoFXML));
            Scene scene = new Scene(loader.load());
            Scene currentScene = stage.getScene();
            if (currentScene != null) {
                for (String stylesheet : currentScene.getStylesheets()) {
                    scene.getStylesheets().add(stylesheet);
                }
            }
            if (scene.getStylesheets().isEmpty()) {
                scene.getStylesheets().add(getClass().getResource("/org/example/css/diurno.css").toExternalForm());
            }
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para abrir un modal
     *
     * @param archivoFXML Archivo FXML del modal
     * @param titulo      Título del modal
     * @param tipo        Tipo de accion (1 - Crear Aula, 2 - Crear Marcaje, 3 - Crear Categoria, 4 - Crear Producto)
     */
    private void abrirModal(String archivoFXML, String titulo, int tipo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/vistas/" + archivoFXML));
            AnchorPane root = loader.load();
            Object controlador = loader.getController();

            switch (tipo) {
                case 1: {
                    controladorFormularioAula controladorFormularioAula = (controladorFormularioAula) controlador;
                    controladorFormularioAula.recibirAula(null);
                    break;
                }
                case 2:
                    break;
                case 3: {
                    controladorFormularioCategoria controladorFormularioCategoria = (controladorFormularioCategoria) controlador;
                    controladorFormularioCategoria.recibirCategoria(null);
                    break;
                }
                case 4: {
                    controladorFormularioProducto controladorFormularioProducto = (controladorFormularioProducto) controlador;
                    controladorFormularioProducto.recibirProducto(null);
                    break;
                }
            }

            Scene scene = new Scene(root);
            Scene currentScene = stage.getScene();
            if (currentScene != null) {
                for (String stylesheet : currentScene.getStylesheets()) {
                    scene.getStylesheets().add(stylesheet);  // Añadir los estilos a la nueva escena
                }
            }
            if (scene.getStylesheets().isEmpty()) {
                scene.getStylesheets().add(getClass().getResource("/org/example/css/diurno.css").toExternalForm());
            }
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para establecer el modo claro
     */
    @FXML
    private void ponerModoClaro() {
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("/org/example/css/diurno.css").toExternalForm());
    }

    /**
     * Método para establecer el modo oscuro
     */
    @FXML
    private void ponerModoOscuro() {
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("/org/example/css/nocturno.css").toExternalForm());
    }

    @FXML
    private void abrirAyuda() {
        Stage helpStage = new Stage();
        WebView webView = new WebView();

        File ayuda = new File("src/main/resources/Ayuda/ayudaCrudAula.html");
        if (ayuda.exists()) {
            webView.getEngine().load(ayuda.toURI().toString());
        } else {
            System.out.println("No se encontro el arcihvo.");
        }

        Scene helpScene = new Scene(webView, 800, 600);
        helpStage.setTitle("Ayuda");
        helpStage.setScene(helpScene);
        helpStage.show();
    }

    @FXML
    private void salir(){
        System.exit(0);
    }
}