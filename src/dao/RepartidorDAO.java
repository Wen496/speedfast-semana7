package dao;

import modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    // Inserta un repartidor y le asigna el id generado por la base de datos
    public boolean guardar(Repartidor repartidor) {
        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionBD.conectar();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, repartidor.getNombre());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                repartidor.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar el repartidor: " + e.getMessage());
            return false;
        } finally {
            ConexionBD.cerrar(con, ps, rs);
        }
    }

    // Devuelve todos los repartidores registrados
    public List<Repartidor> listarTodos() {
        String sql = "SELECT id, nombre FROM repartidor ORDER BY id";
        List<Repartidor> repartidores = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionBD.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                repartidores.add(new Repartidor(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los repartidores: " + e.getMessage());
        } finally {
            ConexionBD.cerrar(con, ps, rs);
        }
        return repartidores;
    }
}
