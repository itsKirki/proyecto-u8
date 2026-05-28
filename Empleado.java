public class Empleado extends Persona{
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
}
