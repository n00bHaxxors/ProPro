/** @file GrupClients.java
 @brief Classe GrupClients
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/** @class GrupClients
 @brief Grup de clients
 @author Lluís Trilla
 */

public class GrupClients {
    private ArrayList<Client> clients; //< Llista de clients

    /**
     * @brief Crea un GrupClients
     * @pre cert
     * @post Crea un GrupClients buit
     */
    public GrupClients(ArrayList<Client> aC){clients=new ArrayList(aC);}
    /**
     * @brief Retorna un iterador de clients del grup
     * @pre cert
     * @post Retorna l'iterador
     */
    public Iterator<Client> iteradorClients(){
        return clients.iterator();
    }
}