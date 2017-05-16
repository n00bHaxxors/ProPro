import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by clank on 16/05/2017.
 */
public class Viatge {
    private LocalDateTime dataHoraInici;
    private Integer nombreDies, preuMaxim;
    Visitable origen, desti;
    String categoria, tipusRuta;
    GrupClients clients;
    ArrayList<Visitable> llistaVisitables;
    public Viatge(LocalDateTime data, Integer n, Integer p, String c, GrupClients cl, ArrayList<Visitable> visitables, String t) {
        dataHoraInici=data;
        nombreDies=n;
        preuMaxim = p;
        categoria = c;
        tipusRuta = t;
        clients = cl;
        llistaVisitables= new ArrayList<Visitable>();
        Iterator<Visitable> it= visitables.iterator();
        origen = it.next();
        while(it.hasNext()){
            desti = it.next();
            if(it.hasNext())llistaVisitables.add(desti);
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
    public String tipusRuta(){
        return tipusRuta;
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

