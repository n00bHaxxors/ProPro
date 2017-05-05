/** @file Mapa.java
    @brief Classe Mapa
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/** @class Visitable
    @brief Classe amb el conjunt de dades del mapa i el grup de clients que estem tractant
    @author Ismael El Habri
*/
public class Mapa {
    private GrupClients clients;
    private HashMap<String,Lloc> mapa;
    
    /** @brief crea un mapa amb el grup de clients i el conjunt de llocs donats
	@pre cert
	@post crea un Mapa amb els clients i els llocs donats
    */
    public Mapa(GrupClients gClients, ArrayList<Lloc> llocs) {
        clients = gClients;
        mapa = new HashMap<>();
        Iterator itr = llocs.iterator();
        Lloc aux;
        while (itr.hasNext()) {
            aux = (Lloc) itr.next();
            mapa.put(aux.nom(), aux);
        }
    }

    public Circuit Dijkstra(Lloc a, Lloc b){
        return null; //s'ha de fer i ficar la puta pre i post
    }
}
