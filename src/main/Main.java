package main;

import dao.ConexionBD;
import vista.VentanaPrincipal;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            verificarConexion();
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }

    // Prueba la conexion al iniciar para avisar de inmediato si hay un problema
    private static void verificarConexion() {
        Connection con = null;
        try {
            con = ConexionBD.conectar();
            System.out.println("Conexion exitosa a speedfast_db.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar a la base de datos:\n" + e.getMessage(),
                    "Error de conexion", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }
}
