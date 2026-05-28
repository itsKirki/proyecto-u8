public class Producto {
    private String codigoBarras;
    private String nombre;
    private String descripcion;
    private String categoria;
    private double precio;  
    private int stock;

    public Producto() {
        this.codigoBarras = "0000000000000";
        this.nombre = "Default";
        this.descripcion = "Default description";
        this.precio = 0.0;
        this.stock = 0;
    }

    public Producto(String codigoBarras, String nombre, String descripcion, double precio, int stock) {
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio >= 0 ? precio : 0;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock >= 0 ? stock : 0;
    }
}
