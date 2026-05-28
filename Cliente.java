import java.util.*;

public class Cliente extends Persona {
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

    
}
