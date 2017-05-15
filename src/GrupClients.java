/** @file GrupClients.java
 @brief Classe GrupClients
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/** @class GrupClients
 @brief GrupClients
 @author Lluís Trilla
 */

public class GrupClients {
    private ArrayList<Client> clients;

    /**
     * @brief Crea un GrupClients
     * @pre cert
     * @post Crea un GrupClients buit
     */
    public GrupClients(ArrayList<Client> aC){clients=aC;}
    
    public Iterator<Client> iteradorClients(){
        return clients.iterator();
    }
}