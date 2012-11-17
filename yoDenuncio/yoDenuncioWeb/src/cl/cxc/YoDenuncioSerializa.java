package cl.cxc;


import cl.cxc.persistencia.DenunciaBD;

import com.google.gson.Gson;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.sql.Date;

import java.util.LinkedList;
import java.util.Scanner;


public class YoDenuncioSerializa {
    private static String archivoDenunciasSerializadas =
        "D:/denunciasSerializadas.ser";
    private static String archivoMunicipiosSerializadas =
        "D:/municipiosSerializados.ser";

    public YoDenuncioSerializa() {
        super();
    }

    public static LinkedList<Denuncia> obtieneDenuncias() throws IOException,
                                                                 ClassNotFoundException {
        ObjectInputStream entrada =
            new ObjectInputStream(new FileInputStream(archivoDenunciasSerializadas));
        LinkedList<Denuncia> denuncias = new LinkedList<Denuncia>();
        Object denuncia;
        denuncia = entrada.readObject();
        try {
            while (denuncia != null) {
                if (denuncia instanceof Denuncia)
                    denuncias.add((Denuncia)denuncia);
                denuncia = entrada.readObject();
            }
        } catch (EOFException ex) { //This exception will be caught when EOF is reached
            System.out.println("End of file reached.");
        }
        entrada.close();
        return denuncias;
    }

    public static LinkedList<Municipio> obtieneMunicipios() throws IOException,
                                                                                 ClassNotFoundException {
        ObjectInputStream entrada =
            new ObjectInputStream(new FileInputStream(archivoMunicipiosSerializadas));
        LinkedList<Municipio> municipios = new LinkedList<Municipio>();
        Object muni;
        muni = entrada.readObject();
        System.out.println("OBTIENE");
        try {
            while (muni != null) {
                if (muni instanceof Municipio) {
                    municipios.add((Municipio)muni);
                    System.out.println(((Municipio)muni).getNombre());
                }
                muni = entrada.readObject();

            }
        } catch (EOFException ex) { //This exception will be caught when EOF is reached
            System.out.println("End of file reached.");
        }
        entrada.close();
        return municipios;
    }


    public static void serializaTodosMunicipiosDesdeArchivo(String archivoFuente,
                                                            String archivoSerializacion) throws IOException {
        Scanner scan = new Scanner(new FileReader(archivoFuente));
        ObjectOutputStream salida =
            new ObjectOutputStream(new FileOutputStream(archivoSerializacion));
        Municipio municipio;
        while (scan.hasNextLine()) {
            String lin = scan.nextLine();
            System.out.println(lin);
            Scanner linea = new Scanner(lin).useDelimiter("[,]");
            salida.writeObject(new Municipio(linea.next(), linea.next(),
                                             linea.next(), linea.next(),
                                             linea.next(), linea.next(),
                                             linea.next()));
        }
        salida.close();
        scan.close();

    }

    public static void serializaDenuncia(Denuncia denuncia) throws IOException {
        ObjectOutputStream salida =
            new ObjectOutputStream((new FileOutputStream(archivoDenunciasSerializadas)));
        salida.writeObject(denuncia);
        salida.close();
    }

    public static void serializaDenuncias(LinkedList<Denuncia> denuncias) throws IOException {
        ObjectOutputStream salida =
            new ObjectOutputStream((new FileOutputStream(archivoDenunciasSerializadas)));
        for (int i = 0; i < denuncias.size(); i++)
            salida.writeObject(denuncias.get(i));
        salida.close();


    }

    public static void main(String[] args) {
        //prueba serializa

        /* try { */
            /* YoDenuncioSerializa.serializaTodosMunicipiosDesdeArchivo("municipios.dat", "municipiosSerializados.ser");
            YoDenuncioSerializa yds= new YoDenuncioSerializa();
            LinkedList<Municipio> municipios= yds.obtieneMunicipios("municipiosSerializados.ser");
            Municipio m;
            for(int i=0; i<10 ; i++)
                    System.out.println(municipios.get(i).getAlcalde()); */

           /*  YoDenuncioSerializa.serializaDenuncia(new Denuncia("9292",
                                                               "denuncia1",
                                                               "foto.jpg"));
            YoDenuncioSerializa.serializaDenuncia(new Denuncia("9292",
                                                               "denuncia2",
                                                               "foto.jpg"));
            YoDenuncioSerializa.serializaDenuncia(new Denuncia("9292",
                                                               "denuncia3",
                                                               "foto.jpg"));
            YoDenuncioSerializa.serializaDenuncia(new Denuncia("9292",
                                                               "denuncia4",
                                                               "foto.jpg"));
            LinkedList<Denuncia> denuncias =
                YoDenuncioSerializa.obtieneDenuncias();
            for (int i = 0; i < denuncias.size(); i++)
                System.out.println(denuncias.get(i).getDescripcion());
            
           Gson gson = new Gson();        
           LinkedList<Denuncia> mapa= new LinkedList<Denuncia>();
           mapa.add(new Denuncia("asda","descripcion", "archivo"));
        mapa.add(new Denuncia("asda2","descripcion2", "archivo2"));
        ListaDenuncias lista = new ListaDenuncias();
        lista.setMaps(mapa);
               System.out.println(gson.toJson(lista));
            
        /* } catch (IOException e) {
            System.out.print(e.toString());
        } catch (ClassNotFoundException e) {
            System.out.print(e.toString());
        } */
        
        DenunciaBD bd= new DenunciaBD();
        System.out.print(bd.insertarDenuncia( "arch1"));
        LinkedList<Denuncia> denuncias= bd.obtieneDenuncias();
       for (int i = 0; i < denuncias.size(); i++)
          System.out.println(denuncias.get(i).getDescripcion()); 
    }

}

