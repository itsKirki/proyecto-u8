
import java.util.InputMismatchException;
import java.util.Scanner;

public class Producto {

    Scanner lee = new Scanner(System.in);

    private int codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private double precio;
    private int stock;
    private static int tmp = 100;

    public Producto() {
        this.codigo = tmp++;
        this.nombre = "Default";
        this.descripcion = "Default description";
        this.precio = 0.0;
        this.stock = 0;
    }

    public Producto(int codigo, String nombre, String descripcion, double precio, int stock) {
        this.codigo = tmp++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo >= 0 ? codigo : 0;
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

    public void agregarProducto() {
        String n, d, c;
        double p = 10.0;
        int s = 0;
        boolean pass = false;

        System.out.print("Escribe el nombre del producto: ");
        n = lee.nextLine();
        System.out.print("Escribe la descripcion del producto: ");
        d = lee.nextLine();
        System.out.print("Escribe la categoria del producto: ");
        c = lee.nextLine();

        System.out.print("Escribe el precio del producto: ");
        do {
            pass = false;
            try {
                p = lee.nextDouble();
                if (p <= 0) {
                    System.err.println("Error: El precio debe ser mayor a 0.");
                } else {
                    pass = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Precio debe ser un número.");
                lee.nextLine();
            }
        } while (!pass);

        System.out.print("Escribe el stock del producto: ");
        do {
            pass = false;
            try {
                s = lee.nextInt();
                if (s < 0) {
                    System.err.println("Error: El stock debe ser mayor o igual a 0.");
                } else {
                    pass = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Stock debe ser un número.");
                lee.nextLine();
            }
        } while (!pass);

        setNombre(n);
        setDescripcion(d);
        setCategoria(c);
        setPrecio(p);
        setStock(s);
    }

    public String formato() {
        return String.format("%-8d|%-20s|%-30s|%-15s|%-10.2f|%-8d", 
                this.codigo, 
                this.nombre, 
                this.descripcion, 
                this.categoria, 
                this.precio, 
                this.stock);
    }
}
