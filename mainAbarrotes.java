
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainAbarrotes {

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
        System.out.println("3. Eliminar factura");
        System.out.println("4. Actualizar factura");
        System.out.println("5. Volver al menú principal");
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

    public static void mainMenu() {
        System.out.println("\n------------ Bienvenido al sistema de gestión de abarrotes ------------");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Productos");
        System.out.println("2. Factura");
        System.out.println("3. Clientes");
        System.out.println("4. Empleados");
        System.out.println("5. Salir");
    }

    public static void main(String[] args) {
        Scanner lee = new Scanner(System.in);
        int opc = 0, opcSub = 0;
        String rutaArchivo = "data/productos.txt",linea = "";
        boolean pass = false;
        GestionarArchivos gestor = new GestionarArchivos();
        Producto prod = new Producto();

        do {
            mainMenu();
            //Verificar que la entrada sea un número entero válido
            do {
                try {
                    opc = lee.nextInt();
                    pass = true;
                } catch (InputMismatchException e) {
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
                            } catch (InputMismatchException e) {
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                lee.nextLine();
                                pass = false;
                            }
                        } while (!pass);
                        
                        switch (opcSub) {
                            case 1:
                                gestor.abrirArchivo(rutaArchivo, "escribir");
                                prod = new Producto();
                                System.out.println("--- RESGISTRO DE NUEVO PRODUCTO ---");
                                prod.agregarProducto();
                                linea = prod.formato();
                                gestor.agregarLinea(linea);
                                gestor.cerrarArchivo();
                                break;
                            case 2:
                                gestor.abrirArchivo(rutaArchivo, "leer");
                                prod = new Producto();
                                System.out.printf("%-8s|%-20s|%-30s|%-15s|%-10s|%-8s%n", "Código", "Nombre", "Descripción", "Categoría", "Precio", "Stock");
                                gestor.imprimirArchivo();
                                gestor.cerrarArchivo();
                                break;
                            case 3:
                                System.out.println("-> Eliminar productos");
                                break;
                            case 4:
                                System.out.println("-> Actualizar productos");
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

                //Opcion factura
                case 2:
                    do {
                        menuFactura();
                        //Verificar que la entrada sea un número entero válido
                        do {
                            try {
                                opcSub = lee.nextInt();
                                pass = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                lee.nextLine();
                                pass = false;
                            }
                        } while (!pass);

                        switch (opcSub) {
                            case 1:
                                System.out.println("-> Agregar factura");
                                break;
                            case 2:
                                System.out.println("-> Mostrar facturas");
                                break;
                            case 3:
                                System.out.println("-> Eliminar facturas");
                                break;
                            case 4:
                                System.out.println("-> Actualizar facturas");
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

                //Opcion clientes
                case 3:
                    do {
                        menuClientes();
                        //Verificar que la entrada sea un número entero válido
                        do {
                            try {
                                opcSub = lee.nextInt();
                                pass = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                lee.nextLine();
                                pass = false;
                            }
                        } while (!pass);

                        switch (opcSub) {
                            case 1:
                                System.out.println("-> Agregar cliente");
                                break;
                            case 2:
                                System.out.println("-> Mostrar clientes");
                                break;
                            case 3:
                                System.out.println("-> Eliminar clientes");
                                break;
                            case 4:
                                System.out.println("-> Actualizar clientes");
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

                //Opcion empleados
                case 4:
                    do {
                        menuEmpleados();
                        //Verificar que la entrada sea un número entero válido
                        do {
                            try {
                                opcSub = lee.nextInt();
                                pass = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Opción no válida. Vuelva a intentarlo.");
                                lee.nextLine();
                                pass = false;
                            }
                        } while (!pass);

                        switch (opcSub) {
                            case 1:
                                System.out.println("-> Agregar empleado");
                                break;
                            case 2:
                                System.out.println("-> Mostrar empleados");
                                break;
                            case 3:
                                System.out.println("-> Eliminar empleados");
                                break;
                            case 4:
                                System.out.println("-> Actualizar empleados");
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

                //Salida
                case 5:
                    System.out.println("Gracias por usar el sistema de gestión de abarrotes. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Vuelva a intentarlo.");
                    break;
            }
        } while (opc != 5);
        lee.close();
    }
}
