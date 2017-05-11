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
    private HashMap<String, Lloc> mapaLlocs;
    
    /** @brief crea un mapa amb el grup de clients i el conjunt de llocs donats
	@pre cert
	@post crea un Mapa amb els clients i els llocs donats
    */
    public Mapa(GrupClients gClients, ArrayList<PuntInteres> punts, ArrayList<Lloc> llocs) {
        clients = gClients;
        mapa = new HashMap<>();
        Iterator<PuntInteres> itr = punts.iterator();
        PuntInteres aux;
        while (itr.hasNext()) {
            aux = itr.next();
            mapa.put(aux.nom(), aux);
        }
        mapaLlocs = new HashMap<>();
        Iterator<Lloc> itr2 = llocs.iterator();
        Lloc aux2;
        while (itr2.hasNext()) {
            aux2 = itr2.next();
            mapaLlocs.put(aux2.nom(), aux2);
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
