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
    ArrayList<Client> llistaClients;
    ArrayList<Visitable> llistaVisitables;
    public Viatge(LocalDateTime data, Integer n, Integer p, String c, ArrayList<Client> clients, ArrayList<Visitable> visitables, String t) {
        dataHoraInici=data;
        nombreDies=n;
        preuMaxim = p;
        categoria = c;
        tipusRuta = t;
        llistaClients=clients;
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
    public Iterator<Client> iteradorClients(){
        return llistaClients.iterator();
    }
    public Iterator<Visitable> iteradorVisitables(){
        return llistaVisitables.iterator();
    }
}

