package cl.cxc;

import java.util.LinkedList;

public class ListaDenuncias {
    private boolean sucess;
    private String message="Loaded data";
    private LinkedList<Denuncia> maps;
    
    public ListaDenuncias() {
        super();
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMaps(LinkedList<Denuncia> maps) {
        this.maps = maps;
    }

    public LinkedList<Denuncia> getMaps() {
        return maps;
    }
}
