/** @class Visita
 @brief Lloc on ja ha estat un client (ta ok?)
 @author Marc Cané Salamià
 */

import java.time.*;

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
        super(data,hora);
        visitat=v;
    }

    /** @brief Consulta el nom del lloc visitat
     @pre cert
     @post Retorna el nom del lloc visitat*/
    public String nom_visitable(){
        return visitat.nom();
    }
}
