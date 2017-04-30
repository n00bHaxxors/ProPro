
/** @class Visita
 @brief Lloc on ja ha estat un client (ta ok?)
 @author Marc Cané Salamià
 */
public class Visita {
    private Lloc visitat; //entenc que no ha de ser un array...
    private LocalDate data_visita; //?
    //ajudeume a posar atributs aqui

    /** @brief Constructor per defecte
     @pre cert
     @post Visita buida creada*/
    public Visita(){}

    /** @brief Constructor amb paràmetres
     @pre cert
     @post Visita amb lloc visitat i data de visita creada*/
    public Visita(Lloc v, LocalDate data){
        visitat=v; data_visita=data;
    }
}
