
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    /**
     * Busca el producto por nombre en productos.txt y descuenta la cantidad
     * indicada de su stock. Si el nombre no coincide con ningún registro,
     * avisa al usuario y no modifica el archivo.
     */
    public void descontarStock(String nombreProducto, int cantidad) {
        final String RUTA = "data/productos.txt";
        String codigoEncontrado = null;
        String nuevaLinea = null;

        // 1. Leer el archivo y buscar el producto por nombre
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                // Formato: código|nombre|descripción|categoría|precio|stock
                if (partes.length >= 6) {
                    String nombreArchivo = partes[1].trim();
                    if (nombreArchivo.equalsIgnoreCase(nombreProducto.trim())) {
                        codigoEncontrado = partes[0].trim();
                        int stockActual = 0;
                        try {
                            stockActual = Integer.parseInt(partes[5].trim());
                        } catch (NumberFormatException e) {
                            System.err.println("Error al leer el stock de '" + nombreProducto + "'.");
                            br.close();
                            return;
                        }

                        int nuevoStock = stockActual - cantidad;
                        if (nuevoStock < 0) {
                            System.err.println("Advertencia: stock insuficiente para '"
                                    + nombreProducto + "'. Stock actual: " + stockActual
                                    + ", solicitado: " + cantidad + ". No se modificó el stock.");
                            br.close();
                            return;
                        }

                        // Reconstruir la línea con el nuevo stock
                        nuevaLinea = String.format("%-20s|%-30s|%-15s|%-10s|%-8d",
                                partes[2].trim(),
                                partes[3].trim(),
                                partes[4].trim(),
                                partes[4].trim(),
                                nuevoStock);

                        // Usamos los campos originales para no perder datos
                        nuevaLinea = String.format("%-20s|%-30s|%-15s|%-10.2f|%-8d",
                                partes[1].trim(),
                                partes[2].trim(),
                                partes[3].trim(),
                                Double.parseDouble(partes[4].trim()),
                                nuevoStock);
                        break;
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error al leer productos para descontar stock: " + e.getMessage());
            return;
        }

        // 2. Si se encontró, actualizar mediante GestionarArchivos
        if (codigoEncontrado != null && nuevaLinea != null) {
            GestionarArchivos gestor = new GestionarArchivos();
            boolean actualizado = gestor.actualizarLinea(RUTA, codigoEncontrado, nuevaLinea);
            if (actualizado) {
                System.out.println("Stock de '" + nombreProducto + "' actualizado (-" + cantidad + " unidades).");
            }
        } else {
            System.out.println("Aviso: '" + nombreProducto + "' no se encontró en el catálogo. Stock sin cambios.");
        }
    }

    public String formato() {
        return String.format("%-8d|%-20s|%-30s|%-15s|%-10.2f|%-8d", 
                this.getCodigo(), 
                this.getNombre(), 
                this.getDescripcion(), 
                this.getCategoria(), 
                this.getPrecio(), 
                this.getStock());
    }
}
