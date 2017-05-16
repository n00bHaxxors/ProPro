/** @class Visita
 @brief Lloc on ja ha estat un client (ta ok?)
 @author Marc Cané Salamià
 */

import java.time.*;
import java.util.Iterator;

public class Visita extends Activitat{
    private Visitable visitat;

    /** @brief Constructor per defecte
     @pre cert
     @post Visita buida creada*/
    //public Visita(){}

    /** @brief Constructor amb paràmetres
     @pre cert
     @post Visita amb lloc visitat i data de visita creada*/
    public Visita(Visitable v, LocalDate data, LocalTime hora){
        super(data,hora,v.preu());
        visitat=v;
    }

    /** @brief Consulta el nom del lloc visitat
     @pre cert
     @post Retorna el nom del lloc visitat*/
    @Override
    public String nomAct(){
        return visitat.nom();
    }
    
    @Override
    public boolean Acceptable (Circuit c){
        return true;
    }
    
    /** @brief Consulta la duracio de la visita
     @pre cert
     @post Retorna la duració de la visita*/
    @Override
    public LocalTime Duracio(){
        return visitat.tempsVisitaRec();
    }
    
    /** @brief Calcula la Satisfaccio que afageix l'activitat
     @pre cert
     @post Retorna la satisfaccio calculada*/
    @Override
    public int Satisfaccio(GrupClients g){
        Iterator<Client> itr = g.iteradorClients();
        int resultat = 0;
        while(itr.hasNext()){
            Client c = itr.next();
            Iterator<String> itr2= c.IteradorPreferencies();
            while (itr2.hasNext()){
                String preferencia = itr2.next();
                if (visitat.conteCaracteristica(preferencia)) resultat++;
            }
        }
        return resultat;
    }
    
    /** @brief consulta la ubicacio on s'ha acabat la activitat
     @pre cert
     @post Retorna el PuntInteres on s'ha acabat la activitat*/
    @Override
    public PuntInteres UbicacioActual(){ return visitat; }
}
