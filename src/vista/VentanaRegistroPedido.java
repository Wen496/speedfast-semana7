package vista;

import dao.PedidoDAO;
import modelo.Pedido;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    private PedidoDAO pedidoDAO;

    private JTextField campoDireccion;
    private JComboBox<String> comboTipo;

    public VentanaRegistroPedido(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;

        setTitle("Registrar Pedido");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Direccion:"));
        campoDireccion = new JTextField();
        panel.add(campoDireccion);

        panel.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"Comida", "Encomienda", "Express"});
        panel.add(comboTipo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarPedido());
        panel.add(new JLabel());
        panel.add(btnGuardar);

        add(panel, BorderLayout.CENTER);
    }

    private void guardarPedido() {
        String direccion = campoDireccion.getText().trim();
        String tipo = ((String) comboTipo.getSelectedItem()).toUpperCase();

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La Direccion es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pedido pedido = new Pedido(direccion, tipo);

        if (pedidoDAO.guardar(pedido)) {
            JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
            campoDireccion.setText("");
            comboTipo.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el pedido en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
