package vista;

import dao.PedidoDAO;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaPedidos extends JFrame {

    private PedidoDAO pedidoDAO;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public VentanaListaPedidos(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;

        setTitle("Listado de Pedidos");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnas = {"ID", "Direccion", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarDatos());

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnRefrescar);

        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        cargarDatos();
    }

    // Lee los pedidos desde la base de datos y los muestra en la tabla
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Pedido> pedidos = pedidoDAO.listarTodos();

        for (Pedido p : pedidos) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getDireccion(),
                    p.getTipo(),
                    p.getEstado()
            });
        }
    }
}
