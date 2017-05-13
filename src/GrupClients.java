/** @file GrupClients.java
 @brief Classe GrupClients
 */
import java.util.HashSet;
import java.util.Iterator;

/** @class GrupClients
 @brief GrupClients
 @author Lluís Trilla
 */

public class GrupClients {
    private HashSet<Client> clients;

    /**
     * @brief Crea un GrupClients
     * @pre cert
     * @post Crea un GrupClients buit
     */
    public GrupClients(){}
    
    public Iterator<Client> iteradorClients(){
        return clients.iterator();
    }
}