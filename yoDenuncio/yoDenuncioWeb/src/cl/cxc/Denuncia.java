package cl.cxc;

import java.util.Date;

public class Denuncia implements java.io.Serializable{
    private int id;
    private double lat;
    private double lng;
    private String descripcion;
    private String nombreArchivo;
    private Date fecha;
    private String estado;
    private String direccion;
    
    public Denuncia(int id, double lat, double lon, String desc, String archivo, Date fech, String est, String dir) {
        this.id=id;
        this.lat = lat;
        lng=lon;
        descripcion=desc;
        nombreArchivo=archivo;
        fecha=fech;
        estado=est;
        direccion=dir;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLng() {
        return lng;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }
    
  public void setDireccion(String dir) {
      this.direccion = dir;
  }

  public String getDireccion() {
      return direccion;
  }
}
