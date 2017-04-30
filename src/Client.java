/** @file Client.java
    @brief Classe Allotjament
*/
import java.util.ArrayList;
import java.util.HashSet;
/** @class Client
    @brief Client
    @author Ismael El Habri
*/


public class Client {
    private String nom;
    
    
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
    
    
}
