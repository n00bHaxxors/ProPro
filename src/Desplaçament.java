/** @class Desplaçament
 @brief Canvi d'una ubicació a una altre fet amb un mitjià de transport
 @author Marc Cané Salamià
 */

import java.time.*;

public class Desplaçament extends Activitat { //aka trasllat

    private Mitja_Transport mitja;
    private PuntInteres origen, desti; //npi de quina classe ha de ser
 
    /** @brief Constructor amb parametres
     @pre cert
     @post Desplaçament amb preu, duracio, mitja, origen i desti creat*/
    public Desplaçament(int p, LocalDate d, LocalTime h, Mitja_Transport m, PuntInteres o, PuntInteres de){
        super(d,h,p);
        mitja=m; origen=o; desti=de;
    }
    
    /** @brief Consulta el mitjà de transport usat en el desplaçament
     @pre cert
     @post Retorna el mitjà de transport usat*/
    public Mitja_Transport mitja_usat(){ return mitja; }
    
    @Override
    public boolean Acceptable (Circuit c){
        return true;
    }
    
    /** @brief Consulta la duracio del desplaçament
     @pre cert
     @post Retorna la duració del desplaçament*/
    @Override
    public LocalTime Duracio(){
        return null; //estic esperant en cané
    }
    
    /** @brief Calcula la Satisfaccio que afageix l'activitat
     @pre cert
     @post Retorna la satisfaccio calculada*/
    @Override
    public int Satisfaccio(GrupClients g){ return 0; }
    
    /** @brief consulta la ubicacio on s'ha acabat la activitat
     @pre cert
     @post Retorna el PuntInteres on s'ha acabat la activitat*/
    @Override
    public PuntInteres UbicacioActual(){ return desti; }
}
