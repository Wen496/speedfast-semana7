package vista;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;
    private EntregaDAO entregaDAO;

    public VentanaPrincipal() {
        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();
        entregaDAO = new EntregaDAO();

        setTitle("SpeedFast - Gestion de Entregas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnRegistrar = new JButton("Registrar pedido");
        JButton btnRegistrarRepartidor = new JButton("Registrar repartidor");
        JButton btnListar = new JButton("Listar pedidos");
        JButton btnAsignar = new JButton("Asignar repartidor / Iniciar entrega");

        btnRegistrar.addActionListener(e -> {
            VentanaRegistroPedido ventana = new VentanaRegistroPedido(pedidoDAO);
            ventana.setVisible(true);
        });

        btnRegistrarRepartidor.addActionListener(e -> {
            VentanaRegistroRepartidor ventana = new VentanaRegistroRepartidor(repartidorDAO);
            ventana.setVisible(true);
        });

        btnListar.addActionListener(e -> {
            VentanaListaPedidos ventana = new VentanaListaPedidos(pedidoDAO);
            ventana.setVisible(true);
        });

        btnAsignar.addActionListener(e -> asignarRepartidor());

        panel.add(btnRegistrar);
        panel.add(btnRegistrarRepartidor);
        panel.add(btnListar);
        panel.add(btnAsignar);

        add(panel, BorderLayout.CENTER);
    }

    private void asignarRepartidor() {
        // Solo se pueden asignar los pedidos que siguen pendientes
        List<Pedido> pendientes = new ArrayList<>();
        for (Pedido p : pedidoDAO.listarTodos()) {
            if ("PENDIENTE".equals(p.getEstado())) {
                pendientes.add(p);
            }
        }

        if (pendientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pedidos pendientes en la base de datos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Repartidor> repartidores = repartidorDAO.listarTodos();
        if (repartidores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay repartidores registrados en la base de datos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pedido[] arregloPedidos = pendientes.toArray(new Pedido[0]);
        Pedido pedido = (Pedido) JOptionPane.showInputDialog(
                this,
                "Selecciona el pedido:",
                "Asignar repartidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                arregloPedidos,
                arregloPedidos[0]
        );

        if (pedido == null) {
            return;
        }

        Repartidor[] arregloRepartidores = repartidores.toArray(new Repartidor[0]);
        Repartidor repartidor = (Repartidor) JOptionPane.showInputDialog(
                this,
                "Selecciona el repartidor:",
                "Asignar repartidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                arregloRepartidores,
                arregloRepartidores[0]
        );

        if (repartidor == null) {
            return;
        }

        // Se registra la entrega con la fecha y hora actuales
        Entrega entrega = new Entrega(pedido.getId(), repartidor.getId(), LocalDate.now(), LocalTime.now());

        if (!entregaDAO.guardar(entrega)) {
            JOptionPane.showMessageDialog(this, "No se pudo registrar la entrega en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pedidoDAO.actualizarEstado(pedido.getId(), "EN_REPARTO")) {
            JOptionPane.showMessageDialog(this, "La entrega se registro, pero no se pudo actualizar el estado del pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Entrega iniciada: " + repartidor.getNombre() + " fue asignado al pedido " + pedido.getId(),
                "Confirmacion",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
