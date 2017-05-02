/** @class Visita
 @brief Lloc on ja ha estat un client (ta ok?)
 @author Marc Cané Salamià
 */

import java.time.*;

public class Visita {
    private Visitable visitat;
    private LocalDate data_visita; //cal?
    //ajudeume a posar atributs aqui

    /** @brief Constructor per defecte
     @pre cert
     @post Visita buida creada*/
    public Visita(){}

    /** @brief Constructor amb paràmetres
     @pre cert
     @post Visita amb lloc visitat i data de visita creada*/
    public Visita(Visitable v, LocalDate data){
        visitat=v; data_visita=data;
    }

    /** @brief Consulta el nom del lloc visitat
     @pre cert
     @post Retorna el nom del lloc visitat*/
    public String nom_visitable(){
        return visitat.nom();
    }
}
