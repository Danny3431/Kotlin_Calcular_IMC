package grupal13;
import java.util.Date;

 
public class Usuario {
        public String nombre;
	public Date fechaDeNacimiento;
	public String Rut;

    public Usuario() {
    }

    public Usuario(String nombre, Date fechaDeNacimiento, String Rut) {
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.Rut = Rut;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", fechaDeNacimiento=" + fechaDeNacimiento + ", Rut=" + Rut + '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getRut() {
        return Rut;
    }

    public void setRut(String Rut) {
        this.Rut = Rut;
    }
    
}
