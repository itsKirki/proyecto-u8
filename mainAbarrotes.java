
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class mainAbarrotes {

    public static void menuProd() {
        System.out.println("\n--- Menú Productos ---");
        System.out.println("1. Agregar producto");
        System.out.println("2. Mostrar productos");
        System.out.println("3. Eliminar productos");
        System.out.println("4. Actualizar productos");
        System.out.println("5. Volver al menú principal");
    }

    public static void menuFactura() {
        System.out.println("\n--- Menú Facturas ---");
        System.out.println("1. Crear factura");
        System.out.println("2. Mostrar facturas");
        System.out.println("3. Volver al menú principal");
    }

    public static void menuClientes() {
        System.out.println("\n--- Menú Clientes ---");
        System.out.println("1. Agregar cliente");
        System.out.println("2. Mostrar clientes");
        System.out.println("3. Eliminar cliente");
        System.out.println("4. Actualizar cliente");
        System.out.println("5. Volver al menú principal");
    }

    public static void menuEmpleados() {
        System.out.println("\n--- Menú Empleados ---");
        System.out.println("1. Agregar Empleado");
        System.out.println("2. Mostrar Empleados");
        System.out.println("3. Eliminar Empleado");
        System.out.println("4. Actualizar Empleado");
        System.out.println("5. Volver al menú principal");
    }

    public static void menuTienda() {
        System.out.println("\n--- Menú Tienda ---");
        System.out.println("1. Mostrar información de la tienda");
        System.out.println("2. Actualizar información de la tienda");
        System.out.println("3. Volver al menú principal");
    }

    public static void mainMenu() {
        System.out.println("\n------------ Bienvenido al sistema de gestión de abarrotes ------------");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Productos");
        System.out.println("2. Factura");
        System.out.println("3. Clientes");
        System.out.println("4. Empleados");
        System.out.println("5. Tienda");
        System.out.println("6. Salir");
    }

    public static void main(String[] args) {
        Scanner lee = new Scanner(System.in);
        int opc = 0, opcSub = 0, s = 0, reintentar = 0, t = 0;
        double p = 0.0, sa = 0.0;
        String linea = "", eliminar = "", n = null, d = null, c = null, codigoActualizar = null, e = null, r = null, tc = null, tu = null, pu = null, su = null;
        boolean pass = false;
        GestionarArchivos gestor = new GestionarArchivos();
        Producto prod = new Producto();
        Cliente clie = new Cliente();
        Empleado emp = new Empleado();
        Venta venta = new Venta();

        Tienda tienda = new Tienda("Abarrotes Don Pepe", "Sucursal Centro", "Av. Principal #123");
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/tienda.txt"));
            String lineaArchivo = null;
            if ((lineaArchivo = br.readLine()) != null) {
                String[] partes = lineaArchivo.split("\\|");
                if (partes.length >= 3) {
                    tienda.setNombre(partes[0].trim());
                    tienda.setSucursal(partes[1].trim());
                    tienda.setDireccion(partes[2].trim());
                }
            }
            br.close();
        } catch (IOException ex) {
            System.out.println("Nota: No se encontró información previa de la tienda. Se usarán valores predeterminados.");
        }

        do {
            mainMenu();
            //Verificar que la entrada sea un número entero válido
            do {
                try {
                    opc = lee.nextInt();
                    pass = true;
                } catch (InputMismatchException exc) {
                    System.out.println("Opción no válida. Vuelva a intentarlo.");
                    lee.nextLine();
                    pass = false;
                }
            } while (!pass);

            switch (opc) {

                //Opcion producto
                case 1:
                    do {
                        menuProd();
                        //Verificar que la entrada sea un número entero válido
                        do {
                            try {
                                opcSub = lee.nextInt();
                                pass = true;
                            } catch (InputMismatchException exc) {
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                lee.nextLine();
                                pass = false;
                            }
                        } while (!pass);

                        switch (opcSub) {
                            case 1:
                                gestor.abrirArchivo("data/productos.txt", "escribir");
                                prod = new Producto();
                                System.out.println("--- RESGISTRO DE NUEVO PRODUCTO ---");
                                prod.agregarProducto();
                                linea = prod.formato();
                                gestor.agregarLinea(linea);
                                gestor.cerrarArchivo();
                                break;
                            case 2:
                                gestor.abrirArchivo("data/productos.txt", "leer");
                                prod = new Producto();
                                System.out.printf("%-8s|%-20s|%-30s|%-15s|%-10s|%-8s%n", "Código", "Nombre", "Descripción", "Categoría", "Precio", "Stock");
                                gestor.imprimirArchivo();
                                gestor.cerrarArchivo();
                                break;
                            case 3:
                                System.out.println("Escriba el código del producto que desea eliminar:");
                                eliminar = lee.nextLine();
                                gestor.borrarLinea("data/productos.txt", eliminar);
                                break;
                            case 4:
                                lee.nextLine();
                                System.out.println("Escriba el nuevo nombre del producto:");
                                n = lee.nextLine();
                                System.out.println("Escriba la nueva descripción del producto:");
                                d = lee.nextLine();
                                System.out.println("Escriba la nueva categoría del producto:");
                                c = lee.nextLine();
                                pass = false;

                                do {
                                    System.out.println("Escriba el nuevo precio del producto:");
                                    try {
                                        p = lee.nextDouble();
                                        if (p <= 0) {
                                            System.err.println("Error: El precio debe ser mayor a 0.");
                                        } else {
                                            pass = true;
                                        }
                                    } catch (InputMismatchException exc) {
                                        System.out.println("Error: Precio debe ser un número.");
                                        lee.nextLine();
                                    }
                                } while (!pass);

                                pass = false;
                                do {
                                    System.out.println("Escriba el nuevo stock del producto:");
                                    try {
                                        s = lee.nextInt();
                                        if (s < 0) {
                                            System.err.println("Error: El stock debe ser mayor a 0.");
                                        } else {
                                            pass = true;
                                        }
                                    } catch (InputMismatchException exc) {
                                        System.out.println("Error: Stock debe ser un número.");
                                        lee.nextLine();
                                    }
                                } while (!pass);
                                lee.nextLine();

                                do {
                                    System.out.println("Escriba el código del producto que desea actualizar:");
                                    codigoActualizar = lee.nextLine();
                                    boolean actualizado = gestor.actualizarLinea(
                                            "data/productos.txt",
                                            codigoActualizar,
                                            String.format(
                                                    "%-20s|%-30s|%-15s|%-10.2f|%-8d",
                                                    n, d, c, p, s
                                            )
                                    );

                                    if (actualizado) {
                                        System.out.println("Producto actualizado exitosamente.");
                                        reintentar = 2;
                                    } else {
                                        System.out.println("Deseas volver a intentar? 1) Sí 2) No");
                                        reintentar = lee.nextInt();
                                        lee.nextLine();
                                    }
                                } while (reintentar != 2);
                                reintentar = 0;

                                break;
                            case 5:
                                System.out.println("Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                break;
                        }
                    } while (opcSub != 5);
                    break;

                //Opcion factura
                case 2:
                    do {
                        menuFactura();
                        //Verificar que la entrada sea un número entero válido
                        do {
                            try {
                                opcSub = lee.nextInt();
                                pass = true;
                            } catch (InputMismatchException exc) {
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                lee.nextLine();
                                pass = false;
                            }
                        } while (!pass);

                        switch (opcSub) {
                            case 1:
                                venta = new Venta();
                                lee.nextLine(); // consumir \n pendiente del nextInt()
                                venta.crearFactura(tienda.getNombre(), lee);
                                break;
                            case 2:
                                venta = new Venta();
                                venta.mostrarFacturas();
                                break;
                            case 3:
                                System.out.println("Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                break;
                        }
                    } while (opcSub != 5);
                    break;

                //Opcion clientes
                case 3:
                    do {
                        menuClientes();
                        //Verificar que la entrada sea un número entero válido
                        do {
                            try {
                                opcSub = lee.nextInt();
                                pass = true;
                            } catch (InputMismatchException exc) {
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                lee.nextLine();
                                pass = false;
                            }
                        } while (!pass);

                        switch (opcSub) {
                            case 1:
                                gestor.abrirArchivo("data/clientes.txt", "escribir");
                                clie = new Cliente();
                                System.out.println("--- RESGISTRO DE NUEVO CLIENTE ---");
                                clie.agregarCliente();
                                linea = clie.formato();
                                gestor.agregarLinea(linea);
                                gestor.cerrarArchivo();
                                break;
                            case 2:
                                gestor.abrirArchivo("data/clientes.txt", "leer");
                                clie = new Cliente();
                                System.out.printf("%-8s|%-20s|%-15s|%-25s|%-10s|%-15s|%-30s|%-15s\n", "Código", "Nombre", "Teléfono", "Email", "Puntos", "RFC", "Fecha de Registro", "Tipo de Cliente");
                                gestor.imprimirArchivo();
                                gestor.imprimirArchivo();
                                gestor.cerrarArchivo();
                                break;
                            case 3:
                                lee.nextLine();
                                System.out.println("Escriba el código del cliente que desea eliminar:");
                                eliminar = lee.nextLine();
                                gestor.borrarLinea("data/clientes.txt", eliminar);
                                break;
                            case 4:
                                lee.nextLine();
                                System.out.println("Escriba el nuevo nombre del cliente:");
                                n = lee.nextLine();

                                do {
                                    pass = false;
                                    System.out.println("Escriba el nuevo teléfono del cliente:");
                                    try {
                                        t = lee.nextInt();
                                        if (t < 0) {
                                            System.err.println("Error: El teléfono no puede ser negativo. Intente de nuevo.");
                                        } else {
                                            pass = true;
                                        }
                                    } catch (InputMismatchException ex) {
                                        System.out.println("Entrada inválida. Por favor, ingrese un número válido para el teléfono.");
                                        lee.nextLine();
                                    }
                                } while (!pass);
                                lee.nextLine();

                                System.out.println("Escriba el nuevo email del cliente:");
                                e = lee.nextLine();

                                do {
                                    pass = false;
                                    System.out.println("Escriba los nuevos puntos de fidelidad del cliente:");
                                    try {
                                        p = lee.nextInt();
                                        if (p < 0) {
                                            System.err.println("Error: Los puntos de fidelidad no pueden ser negativos. Intente de nuevo.");
                                        } else {
                                            pass = true;
                                        }
                                    } catch (InputMismatchException ex) {
                                        System.out.println("Entrada inválida. Por favor, ingrese un número válido para los puntos de fidelidad.");
                                        lee.nextLine();
                                    }
                                } while (!pass);
                                lee.nextLine();

                                System.out.println("Escriba el nuevo RFC de facturación del cliente:");
                                r = lee.nextLine();

                                System.out.println("Escriba el nuevo tipo de cliente (Regular, Premium, VIP):");
                                tc = lee.nextLine();

                                do {
                                    System.out.println("Escriba el código del cliente que desea actualizar:");
                                    codigoActualizar = lee.nextLine();
                                    boolean actualizado = gestor.actualizarLinea(
                                            "data/clientes.txt",
                                            codigoActualizar,
                                            String.format(
                                                    "%-20s|%-15d|%-25s|%-10d|%-15s|%-30s|%-15s", n, (int) t, e, (int) p, r, new Date().toString(), tc
                                            )
                                    );

                                    if (actualizado) {
                                        System.out.println("Cliente actualizado exitosamente.");
                                        reintentar = 2;
                                    } else {
                                        System.out.println("No se encontró el cliente con el código especificado.");
                                        System.out.println("Deseas volver a intentar? 1) Sí 2) No");
                                        reintentar = lee.nextInt();
                                        lee.nextLine();
                                    }
                                } while (reintentar != 2);
                                reintentar = 0;

                                break;
                            case 5:
                                System.out.println("Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                break;
                        }
                    } while (opcSub != 5);
                    break;

                //Opcion empleados
                case 4:
                    do {
                        menuEmpleados();
                        //Verificar que la entrada sea un número entero válido
                        do {
                            try {
                                opcSub = lee.nextInt();
                                pass = true;
                            } catch (InputMismatchException exc) {
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                lee.nextLine();
                                pass = false;
                            }
                        } while (!pass);

                        switch (opcSub) {
                            case 1:
                                gestor.abrirArchivo("data/empleados.txt", "escribir");
                                emp = new Empleado();
                                System.out.println("--- RESGISTRO DE NUEVO EMPLEADO ---");
                                emp.agregarEmpleado();
                                linea = emp.formato();
                                gestor.agregarLinea(linea);
                                gestor.cerrarArchivo();
                                break;
                            case 2:
                                gestor.abrirArchivo("data/empleados.txt", "leer");
                                emp = new Empleado();
                                System.out.printf("%-8s|%-20s|%-15s|%-25s|%-15s|%-10s|%-12s|%-20s\n",
                                        "Código",
                                        "Nombre",
                                        "Teléfono",
                                        "Email",
                                        "RFC",
                                        "Turno",
                                        "Salario",
                                        "Puesto");
                                gestor.imprimirArchivo();
                                gestor.cerrarArchivo();
                                break;
                            case 3:
                                lee.nextLine();
                                System.out.println("Escriba el código del empleado que desea eliminar:");
                                eliminar = lee.nextLine();
                                gestor.borrarLinea("data/empleados.txt", eliminar);
                                break;
                            case 4:
                                lee.nextLine();
                                System.out.println("Escriba el nuevo nombre del empleado:");
                                n = lee.nextLine();

                                do {
                                    pass = false;
                                    System.out.println("Escriba el nuevo teléfono del empleado:");
                                    try {
                                        t = lee.nextInt();
                                        if (t < 0) {
                                            System.err.println("Error: El teléfono no puede ser negativo. Intente de nuevo.");
                                        } else {
                                            pass = true;
                                        }
                                    } catch (InputMismatchException ex) {
                                        System.out.println("Entrada inválida. Por favor, ingrese un número válido para el teléfono.");
                                        lee.nextLine();
                                    }
                                } while (!pass);
                                lee.nextLine();

                                System.out.println("Escriba el nuevo email del empleado:");
                                e = lee.nextLine();

                                System.out.println("Escriba el nuevo RFC del empleado:");
                                r = lee.nextLine();

                                System.out.println("Escriba el nuevo turno del empleado:");
                                tu = lee.nextLine();

                                do {
                                    pass = false;
                                    System.out.println("Escriba el nuevo salario del empleado:");
                                    try {
                                        sa = lee.nextDouble();
                                        if (sa < 0) {
                                            System.err.println("Error: El salario no puede ser negativo. Intente de nuevo.");
                                        } else {
                                            pass = true;
                                        }
                                    } catch (InputMismatchException ex) {
                                        System.out.println("Entrada inválida. Por favor, ingrese un número válido para el salario.");
                                        lee.nextLine();
                                    }
                                } while (!pass);
                                lee.nextLine();

                                System.out.println("Escriba el nuevo puesto del empleado:");
                                pu = lee.nextLine();

                                do {
                                    System.out.println("Escriba el código del empleado que desea actualizar:");
                                    codigoActualizar = lee.nextLine();
                                    boolean actualizado = gestor.actualizarLinea(
                                            "data/empleados.txt",
                                            codigoActualizar,
                                            String.format(
                                                    "%-20s|%-15d|%-25s|%-15s|%-10s|%-12.2f|%-20s",
                                                    n,
                                                    (long) t,
                                                    e,
                                                    r,
                                                    tu,
                                                    sa,
                                                    pu
                                            )
                                    );

                                    if (actualizado) {
                                        System.out.println("Empleado actualizado exitosamente.");
                                        reintentar = 2;
                                    } else {
                                        System.out.println("No se encontró el empleado con el código especificado.");
                                        System.out.println("Deseas volver a intentar? 1) Sí 2) No");
                                        reintentar = lee.nextInt();
                                        lee.nextLine();
                                    }
                                } while (reintentar != 2);
                                reintentar = 0;
                                break;
                            case 5:
                                System.out.println("-> Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                break;
                        }
                    } while (opcSub != 5);
                    break;

                case 5:
                    do {
                        menuTienda();
                        //Verificar que la entrada sea un número entero válido
                        do {
                            try {
                                opcSub = lee.nextInt();
                                pass = true;
                            } catch (InputMismatchException exc) {
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                lee.nextLine();
                                pass = false;
                            }
                        } while (!pass);

                        switch (opcSub) {
                            case 1:
                                gestor.abrirArchivo("data/tienda.txt", "leer");
                                prod = new Producto();
                                System.out.printf("%-16s|%-20s|%-30s%n", "Nombre", "Sucursal", "Dirección");
                                gestor.imprimirArchivo();
                                gestor.cerrarArchivo();
                                break;
                            case 2:
                                lee.nextLine();
                                System.out.println("Escriba el nuevo nombre de la tienda:");
                                n = lee.nextLine();

                                System.out.println("Escriba la nueva sucursal de la tienda:");
                                su = lee.nextLine();

                                System.out.println("Escriba la nueva dirección de la tienda:");
                                d = lee.nextLine();

                                tienda.setNombre(n);
                                tienda.setSucursal(su);
                                tienda.setDireccion(d);

                                try {
                                    java.io.FileWriter fw = new java.io.FileWriter("data/tienda.txt", false);
                                    java.io.PrintWriter pw = new java.io.PrintWriter(fw);

                                    pw.printf("%-16s|%-20s|%-30s|%n", tienda.getNombre(), tienda.getSucursal(), tienda.getDireccion());
                                    pw.close();

                                    System.out.println("¡El archivo ha sido actualizado correctamente!");

                                } catch (java.io.IOException ex) {
                                    System.out.println("Error al intentar actualizar el archivo: " + ex.getMessage());
                                }
                                break;
                            case 3:
                                System.out.println("Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                break;
                        }
                    } while (opcSub != 3);
                    break;

                //Salida
                case 6:
                    System.out.println("Gracias por usar el sistema de gestión de abarrotes. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Vuelva a intentarlo.");
                    break;
            }
        } while (opc != 6);
        lee.close();
    }
}
