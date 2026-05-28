
public class Tienda {

    private String nombre;
    private String sucursal;
    private String direccion;

    public Tienda(String nombre, String sucursal, String direccion) {
        this.nombre = nombre;
        this.sucursal = sucursal;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
