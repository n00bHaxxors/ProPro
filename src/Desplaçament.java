/** @class Desplaçament
 @brief Canvi d'una ubicació a una altre fet amb un mitjià de transport
 @author Marc Cané Salamià
 */

import java.time.*;

public class Desplaçament extends Activitat { //aka trasllat

    private Mitja_Transport mitja;
    private Localitzacio origen, desti;
    private LocalTime durada;
 
    /** @brief Constructor amb parametres
     @pre cert
     @post Desplaçament amb preu, duracio, mitja, origen i desti creat*/
    public Desplaçament(int p, LocalDate d, LocalTime h, Mitja_Transport m, Localitzacio o, Localitzacio de, LocalTime duracio){
        super(d,h,p);
        mitja=m; origen=o; desti=de; durada = duracio;
    }
    
    /** @brief Consulta el mitjà de transport usat en el desplaçament
     @pre cert
     @post Retorna el mitjà de transport usat*/
    public Mitja_Transport mitja_usat(){ return mitja; }
    
    /** @brief Consulta si l'Activitat és acceptable
     @pre circuit i viatge no buits
     @post retorna cert si és acceptable i fals en c.c.*/
    @Override
    public boolean Acceptable (Circuit c, Viatge v){
        boolean potSerNocturn = c.dies_total()==v.nombreDies() || c.dies_total() == 0;
        LocalDateTime inici = diaActivitat().atTime(horaActivitat()), 
                fi = inici.plusHours(Duracio().getHour()).plusMinutes(Duracio().getMinute());
        // ! visitat, abans del final del dia, i menys de sis hores de visites totals diaries
        boolean resultat = potSerNocturn || inici.isBefore(inici.plusDays(1).toLocalDate().atTime(0, 0)) && fi.isBefore(inici.plusDays(1).toLocalDate().atTime(0, 0));
        return resultat && c.transportEnBucle(this);
    }
    
    /** @brief Consulta la duracio del desplaçament
     @pre cert
     @post Retorna la duració del desplaçament*/
    @Override
    public LocalTime Duracio(){
        return durada; //estic esperant en cané
    }
    
    /** @brief Calcula la Satisfaccio que afageix l'activitat
     @pre cert
     @post Retorna la satisfaccio calculada*/
    @Override
    public int Satisfaccio(GrupClients g){ return 0; }
    
    /** @brief consulta la ubicacio on s'ha acabat la activitat
     @pre cert
     @post Retorna el nom de la Localitzacio on s'ha acabat la activitat*/
    @Override
    public String UbicacioActual(){ return desti.nom(); }
    
    /** @brief Consulta el nom del Mitja usat
     @pre cert
     @post Retorna el nom del MitjaTransport usat*/
    public String nomAct(){ return mitja.nom(); }
        
    /** @brief Passa el desplaçament a format String
     @pre cert
     @post Retorna una String amb el desplaçament*/
    @Override
    public String toString(){
        LocalTime horaFinal = horaActivitat().plusHours(Duracio().getHour()).plusMinutes(Duracio().getMinute());
        String s = horaActivitat().toString() + " " + horaFinal.toString() + " " + origen.nom() + " -> " + desti.nom() + "(" + nomAct() + ")";
        return s;
    }
}
