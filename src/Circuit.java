/**
 @file Allotjament.java
    @brief Classe Circuit
*/
/** @class Circuit
    @brief Conté un possible recorregut que faràn els clients
    @author Marc Cané Salamià
*/

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Circuit {
    private LocalDateTime inici_viatge, fi_viatge;
    private int preu_per_persona;
    private int grau_satisfaccio;
    private int dies;
    private int nActivitats;
    private ArrayList<Activitat> activitats;
    //metodes que no se si calen aquí!
    HashMap<String, Visita> visitesFetes;

    /** @brief Constructor circuit amb paràmetres
     @pre cert
     @post Circuit amb preu,grau,temps i Activitats creat*/
    public Circuit(int preu, int grau, int d, ArrayList<Activitat> a){ //aixo cal?
        preu_per_persona=preu; grau_satisfaccio=grau; dies = d; activitats=a;
    }
    /** @brief Constructor circuit amb el dia d'inici del circuit
     @pre cert
     @post Circuit usant di com a dia inicial i final (está buit)*/
    public Circuit(LocalDateTime inici){
        activitats = new ArrayList();
        preu_per_persona = 0; grau_satisfaccio=0;nActivitats=0; inici_viatge=inici; fi_viatge=inici;
    }
    

    /** @brief Consulta el temps total
     @pre cert
     @post Retorna el temps total, en dies, que durarà el circuit*/
    public int dies_total() {
        return dies;
    }

    /** @brief Consulta el grau de satisfacció (mitja?) que tindràn els clients al fer el circuit
     @pre cert
     @post Retorna el grau de satisfaccio mitjà*/
    public int grau_satisfaccio(){ return grau_satisfaccio; }

    /** @brief Consulta el preu per persona
     @pre cert
     @post Retorna el preu per persona*/
    public int preu_persona(){
        return preu_per_persona;
    }
    
     /** @brief consulta l'última activitat feta
     @pre cert
     @post retorna a*/
    public Activitat ultimaActivitat(){
        if (activitats.isEmpty()) return null;
        else return activitats.get(nActivitats);
    }
    
    /** @brief afegeix una activitat al circuit
     @pre a acceptable
     @post a afegida al circuit*/
    public void afegirActivitat(Activitat a, GrupClients g, Mapa m){
        activitats.add(a);
        nActivitats++;
        LocalTime temps = a.Duracio();
        fi_viatge.plusHours(temps.getHour()).plusMinutes(temps.getMinute()).plusSeconds(temps.getSecond());
        dies = (int)ChronoUnit.DAYS.between(fi_viatge, inici_viatge);
        grau_satisfaccio += a.Satisfaccio(g);
        if (m.conteVisitable(a.nomAct())) visitesFetes.put(a.nomAct(),(Visita)a);
    }
    
    /** @brief Treu l'última activitat del circuit
     @pre Circuit no buit
     @post última activitat del circuit treta*/
    public void treureUltimaActivitat(GrupClients g, Mapa m){        
        nActivitats--;
        Activitat a = activitats.remove(nActivitats);
        LocalTime temps = a.Duracio();
        fi_viatge.minusHours(temps.getHour()).minusMinutes(temps.getMinute()).minusSeconds(temps.getSecond());
        dies = (int)ChronoUnit.DAYS.between(fi_viatge, inici_viatge);
        grau_satisfaccio -= a.Satisfaccio(g);
        if (m.conteVisitable(a.nomAct())) visitesFetes.remove(a.nomAct());
    }
    /** @brief Consulta el dia i la hora en que acabem el circuit
     @pre cert
     @post retorna un LocalDateTime amb el dia i la hora en que s'acaba el circuit*/
    public LocalDateTime acabamentCircuit (){ return fi_viatge; }
    
    /** @brief Consulta el dia i la hora en que iniciem el circuit
     @pre cert
     @post retorna un LocalDateTime amb el dia i la hora en que es comença el circuit*/
    public LocalDateTime iniciCircuit(){ return inici_viatge; }
    
    /** @brief Consulta si el circuit actual es una solucio completa
     @pre c, origen, desti i diesV no nulls
     @post retorna cert si la solucio es completa i fals en c.c.*/
    public boolean solucioCompleta(Set<Visitable> c, PuntInteres origen, PuntInteres desti, int diesV){
        boolean resultat = activitats.get(nActivitats-1).nomAct().equals(desti.nom()) && activitats.get(0).nomAct().equals(origen.nom())&& diesV==dies;
        Iterator<Visitable> itr = c.iterator();
        while (resultat && itr.hasNext()){
            Visitable aux = itr.next();
            resultat = visitesFetes.containsKey(aux.nom());
        }
        return resultat;
    }
    
    /** @brief consulta el temps de visites total que sha fet en un dia
     @pre dia no null
     @post retorna el temps invertit en visites en el dia*/
    public LocalTime horesVisites(LocalDate dia){
        Iterator<Visita> itr = visitesFetes.values().iterator();
        LocalTime temps = LocalTime.of(0, 0);
        while (itr.hasNext()){
            Visita aux = itr.next();
            if (aux.diaActivitat().equals(dia)) temps.plusHours(aux.Duracio().getHour()).plusMinutes(aux.Duracio().getMinute());
        }
        return temps;
    }
}
