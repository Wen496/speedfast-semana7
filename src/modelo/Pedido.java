package modelo;

public class Pedido {

    private int id;
    private String direccion;
    private String tipo;
    private String estado;
    private String repartidorAsignado;

    // Constructor para un pedido nuevo: el id lo asigna la base de datos
    public Pedido(String direccion, String tipo) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = "PENDIENTE";
        this.repartidorAsignado = "Sin asignar";
    }

    // Constructor para un pedido leido desde la base de datos
    public Pedido(int id, String direccion, String tipo, String estado, String repartidorAsignado) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
        this.repartidorAsignado = repartidorAsignado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public void setRepartidorAsignado(String repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    @Override
    public String toString() {
        return "Pedido " + id + " - " + direccion + " (" + tipo + ")";
    }
}
