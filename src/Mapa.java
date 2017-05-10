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
    private HashMap<String,PuntInteres> mapa;
    
    /** @brief crea un mapa amb el grup de clients i el conjunt de llocs donats
	@pre cert
	@post crea un Mapa amb els clients i els llocs donats
    */
    public Mapa(GrupClients gClients, ArrayList<PuntInteres> punts) {
        clients = gClients;
        mapa = new HashMap<>();
        Iterator<PuntInteres> itr = punts.iterator();
        PuntInteres aux;
        while (itr.hasNext()) {
            aux = itr.next();
            mapa.put(aux.nom(), aux);
        }
    }
    /** @brief busca el circuit optim entre dos llocs.
	@pre cert
	@post retorna el circit optim entre dos llocs.
    */
    public Circuit Dijkstra(PuntInteres a, PuntInteres b){
        return null; //s'ha de fer i ficar la puta pre i post
    }
}
