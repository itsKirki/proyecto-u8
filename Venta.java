import java.util.Date;

public class Venta {
    private int folioTicket;
    private Date fechaHora;
    private double total;
    private String metodoPago;
    private Producto[] productos;
    

    public Venta(int folioTicket, Date fechaHora, double total, String metodoPago, Producto[] productos) {
        this.folioTicket = folioTicket;
        this.fechaHora = fechaHora;
        this.total = total;
        this.metodoPago = metodoPago;
        this.productos = productos;
    }   

    public int getFolioTicket() {
        return folioTicket;
    }

    public void setFolioTicket(int folioTicket) {
        this.folioTicket = folioTicket;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Producto[] getProductos() {
        return productos;
    }

    public void setProductos(Producto[] productos) {
        this.productos = productos;
    }
}
