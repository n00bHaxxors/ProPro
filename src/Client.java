/** @file Client.java
    @brief Classe Allotjament
*/
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/** @class Client
    @brief Classe contenidora de un Client
    @author Ismael El Habri
*/

public class Client {
    
    /** @class VisitaAnterior
    @brief Classe que conté una visita anterior de un visitable feta per un Client
    @author Ismael El Habri
    */
    private class VisitaAnterior{
        /** @invariant v!=null && data != null
        */
        private Visitable v; //< visitable visitat
        private LocalDate data; //<dia de la visita
        /** @brief Crea una visita anterior feta pel client
            @pre visitat && d diferents de null
            @post Visita creada amb els parametres  */
        public VisitaAnterior (Visitable visitat, LocalDate d){ v=visitat; data=d; }
        /** @brief consulta el nom del visitable
            @pre cert
            @post Retorna el nom del vistable visitat anteriorment  */
        public String nom_visitable(){ return v.nom(); }
    }
    
    /** @invariant nom!=null && preferencies != null && visites != null
     */
    private String nom; //<nom del client
    private ArrayList<VisitaAnterior> visites; //<llistat de visites anteriorment fetes pel client
    private HashSet<String> preferencies; //conjunt de preferencies del Client
    
    /** @brief Crea un client amb els parametres
	@pre cert
	@post Client amb el nom n i les preferencies Pref  */
    public Client(String nomClient, ArrayList<String> Pref){
        nom = nomClient; 
        preferencies = new HashSet(Pref);
        visites = new ArrayList<VisitaAnterior>();
    }
    
    /** @brief Consulta si el client te una preferencia concreta o no
	@pre cert
	@post retorna cert si té la preferencia car i fals en c.c. */
    public boolean tePreferencia(String car){
        return preferencies.contains(car);
    }
    
    /** @brief Afageix una visita previa al client
	@pre cert
	@post Client amb nova visita */
    public void afegirVisita(Visitable PuntVisita, LocalDate data) {
        VisitaAnterior temp = new VisitaAnterior(PuntVisita, data);
        visites.add(temp);
    }
    
    /** @brief consulta si el client ha visitat anteriorment un visitable o no
	@pre cert
	@post retorna cert si si s'ha visitat punt i fals en c.c. */
    public boolean shaVisitat(Visitable punt){
        Iterator<VisitaAnterior>  itr = visites.iterator();
        boolean trobat = false;
        VisitaAnterior temp;
        while (itr.hasNext() && !trobat){
            temp = itr.next();
            trobat = punt.nom().equals(temp.nom_visitable());
        }
        return trobat;
    }
    
    /** @brief Consulta el nom del client
	@pre cert
	@post retorna el nom del client */
    public String nom(){return nom;}
    
    /** @brief consulta les preferencies d'un clint
	@pre cert
	@post retorna un iterador a les seves preferencies */
    public Iterator <String> IteradorPreferencies(){
        return preferencies.iterator();
    }
    
    @Override
    public int hashCode(){
        return nom.hashCode();
    }
}
