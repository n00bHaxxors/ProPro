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

public class Circuit {
    private LocalDateTime inici_viatge, fi_viatge;
    private int preu_per_persona;
    private int grau_satisfacio;
    private int dies;
    private int nActivitats;
    private ArrayList<Activitat> activitats;

    /** @brief Constructor circuit amb paràmetres
     @pre cert
     @post Circuit amb preu,grau,temps i Activitats creat*/
    public Circuit(int preu, int grau, int d, ArrayList<Activitat> a){ //aixo cal?
        preu_per_persona=preu; grau_satisfacio=grau; dies = d; activitats=a;
    }
    /** @brief Constructor circuit amb el dia d'inici del circuit
     @pre cert
     @post Circuit usant di com a dia inicial i final (está buit)*/
    public Circuit(LocalDateTime inici){
        activitats = new ArrayList();
        preu_per_persona = 0; grau_satisfacio=0;nActivitats=0; inici_viatge=inici; fi_viatge=inici;
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
    public int grau_satisfacio(){ return grau_satisfacio; }

    /** @brief Consulta el preu per persona
     @pre cert
     @post Retorna el preu per persona*/
    public int preu_persona(){
        return preu_per_persona;
    }
    
     /** @brief consulta l'última activitat feta
     @pre circuit no buit
     @post retorna a*/
    public Activitat ultimaActivitat(){
        return activitats.get(nActivitats);
    }
    
    /** @brief afegeix una activitat al circuit
     @pre a acceptable
     @post a afegida al circuit*/
    public void afegirActivitat(Activitat a){
        activitats.add(a);
        nActivitats++;
        LocalTime temps = a.Duracio();
        fi_viatge.plusHours(temps.getHour()).plusMinutes(temps.getMinute()).plusSeconds(temps.getSecond());
        dies = (int)ChronoUnit.DAYS.between(fi_viatge, inici_viatge);
    }
    
    /** @brief Treu l'última activitat del circuit
     @pre Circuit no buit
     @post última activitat del circuit treta*/
    public void treureUltimaActivitat(){
        Activitat a = activitats.remove(nActivitats);
        nActivitats--;
        LocalTime temps = a.Duracio();
        fi_viatge.minusHours(temps.getHour()).minusMinutes(temps.getMinute()).minusSeconds(temps.getSecond());
        dies = (int)ChronoUnit.DAYS.between(fi_viatge, inici_viatge);
    }
}
