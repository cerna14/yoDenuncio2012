package cl.cxc;

import cl.cxc.persistencia.DenunciaBD;

import java.io.IOException;

import java.sql.Date;

import java.util.LinkedList;

public class Controlador {
    private static Controlador instance = null;
    private LinkedList<Denuncia> denuncias;
    private LinkedList<Municipio> municipios;
    
    private String archivo="";
    

    public Controlador() throws IOException, ClassNotFoundException {
       // denuncias = YoDenuncioSerializa.obtieneDenuncias();
       // municipios = YoDenuncioSerializa.obtieneMunicipios();
    }

    public static Controlador getInstance() throws IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new Controlador();
        }
        return instance;
    }

    public void agregaDenuncia(Denuncia denuncia) {
        denuncias.add(denuncia);
    }

    public LinkedList<Denuncia> obtieneDenunciasAlaRedonda(double lat, double lng) {

        //SOLO PARA PRUEBA
        LinkedList<Denuncia> lista = new LinkedList<Denuncia>();
        /* lista.add(new Denuncia(1,-33.4492362, -70.654778, "denuncia1", "foto1",null, ""));
        lista.add(new Denuncia(2,-33.442909, -70.65387, "denuncia2", "foto2",null,""));
           return lista; */
        DenunciaBD db = new DenunciaBD();
        return db.obtieneDenuncias();
    }

    public int agregaDenuncia(String fileName) {
        this.archivo=fileName;
        return 1;
        //DenunciaBD db = new DenunciaBD();
        //return db.insertarDenuncia(fileName);
    }

    public void insertarDenuncia(int id, double lat, double lon, String desc,  Date fech, String est, String dir) {
        DenunciaBD db = new DenunciaBD();
        db.insertarDenuncia(id, lat, lon, desc, archivo, fech, est, dir);
    }
}
