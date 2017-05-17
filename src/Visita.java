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
    
    /** @brief Consulta si l'Activitat és acceptable
     @pre circuit i viatge no buits
     @post retorna cert si és acceptable i fals en c.c.*/
    @Override
    public boolean Acceptable (Circuit c, Viatge v){
        LocalDateTime inici = diaActivitat().atTime(horaActivitat()), 
                fi = inici.plusHours(Duracio().getHour()).plusMinutes(Duracio().getMinute());
        boolean resultat = inici.isBefore(inici.plusDays(1).toLocalDate().atTime(0, 0)) && fi.isBefore(inici.plusDays(1).toLocalDate().atTime(0, 0)) &&
                c.horesVisites(diaActivitat()).plusHours(Duracio().getHour()).plusMinutes(Duracio().getMinute()).isBefore(LocalTime.of(6, 0));
        return resultat;
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
        return visitat.grauSatisfaccio(g);
    }
    
    /** @brief consulta la ubicacio on s'ha acabat la activitat
     @pre cert
     @post Retorna el PuntInteres on s'ha acabat la activitat*/
    @Override
    public PuntInteres UbicacioActual(){ return visitat; }
}
