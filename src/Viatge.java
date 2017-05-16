import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by clank on 16/05/2017.
 */
public class Viatge {
    private LocalDateTime dataHoraInici;
    private Integer nombreDies, preuMaxim;
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
        llistaVisitables=visitables;
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
    public Iterator<Visitable> iteradorVisitables(){
        return llistaVisitables.iterator();
    }
}

