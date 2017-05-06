/** @file Client.java
    @brief Classe Allotjament
*/
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
/** @class Client
    @brief Client
    @author Ismael El Habri
*/


public class Client {
    private String nom;
    private ArrayList<Visita> visites;
    
    private HashSet<String> preferencies;
    
    /** @brief Crea un client amb els parametres
	@pre cert
	@post Client amb el nom n i les preferencies Pref  */
    public Client(String nomClient, ArrayList<String> Pref){
        nom = nomClient; 
        preferencies = new HashSet(Pref);        
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
        Visita temp = new Visita(PuntVisita, data);
        visites.add(temp);
    }
    
    /** @brief consulta si el client ha visitat anteriorment un visitable o no
	@pre cert
	@post retorna cert si si s'ha visitat punt i fals en c.c. */
    public boolean shaVisitat(Visitable punt){
        Iterator<Visita>  itr = visites.iterator();
        boolean trobat = false;
        Visita temp;
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
    
    @Override
    public int hashCode(){
        return nom.hashCode();
    }
}
