package creadorDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class ejecutarParaCrearLaDB {
    //Hola sofia, te lo pongo facil, solo tienes que ejecutar este archivo y se creara la base de datos
    //Con algunas entradas ya creadas para que puedas probar el programa sin lios

    public static void main(String[] args) {
        String rutaArchivo = "inventario.sql"; // Ruta a tu archivo SQL
        String url = "jdbc:mysql://localhost:3306/inventarioEsther";
        String usuario = "root"; // Cambia esto por tu usuario
        String contraseña = ""; // Cambia esto por tu contraseña

        crearDB(rutaArchivo, url, usuario, contraseña);
    }

    private static void crearDB(String rutaArchivo, String url, String usuario, String contraseña) {
        try (Connection conn = DriverManager.getConnection(url, usuario, contraseña);
             Statement stmt = conn.createStatement();
             BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            StringBuilder sql = new StringBuilder();
            String linea;
            boolean inTrigger = false;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("--") || linea.startsWith("#")) {
                    continue;
                }

                if (linea.toUpperCase().startsWith("BEGIN")) {
                    inTrigger = true;
                }

                sql.append(linea).append(" ");

                if (linea.endsWith(";") && !inTrigger) {
                    stmt.execute(sql.toString());
                    sql.setLength(0);
                }

                if (linea.toUpperCase().endsWith("END;")) {
                    inTrigger = false;
                    stmt.execute(sql.toString());
                    sql.setLength(0);
                }
            }

            System.out.println("Archivo SQL ejecutado exitosamente.");

        } catch (IOException e) {
            System.out.println("Error al leer el archivo SQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al ejecutar SQL: " + e.getMessage());
        }
    }
}
