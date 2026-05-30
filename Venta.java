import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Venta {

    private int folioTicket;
    private Date fechaHora;
    private double total;
    private String metodoPago;
    private Producto[] productos;
    private static int tmp = 1000;

    private static final String RUTA_FACTURAS = "data/facturas.txt";
    private static final String SEPARADOR = "========================================";

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

    // ─── Métodos de factura ───────────────────────────────────────────────────

    /**
     * Solicita datos al usuario y guarda la factura en data/facturas.txt.
     * Recibe el nombre de la tienda para incluirlo en la factura.
     */
    public void crearFactura(String nombreTienda) {
        Scanner lee = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        System.out.println("\n--- CREAR NUEVA FACTURA ---");

        // Nombre del cliente
        System.out.print("Ingrese el nombre del cliente: ");
        String nombreCliente = lee.nextLine();

        // Nombre del empleado
        System.out.print("Ingrese el nombre del empleado que atiende: ");
        String nombreEmpleado = lee.nextLine();

        // Método de pago
        System.out.print("Ingrese el método de pago (Efectivo / Tarjeta / Transferencia): ");
        String metodo = lee.nextLine();
        this.setMetodoPago(metodo);

        // Mostrar productos existentes del catálogo
        System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
        System.out.printf("%-8s|%-20s|%-15s|%-10s|%-8s%n", "Código", "Nombre", "Categoría", "Precio", "Stock");
        System.out.println("------------------------------------------------------------");
        try {
            BufferedReader brProd = new BufferedReader(new FileReader("data/productos.txt"));
            String lineaProd;
            while ((lineaProd = brProd.readLine()) != null) {
                System.out.println(lineaProd);
            }
            brProd.close();
        } catch (IOException ex) {
            System.err.println("Advertencia: No se pudo leer el catálogo de productos: " + ex.getMessage());
        }
        System.out.println("------------------------------------------------------------");

        // Captura de productos uno por uno
        java.util.ArrayList<Producto> lista = new java.util.ArrayList<>();
        double totalCalculado = 0.0;
        boolean agregarMas = true;
        boolean pass = false;
        int contadorProd = 1;

        while (agregarMas) {
            System.out.println("\n-- Producto #" + contadorProd + " --");
            String nombreProd;
            double precioProd = 0.0;
            int cantidadProd = 0;

            // Pedir nombre y buscar precio automáticamente en el catálogo
            boolean productoValido = false;
            do {
                System.out.print("Nombre del producto: ");
                nombreProd = lee.nextLine();

                // Buscar el producto en productos.txt
                try {
                    BufferedReader brBuscar = new BufferedReader(new FileReader("data/productos.txt"));
                    String lineaBuscar;
                    while ((lineaBuscar = brBuscar.readLine()) != null) {
                        String[] partes = lineaBuscar.split("\\|");
                        // Formato: código|nombre|descripción|categoría|precio|stock
                        if (partes.length >= 6 && partes[1].trim().equalsIgnoreCase(nombreProd.trim())) {
                            precioProd = Double.parseDouble(partes[4].trim());
                            productoValido = true;
                            System.out.printf("Precio registrado: $%.2f%n", precioProd);
                            break;
                        }
                    }
                    brBuscar.close();
                } catch (IOException ex) {
                    System.err.println("Error al buscar el producto en el catálogo: " + ex.getMessage());
                    break;
                } catch (NumberFormatException ex) {
                    System.err.println("Error al leer el precio del producto en el catálogo.");
                    break;
                }

                if (!productoValido) {
                    System.err.println("Producto '" + nombreProd + "' no encontrado en el catálogo. Intente de nuevo.");
                }
            } while (!productoValido);

            pass = false;
            do {
                System.out.print("Cantidad: ");
                try {
                    cantidadProd = lee.nextInt();
                    if (cantidadProd <= 0) {
                        System.err.println("La cantidad debe ser mayor a 0.");
                    } else {
                        pass = true;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Entrada inválida. Ingrese un número entero.");
                    lee.nextLine();
                }
            } while (!pass);
            lee.nextLine();

            // Guardar producto usando stock para almacenar la cantidad
            Producto prod = new Producto();
            prod.setNombre(nombreProd);
            prod.setPrecio(precioProd);
            prod.setStock(cantidadProd);
            lista.add(prod);
            totalCalculado += precioProd * cantidadProd;
            contadorProd++;

            // Preguntar si desea agregar otro producto
            int opcContinuar = 0;
            pass = false;
            do {
                System.out.println("\n¿Desea agregar otro producto?");
                System.out.println("1. Sí, agregar otro producto");
                System.out.println("2. No, continuar con la factura");
                try {
                    opcContinuar = lee.nextInt();
                    if (opcContinuar != 1 && opcContinuar != 2) {
                        System.err.println("Opción no válida. Ingrese 1 o 2.");
                    } else {
                        pass = true;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Entrada inválida. Ingrese 1 o 2.");
                    lee.nextLine();
                }
            } while (!pass);
            lee.nextLine();

            agregarMas = (opcContinuar == 1);
        }

        Producto[] listaProductos = lista.toArray(new Producto[0]);

        this.setProductos(listaProductos);
        this.setTotal(totalCalculado);
        this.setFechaHora(new Date());

        // Escribir en el archivo
        try {
            java.io.File archivo = new java.io.File(RUTA_FACTURAS);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));

            pw.println(SEPARADOR);
            pw.println("         TIENDA: " + nombreTienda.toUpperCase());
            pw.println(SEPARADOR);
            pw.printf("%-18s: %s%n", "Folio",          this.folioTicket);
            pw.printf("%-18s: %s%n", "Fecha y hora",   sdf.format(this.fechaHora));
            pw.printf("%-18s: %s%n", "Cliente",        nombreCliente);
            pw.printf("%-18s: %s%n", "Empleado",       nombreEmpleado);
            pw.printf("%-18s: %s%n", "Método de pago", this.metodoPago);
            pw.println("------------------------------------------");
            pw.printf("%-25s %-10s %-8s %-12s%n", "Producto", "P.Unit.", "Cant.", "Subtotal");
            pw.println("------------------------------------------");

            for (Producto p : listaProductos) {
                double subtotal = p.getPrecio() * p.getStock();
                pw.printf("%-25s $%-9.2f %-8d $%-11.2f%n",
                        p.getNombre(), p.getPrecio(), p.getStock(), subtotal);
            }

            pw.println("------------------------------------------");
            pw.printf("%-44s $%-11.2f%n", "TOTAL:", this.total);
            pw.println(SEPARADOR);
            pw.println(); // línea en blanco entre facturas
            pw.close();

            System.out.println("\nFactura #" + this.folioTicket + " guardada exitosamente en " + RUTA_FACTURAS);

            // Descontar stock de cada producto en el catálogo
            Producto gestor = new Producto();
            for (Producto p : listaProductos) {
                gestor.descontarStock(p.getNombre(), p.getStock());
            }

        } catch (IOException e) {
            System.err.println("Error al guardar la factura: " + e.getMessage());
        }
    }

    //Lee e imprime todas las facturas almacenadas en data/facturas.txt
    public void mostrarFacturas() {
        java.io.File archivo = new java.io.File(RUTA_FACTURAS);

        if (!archivo.exists()) {
            System.out.println("No existen facturas registradas todavía.");
            return;
        }

        System.out.println("\n============ FACTURAS REGISTRADAS ============");
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de facturas: " + e.getMessage());
        }
    }
}
