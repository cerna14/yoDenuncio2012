package cl.cxc;
@SuppressWarnings("serial")
public class Municipio implements java.io.Serializable{
    private String nombre;
    private String region;
    private String alcalde;
    private String partidoAlcalde;
    private String direccion;
    private String correoContacto;
    private String telefono;
    
    public Municipio(String nom, String reg, String alc, String partidoAlc, String dir, String email, String fono) {
        nombre=nom;
        region=reg;
        alcalde=alc;
        partidoAlcalde=partidoAlc;
        direccion=dir;
        correoContacto=email;
        telefono=fono;
    }


    public void setAlcalde(String alcalde) {
        this.alcalde = alcalde;
    }

    public String getAlcalde() {
        return alcalde;
    }

    public void setPartidoAlcalde(String partidoAlcalde) {
        this.partidoAlcalde = partidoAlcalde;
    }

    public String getPartidoAlcalde() {
        return partidoAlcalde;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}
