import java.util.*;

public class Cliente extends Persona {
    private int puntosFidelidad;
    private String rfcFacturacion;
    private Date fechaRegistro;
    private String tipoCliente;
    
    public Cliente(int id, String nombre, int telefono, String email, int puntosFidelidad, String rfcFacturacion, Date fechaRegistro, String tipoCliente) {
        super(id, nombre, telefono, email);
        this.puntosFidelidad = puntosFidelidad;
        this.rfcFacturacion = rfcFacturacion;
        this.fechaRegistro = fechaRegistro;
        this.tipoCliente = tipoCliente;
    }

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public void setPuntosFidelidad(int puntosFidelidad) {
        this.puntosFidelidad = puntosFidelidad;
    }

    public String getRfcFacturacion() {
        return rfcFacturacion;
    }

    public void setRfcFacturacion(String rfcFacturacion) {
        this.rfcFacturacion = rfcFacturacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    
}
