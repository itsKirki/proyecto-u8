public class Empleado extends Persona{
    private String rfc;
    private String turno;
    private double salario;
    private String puesto;

    public Empleado(int ID, String nombre, int telefono, String email, String rfc, String turno, double salario, String puesto) {
        super(ID, nombre, telefono, email);
        this.rfc = rfc;
        this.turno = turno;
        this.salario = salario;
        this.puesto = puesto;
    }   

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}
