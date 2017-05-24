/** @class Visita
 @brief Lloc on ja ha estat un client (ta ok?)
 @author Marc Cané Salamià
 */

import java.time.*;
import java.util.Iterator;
import java.util.Set;

public class Visita extends Activitat{
    private Visitable visitat;

    /** @brief Constructor per defecte
     @pre cert
     @post Visita buida creada*/
    public Visita(){}

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
    public boolean Acceptable (Circuit c, Viatge v, Set<Visitable> obl){
        boolean shaVisitat = c.visitaFeta(visitat);
        LocalDateTime inici = diaActivitat().atTime(horaActivitat()), 
                fi = inici.plusHours(Duracio().getHour()).plusMinutes(Duracio().getMinute());
        // ! visitat, abans del final del dia, i menys de sis hores de visites totals diaries
        boolean visitaSatisfactoria = Satisfaccio(v.clients())>0 || v.desti().nom()==visitat.nom() || v.origen().nom()==visitat.nom() || obl.contains(visitat);
        boolean resultat = !shaVisitat && inici.toLocalTime().isBefore(LocalTime.of(23, 59)) && fi.isBefore(inici.plusDays(1).toLocalDate().atTime(0, 0)) &&
                !c.horesVisites(diaActivitat()).plusHours(Duracio().getHour()).plusMinutes(Duracio().getMinute()).isAfter(LocalTime.of(6, 0));
        return resultat && visitaSatisfactoria;
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
        int a = visitat.grauSatisfaccio(g);
        return visitat.grauSatisfaccio(g);
    }
    
    /** @brief consulta la ubicacio on s'ha acabat la activitat
     @pre cert
     @post Retorna el nom de la Localització on s'ha acabat la activitat*/
    @Override
    public String UbicacioActual(){ return visitat.nom(); }
     
    /** @brief Passa la visitaa format String
     @pre cert
     @post Retorna una String amb la visita*/   
    @Override
    public String toString(){
        LocalTime horaFinal = horaActivitat().plusHours(Duracio().getHour()).plusMinutes(Duracio().getMinute());
        String s = horaActivitat().toString() + " " + horaFinal.toString() + " " + nomAct();
        return s;
    }
}
