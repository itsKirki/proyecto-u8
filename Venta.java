import java.util.Date;

public class Venta {
    private int folioTicket;
    private Date fechaHora;
    private double total;
    private String metodoPago;
    private Producto[] productos;
    private static int tmp = 1000;
    
    public Venta() {
        this.folioTicket = tmp++;
        this.fechaHora = new Date();
        this.total = 0.0;
        this.metodoPago = "Default";
        this.productos = new Producto[0];
    }

    public Venta(int folioTicket, Date fechaHora, double total, String metodoPago, Producto[] productos) {
        this.folioTicket = tmp++;
        this.fechaHora = fechaHora;
        this.total = total;
        this.metodoPago = metodoPago;
        this.productos = productos;
    }   

    public int getFolioTicket() {
        return folioTicket;
    }

    public void setFolioTicket(int folioTicket) {
        this.folioTicket = folioTicket >= 0 ? folioTicket : 0;
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
        this.total = total >= 0 ? total : 0;
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
