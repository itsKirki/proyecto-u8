
import java.util.*;

public class Cliente extends Persona {

    Scanner lee = new Scanner(System.in);

    private int puntosFidelidad;
    private String rfcFacturacion;
    private Date fechaRegistro;
    private String tipoCliente;

    public Cliente() {
        super();
        this.puntosFidelidad = 0;
        this.rfcFacturacion = "RFC000000000";
        this.fechaRegistro = new Date();
        this.tipoCliente = "Default";
    }

    public Cliente(int id, String nombre, int telefono, String email, int puntosFidelidad, String rfcFacturacion, Date fechaRegistro, String tipoCliente) {
        super(id, nombre, telefono, email);
        this.puntosFidelidad = puntosFidelidad;
        this.rfcFacturacion = rfcFacturacion;
        this.fechaRegistro = fechaRegistro;
        this.tipoCliente = tipoCliente;
    }

    public int getPuntosFidelidad() {
        return this.puntosFidelidad;
    }

    public void setPuntosFidelidad(int puntosFidelidad) {
        this.puntosFidelidad = puntosFidelidad >= 0 ? puntosFidelidad : 0;
    }

    public String getRfcFacturacion() {
        return this.rfcFacturacion;
    }

    public void setRfcFacturacion(String rfcFacturacion) {
        this.rfcFacturacion = rfcFacturacion;
    }

    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getTipoCliente() {
        return this.tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void agregarCliente() {
        String n, e, r, tc;
        int t = 0, p = 0;
        boolean pass = false;

        System.out.print("Ingrese el nombre del cliente: ");
        n = lee.nextLine();

        do {
            pass = false;
            System.out.print("Ingrese el teléfono del cliente: ");
            try {
                t = lee.nextInt();
                if (t < 0) {
                    System.err.println("El teléfono no puede ser negativo. Intente de nuevo.");
                } else {
                    pass = true;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Entrada inválida. Por favor, ingrese un número válido para el teléfono.");
                lee.nextLine();
            }
        } while (!pass);
        lee.nextLine();

        System.out.print("Ingrese el email del cliente: ");
        e = lee.nextLine();

        do {
            pass = false;
            System.out.print("Ingrese los puntos de fidelidad del cliente: ");
            try {
                p = lee.nextInt();
                if (p < 0) {
                    System.err.println("Los puntos de fidelidad no pueden ser negativos. Intente de nuevo.");
                } else {
                    pass = true;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Entrada inválida. Por favor, ingrese un número válido para los puntos de fidelidad.");
                lee.nextLine();
            }
        } while (!pass);
        lee.nextLine();

        System.out.print("Ingrese el RFC de facturación del cliente: ");
        r = lee.nextLine();

        this.setFechaRegistro(new Date()); // Establecer la fecha de registro actual

        System.out.print("Ingrese el tipo de cliente (Regular, Premium, VIP): ");
        tc = lee.nextLine();

        super.setNombre(n);
        super.setTelefono(t);
        super.setEmail(e);
        this.setPuntosFidelidad(p);
        this.setRfcFacturacion(r);
        this.setTipoCliente(tc);
    }

    public String formato() {
        return String.format("%-8d|%-20s|%-15s|%-25s|%-10d|%-15s|%-30s|%-15s", 
            super.getId(), 
            super.getNombre(), 
            super.getTelefono(), 
            super.getEmail(), 
            this.getPuntosFidelidad(), 
            this.getRfcFacturacion(), 
            this.getFechaRegistro().toString(), 
            this.getTipoCliente()
        );
    }
}
