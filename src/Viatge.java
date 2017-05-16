import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by clank on 16/05/2017.
 */
public class Viatge {
    private LocalDateTime dataHoraInici;
    private Integer nombreDies, preuMaxim;
    private Visitable origen, desti;
    private String categoria;
    private GrupClients clients;
    private ArrayList<Visitable> llistaVisitables;
    private Boolean rutaBarata = false, rutaCurta = false, rutaSatisfactoria = false;
    public Viatge(LocalDateTime data, Integer n, Integer p, String c, GrupClients cl, ArrayList<Visitable> visitables, ArrayList<String> t) {
        dataHoraInici=data;
        nombreDies=n;
        preuMaxim = p;
        categoria = c;
        clients = cl;
        llistaVisitables= new ArrayList<Visitable>();
        Iterator<Visitable> it= visitables.iterator();
        origen = it.next();
        while(it.hasNext()){
            desti = it.next();
            if(it.hasNext())llistaVisitables.add(desti);
        }
        Iterator<String> itTipus = t.iterator();
        String temp = itTipus.next();
        do{
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
        }while(itTipus.hasNext());
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
    public Visitable origen(){
        return origen;
    }
    public Visitable desti(){
        return desti;
    }
    public Iterator<Visitable> iteradorVisitables(){
        return llistaVisitables.iterator();
    }
}

