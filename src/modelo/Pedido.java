package modelo;

public class Pedido {

    private int id;
    private String direccion;
    private String tipo;
    private String estado;

    // Constructor para un pedido nuevo: el id lo asigna la base de datos
    public Pedido(String direccion, String tipo) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = "PENDIENTE";
    }

    // Constructor para un pedido leido desde la base de datos
    public Pedido(int id, String direccion, String tipo, String estado) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Pedido " + id + " - " + direccion + " (" + tipo + ")";
    }
}
