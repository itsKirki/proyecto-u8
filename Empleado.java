
import java.util.InputMismatchException;
import java.util.Scanner;

public class Empleado extends Persona {

    Scanner lee = new Scanner(System.in);

    private String rfc;
    private String turno;
    private double salario;
    private String puesto;

    public Empleado() {
        super();
        this.rfc = "RFC000000000";
        this.turno = "Default";
        this.salario = 0.0;
        this.puesto = "Default";
    }

    public Empleado(int ID, String nombre, int telefono, String email, String rfc, String turno, double salario, String puesto) {
        super(ID, nombre, telefono, email);
        this.rfc = rfc;
        this.turno = turno;
        this.salario = salario;
        this.puesto = puesto;
    }

    public String getRfc() {
        return this.rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTurno() {
        return this.turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public double getSalario() {
        return this.salario;
    }

    public void setSalario(double salario) {
        this.salario = salario >= 0 ? salario : 0;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public void agregarEmpleado() {
        String n, e, r, tc, rf, tu, pu;
        int t = 0, p = 0;
        double s = 0.0;
        boolean pass = false;

        System.out.print("Ingrese el nombre del empleado: ");
        n = lee.nextLine();

        do {
            pass = false;
            System.out.print("Ingrese el teléfono del empleado: ");
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

        System.out.print("Ingrese el email del empleado: ");
        e = lee.nextLine();

        System.out.print("Ingrese el RFC del empleado: ");
        rf = lee.nextLine();

        System.out.print("Ingrese el turno del empleado: ");
        tu = lee.nextLine();

        do {
            pass = false;
            System.out.print("Ingrese el salario del empleado: ");
            try {
                s = lee.nextDouble();
                if (s < 0) {
                    System.err.println("El salario no puede ser negativo. Intente de nuevo.");
                } else {
                    pass = true;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Entrada inválida. Por favor, ingrese un número válido para el salario.");
                lee.nextLine();
            }
        } while (!pass);
        lee.nextLine();

        System.out.print("Ingrese el puesto del empleado: ");
        pu = lee.nextLine();

        super.setNombre(n);
        super.setTelefono(t);
        super.setEmail(e);
        this.setRfc(rf);
        this.setTurno(tu);
        this.setSalario(s);
        this.setPuesto(pu);
    }

    public String formato() {
        return String.format("%-8d|%-20s|%-15d|%-25s|%-15s|%-10s|%-12.2f|%-20s",
                super.getId(),
                super.getNombre(),
                super.getTelefono(),
                super.getEmail(),
                this.getRfc(),
                this.getTurno(),
                this.getSalario(),
                this.getPuesto()
        );
    }
}
