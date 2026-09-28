package vista;

import dao.RepartidorDAO;
import modelo.Repartidor;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroRepartidor extends JFrame {

    private RepartidorDAO repartidorDAO;

    private JTextField campoNombre;

    public VentanaRegistroRepartidor(RepartidorDAO repartidorDAO) {
        this.repartidorDAO = repartidorDAO;

        setTitle("Registrar Repartidor");
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panel.add(campoNombre);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarRepartidor());
        panel.add(new JLabel());
        panel.add(btnGuardar);

        add(panel, BorderLayout.CENTER);
    }

    private void guardarRepartidor() {
        String nombre = campoNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El Nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Repartidor repartidor = new Repartidor(nombre);

        if (repartidorDAO.guardar(repartidor)) {
            JOptionPane.showMessageDialog(this, "Repartidor registrado correctamente.", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
            campoNombre.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el repartidor en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
