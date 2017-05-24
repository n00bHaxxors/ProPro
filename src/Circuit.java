/**
 @file Allotjament.java
    @brief Classe Circuit
*/
/** @class Circuit
    @brief Conté un possible recorregut que faràn els clients
    @author Marc Cané Salamià
*/

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    HashMap<String, Visita> visitesFetes;

    /** @brief Constructor circuit amb paràmetres
     @pre cert
     @post Circuit amb preu,grau,temps i Activitats creat*/
    public Circuit(int preu, int grau, int d, ArrayList<Activitat> a){ //aixo cal?
        preu_per_persona=preu; grau_satisfaccio=grau; dies = d; activitats=a;
    }
    
    /** @brief Constructor de copia
     @pre cert
     @post nou circuit amb contingut clonat de o*/
    public Circuit(Circuit o){
        preu_per_persona = o.preu_per_persona; grau_satisfaccio = o.grau_satisfaccio; dies = o.dies; nActivitats = o.nActivitats;
        inici_viatge=o.inici_viatge.toLocalDate().atTime(o.inici_viatge.toLocalTime());
        fi_viatge=o.inici_viatge.toLocalDate().atTime(o.fi_viatge.toLocalTime());
        activitats = new ArrayList (o.activitats); visitesFetes = new HashMap(o.visitesFetes);
    }
    
    /** @brief Constructor circuit amb el dia d'inici del circuit
     @pre cert
     @post Circuit usant di com a dia inicial i final (está buit)*/
    public Circuit(LocalDateTime inici){
        activitats = new ArrayList(); visitesFetes = new HashMap();
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
        else return activitats.get(nActivitats-1);
    }
    
    /** @brief afegeix una activitat al circuit
     @pre a acceptable
     @post a afegida al circuit*/
    public void afegirActivitat(Activitat a, Mapa m, Viatge v){
        activitats.add(a);
        nActivitats++;
        LocalTime temps = a.Duracio();
        fi_viatge = fi_viatge.toLocalDate().atTime(a.horaActivitat()).plusHours(temps.getHour()).plusMinutes(temps.getMinute());
        dies = (int)ChronoUnit.DAYS.between(fi_viatge, inici_viatge);
        grau_satisfaccio += a.Satisfaccio(v.clients());
        if (m.conteVisitable(a.nomAct())) visitesFetes.put(a.nomAct(),(Visita)a);
        preu_per_persona += a.preuAct();
    }
    
    /** @brief Treu l'última activitat del circuit
     @pre Circuit no buit
     @post última activitat del circuit treta*/
    public void treureUltimaActivitat(Mapa m, Viatge v){        
        nActivitats--;
        Activitat a = activitats.remove(nActivitats);
        LocalTime temps = a.Duracio();
        if (nActivitats != 0){
            Activitat b = activitats.get(nActivitats-1);
            fi_viatge=b.diaActivitat().atTime(b.horaActivitat()).plusHours(temps.getHour()).plusMinutes(temps.getMinute());
        }
        else fi_viatge = inici_viatge;
        dies = (int)ChronoUnit.DAYS.between(fi_viatge, inici_viatge);
        grau_satisfaccio -= a.Satisfaccio(v.clients());
        preu_per_persona -= a.preuAct();
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
    public boolean solucioCompleta(Set<Visitable> c, Localitzacio origen, Localitzacio desti, int diesV, Mapa g){
        //comprovem que origen i desti son visitables
        boolean oVis = g.conteVisitable(origen.nom()), dVis = g.conteVisitable(desti.nom());
        boolean resultat = diesV>=dies;
        if (oVis) resultat = resultat && activitats.get(0).nomAct().equals(origen.nom()) && visitesFetes.containsKey(origen.nom());
        if (dVis) resultat = resultat && activitats.get(nActivitats-1).nomAct().equals(desti.nom()) && visitesFetes.containsKey(desti.nom());
        else resultat = resultat && activitats.get(nActivitats-1).UbicacioActual().equals(desti.nom());
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
            if (aux.diaActivitat().equals(dia))
                temps.plusHours(aux.Duracio().getHour()).plusMinutes(aux.Duracio().getMinute());
        }
        return temps;
    }
    
    /** @brief consulta si hem visitat un visitable
     @pre v!=null
     @post retorna cert si hem visitat v i fals en c.c.*/
    public boolean visitaFeta(Visitable v){ return visitesFetes.containsKey(v.nom());}

    /** @brief retorna un iterador que recore les activitats que faran els clients
     @pre cert
     @post retorna un iterador de les activitats del circuit*/
    public Iterator<Activitat> Activitats(){
        return activitats.iterator();
    }
}
