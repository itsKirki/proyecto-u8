import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private static final String RUTA_FACTURAS  = "data/facturas.txt";
    private static final String RUTA_PRODUCTOS = "data/productos.txt";
    private static final String RUTA_CLIENTES  = "data/clientes.txt";
    private static final String SEPARADOR      = "========================================";

    public Venta() {
        this.folioTicket = tmp++;
        this.fechaHora   = new Date();
        this.total       = 0.0;
        this.metodoPago  = "Default";
        this.productos   = new Producto[0];
    }

    public Venta(int folioTicket, Date fechaHora, double total, String metodoPago, Producto[] productos) {
        this.folioTicket = tmp++;
        this.fechaHora   = fechaHora;
        this.total       = total;
        this.metodoPago  = metodoPago;
        this.productos   = productos;
    }

    public int        getFolioTicket()  { return folioTicket; }
    public Date       getFechaHora()    { return fechaHora; }
    public double     getTotal()        { return total; }
    public String     getMetodoPago()   { return metodoPago; }
    public Producto[] getProductos()    { return productos; }

    public void setFolioTicket(int v)       { this.folioTicket = v >= 0 ? v : 0; }
    public void setFechaHora(Date v)        { this.fechaHora   = v; }
    public void setTotal(double v)          { this.total       = v >= 0 ? v : 0; }
    public void setMetodoPago(String v)     { this.metodoPago  = v; }
    public void setProductos(Producto[] v)  { this.productos   = v; }

    // ─── Parsing tolerante: acepta | (formato nuevo) y espacios (formato original) ──
    private String[] parsearLinea(String linea) {
        String[] partes = linea.split("\\|");
        if (partes.length >= 2) return partes;       // separador | encontrado
        return linea.trim().split("\\s{2,}");        // fallback: 2 o más espacios
    }

    // ─── Buscar producto por nombre o código; ignora los que tienen stock <= 0 ──
    // Devuelve {nombre, precio, stock} o null si no se encuentra o sin stock.
    private String[] buscarProducto(String entrada) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA_PRODUCTOS));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = parsearLinea(linea);
                // Formato: código | nombre | descripción | categoría | precio | stock
                if (p.length < 6) continue;
                String codigo = p[0].trim();
                String nombre = p[1].trim();
                int    stock;
                try { stock = Integer.parseInt(p[5].trim()); }
                catch (NumberFormatException e) { continue; }
                if (stock <= 0) continue;
                if (codigo.equalsIgnoreCase(entrada.trim()) ||
                    nombre.equalsIgnoreCase(entrada.trim())) {
                    br.close();
                    return new String[]{ nombre, p[4].trim(), String.valueOf(stock) };
                }
            }
            br.close();
        } catch (IOException ex) {
            System.err.println("Error al buscar producto: " + ex.getMessage());
        }
        return null;
    }

    // ─── Buscar cliente por nombre ───────────────────────────────────────────
    // Devuelve {codigo, nombre, puntos} o null si no existe.
    private String[] buscarCliente(String nombreBuscar) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA_CLIENTES));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] c = parsearLinea(linea);
                // Formato: id | nombre | telefono | email | puntos | rfc | fechaRegistro | tipoCliente
                if (c.length < 5) continue;
                if (c[1].trim().equalsIgnoreCase(nombreBuscar.trim())) {
                    br.close();
                    return new String[]{ c[0].trim(), c[1].trim(), c[4].trim() };
                }
            }
            br.close();
        } catch (IOException ex) {
            System.err.println("Error al buscar cliente: " + ex.getMessage());
        }
        return null;
    }

    // ─── Actualizar puntos de un cliente en clientes.txt ────────────────────
    private void actualizarPuntosCliente(String codigoCliente, int nuevosPuntos) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA_CLIENTES));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] c = parsearLinea(linea);
                if (c.length >= 8 && c[0].trim().equals(codigoCliente)) {
                    String nuevaLinea = String.format("%-20s|%-15s|%-25s|%-10d|%-15s|%-30s|%-15s",
                            c[1].trim(), c[2].trim(), c[3].trim(),
                            nuevosPuntos,
                            c[5].trim(), c[6].trim(), c[7].trim());
                    GestionarArchivos gestor = new GestionarArchivos();
                    gestor.actualizarLinea(RUTA_CLIENTES, codigoCliente, nuevaLinea);
                    break;
                }
            }
            br.close();
        } catch (IOException ex) {
            System.err.println("Error al actualizar puntos del cliente: " + ex.getMessage());
        }
    }

    // ─── Registrar cliente nuevo con placeholders ────────────────────────────
    private void registrarClienteNuevo(String nombre) {
        try {
            java.io.File archivo = new java.io.File(RUTA_CLIENTES);
            if (!archivo.exists()) archivo.createNewFile();
            int nuevoId = (int)(System.currentTimeMillis() % 9000) + 1000;
            PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));
            pw.printf("%-8d|%-20s|%-15s|%-25s|%-10d|%-15s|%-30s|%-15s%n",
                    nuevoId, nombre, "N/A", "N/A", 0, "N/A", new Date().toString(), "Regular");
            pw.close();
            System.out.println("Cliente nuevo registrado con nombre '" + nombre + "'.");
        } catch (IOException ex) {
            System.err.println("Error al registrar cliente nuevo: " + ex.getMessage());
        }
    }

    // ─── crearFactura ────────────────────────────────────────────────────────
    public void crearFactura(String nombreTienda, Scanner lee) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        System.out.println("\n--- CREAR NUEVA FACTURA ---");

        // ── Buscar o registrar cliente ────────────────────────────────────────
        System.out.print("Ingrese el nombre del cliente: ");
        String nombreCliente = lee.nextLine();

        double descuentoPuntos = 0.0;
        String[] datosCliente = buscarCliente(nombreCliente);

        if (datosCliente != null) {
            nombreCliente = datosCliente[1];
            int puntosActuales = 0;
            try { puntosActuales = Integer.parseInt(datosCliente[2].trim()); }
            catch (NumberFormatException ignored) {}

            System.out.println("Cliente encontrado: " + nombreCliente);

            if (puntosActuales > 0) {
                System.out.println("Puntos de fidelidad disponibles: " + puntosActuales);
                int usarPuntos = 0;
                boolean pass = false;
                do {
                    System.out.println("¿Desea usar puntos de fidelidad? (1 punto = $1.00 de descuento)");
                    System.out.println("1. Si   2. No");
                    try {
                        usarPuntos = lee.nextInt();
                        if (usarPuntos != 1 && usarPuntos != 2)
                            System.err.println("Ingrese 1 o 2.");
                        else pass = true;
                    } catch (InputMismatchException ex) {
                        System.out.println("Entrada invalida. Ingrese 1 o 2.");
                        lee.nextLine();
                    }
                } while (!pass);
                lee.nextLine();

                if (usarPuntos == 1) {
                    int puntosAUsar = 0;
                    pass = false;
                    do {
                        System.out.print("Cuantos puntos desea usar? (max. " + puntosActuales + "): ");
                        try {
                            puntosAUsar = lee.nextInt();
                            if (puntosAUsar <= 0)
                                System.err.println("Debe usar al menos 1 punto.");
                            else if (puntosAUsar > puntosActuales)
                                System.err.println("No puede usar mas puntos de los que tiene (" + puntosActuales + ").");
                            else pass = true;
                        } catch (InputMismatchException ex) {
                            System.out.println("Entrada invalida. Ingrese un numero entero.");
                            lee.nextLine();
                        }
                    } while (!pass);
                    lee.nextLine();

                    descuentoPuntos = puntosAUsar;
                    int puntosRestantes = puntosActuales - puntosAUsar;
                    actualizarPuntosCliente(datosCliente[0], puntosRestantes);
                    System.out.printf("Descuento aplicado: $%.2f. Puntos restantes: %d%n",
                            descuentoPuntos, puntosRestantes);
                }
            } else {
                System.out.println("El cliente no tiene puntos de fidelidad disponibles.");
            }
        } else {
            System.out.println("Cliente no encontrado. Se registrara como cliente nuevo.");
            registrarClienteNuevo(nombreCliente);
        }

        // ── Empleado ──────────────────────────────────────────────────────────
        System.out.print("Ingrese el nombre del empleado que atiende: ");
        String nombreEmpleado = lee.nextLine();

        // ── Metodo de pago ────────────────────────────────────────────────────
        System.out.print("Ingrese el metodo de pago (Efectivo / Tarjeta / Transferencia): ");
        this.setMetodoPago(lee.nextLine());

        // ── Mostrar catalogo (solo productos con stock > 0) ───────────────────
        System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
        System.out.printf("%-8s|%-20s|%-30s|%-15s|%-10s|%-8s%n",
                "Codigo", "Nombre", "Descripcion", "Categoria", "Precio", "Stock");
        System.out.println("--------------------------------------------------------------------------------------");
        try {
            BufferedReader brProd = new BufferedReader(new FileReader(RUTA_PRODUCTOS));
            String lineaProd;
            while ((lineaProd = brProd.readLine()) != null) {
                String[] p = parsearLinea(lineaProd);
                if (p.length >= 6) {
                    try {
                        if (Integer.parseInt(p[5].trim()) > 0)
                            System.out.println(lineaProd);
                    } catch (NumberFormatException ignored) {}
                }
            }
            brProd.close();
        } catch (IOException ex) {
            System.err.println("Advertencia: No se pudo leer el catalogo: " + ex.getMessage());
        }
        System.out.println("--------------------------------------------------------------------------------------");

        // ── Captura de productos ──────────────────────────────────────────────
        ArrayList<Producto> lista = new ArrayList<>();
        double totalCalculado = 0.0;
        boolean agregarMas   = true;
        boolean pass         = false;
        int contadorProd     = 1;

        while (agregarMas) {
            System.out.println("\n-- Producto #" + contadorProd + " --");

            // Elegir metodo de busqueda
            int modoBusqueda = 0;
            pass = false;
            do {
                System.out.println("Buscar producto por:");
                System.out.println("1. Nombre   2. Codigo");
                try {
                    modoBusqueda = lee.nextInt();
                    if (modoBusqueda != 1 && modoBusqueda != 2)
                        System.err.println("Ingrese 1 o 2.");
                    else pass = true;
                } catch (InputMismatchException ex) {
                    System.out.println("Entrada invalida. Ingrese 1 o 2.");
                    lee.nextLine();
                }
            } while (!pass);
            lee.nextLine();

            // Buscar hasta encontrar un producto valido con stock
            String[] datosProducto = null;
            do {
                System.out.print(modoBusqueda == 1 ? "Nombre del producto: " : "Codigo del producto: ");
                String entrada = lee.nextLine();
                datosProducto = buscarProducto(entrada);
                if (datosProducto == null)
                    System.err.println("Producto no encontrado o sin stock disponible. Intente de nuevo.");
            } while (datosProducto == null);

            String nombreProd = datosProducto[0];
            double precioProd = Double.parseDouble(datosProducto[1]);
            int    stockDisp  = Integer.parseInt(datosProducto[2]);

            System.out.printf("Producto: %s  |  Precio: $%.2f  |  Stock disponible: %d%n",
                    nombreProd, precioProd, stockDisp);

            // Cantidad (validada contra stock disponible)
            int cantidadProd = 0;
            pass = false;
            do {
                System.out.print("Cantidad: ");
                try {
                    cantidadProd = lee.nextInt();
                    if (cantidadProd <= 0)
                        System.err.println("La cantidad debe ser mayor a 0.");
                    else if (cantidadProd > stockDisp)
                        System.err.println("Stock insuficiente. Disponible: " + stockDisp + ".");
                    else pass = true;
                } catch (InputMismatchException ex) {
                    System.out.println("Entrada invalida. Ingrese un numero entero.");
                    lee.nextLine();
                }
            } while (!pass);
            lee.nextLine();

            Producto prod = new Producto();
            prod.setNombre(nombreProd);
            prod.setPrecio(precioProd);
            prod.setStock(cantidadProd);   // stock reutilizado para cantidad comprada
            lista.add(prod);
            totalCalculado += precioProd * cantidadProd;
            contadorProd++;

            // Agregar otro?
            int opcContinuar = 0;
            pass = false;
            do {
                System.out.println("\nDesea agregar otro producto?");
                System.out.println("1. Si, agregar otro producto");
                System.out.println("2. No, continuar con la factura");
                try {
                    opcContinuar = lee.nextInt();
                    if (opcContinuar != 1 && opcContinuar != 2)
                        System.err.println("Opcion no valida. Ingrese 1 o 2.");
                    else pass = true;
                } catch (InputMismatchException ex) {
                    System.out.println("Entrada invalida. Ingrese 1 o 2.");
                    lee.nextLine();
                }
            } while (!pass);
            lee.nextLine();

            agregarMas = (opcContinuar == 1);
        }

        Producto[] listaProductos = lista.toArray(new Producto[0]);

        double totalFinal = totalCalculado - descuentoPuntos;
        if (totalFinal < 0) totalFinal = 0;

        this.setProductos(listaProductos);
        this.setTotal(totalFinal);
        this.setFechaHora(new Date());

        // ── Escribir factura ──────────────────────────────────────────────────
        try {
            java.io.File archivo = new java.io.File(RUTA_FACTURAS);
            if (!archivo.exists()) archivo.createNewFile();

            PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));

            pw.println(SEPARADOR);
            pw.println("         TIENDA: " + nombreTienda.toUpperCase());
            pw.println(SEPARADOR);
            pw.printf("%-18s: %s%n",  "Folio",          this.folioTicket);
            pw.printf("%-18s: %s%n",  "Fecha y hora",   sdf.format(this.fechaHora));
            pw.printf("%-18s: %s%n",  "Cliente",        nombreCliente);
            pw.printf("%-18s: %s%n",  "Empleado",       nombreEmpleado);
            pw.printf("%-18s: %s%n",  "Metodo de pago", this.metodoPago);
            pw.println("------------------------------------------");
            pw.printf("%-25s %-10s %-8s %-12s%n", "Producto", "P.Unit.", "Cant.", "Subtotal");
            pw.println("------------------------------------------");

            for (Producto p : listaProductos) {
                double subtotal = p.getPrecio() * p.getStock();
                pw.printf("%-25s $%-9.2f %-8d $%-11.2f%n",
                        p.getNombre(), p.getPrecio(), p.getStock(), subtotal);
            }

            pw.println("------------------------------------------");
            if (descuentoPuntos > 0) {
                pw.printf("%-44s $%-11.2f%n", "Subtotal:", totalCalculado);
                pw.printf("%-44s -$%-10.2f%n", "Descuento por puntos:", descuentoPuntos);
            }
            pw.printf("%-44s $%-11.2f%n", "TOTAL:", totalFinal);
            pw.println(SEPARADOR);
            pw.println();
            pw.close();

            System.out.println("\nFactura #" + this.folioTicket + " guardada exitosamente en " + RUTA_FACTURAS);

            // Descontar stock
            Producto gestor = new Producto();
            for (Producto p : listaProductos) {
                gestor.descontarStock(p.getNombre(), p.getStock());
            }

        } catch (IOException e) {
            System.err.println("Error al guardar la factura: " + e.getMessage());
        }
    }

    // ─── mostrarFacturas ─────────────────────────────────────────────────────
    public void mostrarFacturas() {
        java.io.File archivo = new java.io.File(RUTA_FACTURAS);
        if (!archivo.exists()) {
            System.out.println("No existen facturas registradas todavia.");
            return;
        }
        System.out.println("\n============ FACTURAS REGISTRADAS ============");
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null)
                System.out.println(linea);
            br.close();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de facturas: " + e.getMessage());
        }
    }
}
