package dao;

import modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // Inserta un pedido y le asigna el id generado por la base de datos
    public boolean guardar(Pedido pedido) {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionBD.conectar();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipo());
            ps.setString(3, pedido.getEstado());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                pedido.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar el pedido: " + e.getMessage());
            return false;
        } finally {
            ConexionBD.cerrar(con, ps, rs);
        }
    }

    // Lista todos los pedidos junto al ultimo repartidor asignado (si tiene)
    public List<Pedido> listarTodos() {
        String sql = "SELECT p.id, p.direccion, p.tipo, p.estado, "
                + "(SELECT r.nombre FROM entrega e "
                + "JOIN repartidor r ON r.id = e.id_repartidor "
                + "WHERE e.id_pedido = p.id ORDER BY e.id DESC LIMIT 1) AS repartidor "
                + "FROM pedido p ORDER BY p.id";
        List<Pedido> pedidos = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionBD.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String repartidor = rs.getString("repartidor");
                pedidos.add(new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getString("estado"),
                        repartidor != null ? repartidor : "Sin asignar"
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los pedidos: " + e.getMessage());
        } finally {
            ConexionBD.cerrar(con, ps, rs);
        }
        return pedidos;
    }

    // Cambia el estado de un pedido (por ejemplo, a EN_REPARTO)
    public boolean actualizarEstado(int idPedido, String nuevoEstado) {
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionBD.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado del pedido: " + e.getMessage());
            return false;
        } finally {
            ConexionBD.cerrar(con, ps, null);
        }
    }
}
