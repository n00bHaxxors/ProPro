import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
public class Viatge {
    private LocalDateTime dataHoraInici;
    private Integer nombreDies, preuMaxim;
    private Localitzacio origen, desti;
    private String categoria;
    private GrupClients clients;
    private ArrayList<Visitable> llistaVisitables;
    private Boolean rutaBarata = false, rutaCurta = false, rutaSatisfactoria = false;
    public Viatge(LocalDateTime data, Integer n, Integer p, String c, GrupClients cl, Localitzacio o,Localitzacio d, ArrayList<Visitable> visitables, ArrayList<String> t) {
        dataHoraInici=data;
        nombreDies=n;
        preuMaxim = p;
        categoria = c;
        clients = cl;
        llistaVisitables = visitables;
        origen = o;
        desti=d;
        Iterator<String> itTipus = t.iterator();
        String temp;
        while(itTipus.hasNext()){
            temp = itTipus.next();
            switch(temp){
                case "ruta curta":
                    rutaCurta=true;
                    break;
                case "ruta barata":
                    rutaBarata=true;
                    break;
                case "ruta satisfactoria":
                    rutaSatisfactoria=true;
                    break;
                default:
                    //throw
                    break;
            }
        }
    }
    public LocalDateTime dataHoraInici(){
        return dataHoraInici;
    }
    public Integer nombreDies(){
        return nombreDies;
    }
    public Integer preuMaxim(){
        return preuMaxim;
    }
    public String categoria(){
        return categoria;
    }
    public GrupClients clients(){
        return clients;
    }
    public Localitzacio origen(){
        return origen;
    }
    public Localitzacio desti(){
        return desti;
    }
    public Iterator<Visitable> iteradorVisitables(){
        return llistaVisitables.iterator();
    }
    public Boolean RutaCurta(){
        return rutaCurta;
    }
    public Boolean RutaBarata(){
        return rutaBarata;
    }
    public Boolean RutaSatisfactoria(){
        return rutaSatisfactoria;
    }
    
}

