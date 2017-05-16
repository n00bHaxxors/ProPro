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
    private HashMap<String,Visitable> mapaV;
    private HashMap<String,Allotjament> mapaA;
    private HashMap<String, Lloc> mapaLlocs;
    private ArrayList<Viatge> viatges;

    /** @brief crea un mapa amb el grup de clients i el conjunt de llocs donats
	@pre cert
	@post crea un Mapa amb els clients i els llocs donats
    */
    public Mapa(GrupClients gClients, ArrayList<Visitable> visitables, ArrayList<Allotjament> hotels, ArrayList<Lloc> llocs, ArrayList<Viatge> arrayViatges) {
        clients = gClients;
        mapaV = new HashMap<>();
        Iterator<Visitable> itr = visitables.iterator();
        Visitable aux;
        while (itr.hasNext()) {
            aux = itr.next();
            mapaV.put(aux.nom(), aux);
        }
        mapaA = new HashMap<>();
        Iterator<Allotjament> itr1 = hotels.iterator();
        Allotjament aux1;
        while (itr1.hasNext()) {
            aux1 = itr1.next();
            mapaA.put(aux1.nom(), aux1);
        }
        mapaLlocs = new HashMap<>();
        Iterator<Lloc> itr2 = llocs.iterator();
        Lloc aux2;
        while (itr2.hasNext()) {
            aux2 = itr2.next();
            mapaLlocs.put(aux2.nom(), aux2);
        }
        viatges=arrayViatges;
    }
    
    /** @brief consulta el grup de clients
	@pre cert
	@post retorna el grup de clients
    */
    public GrupClients clients() { return clients; }
    
    /** @brief Consulta un lloc amb el nom
	@pre cert
	@post retorna el lloc amb nom n
    */
    public Lloc lloc(String n){ return mapaLlocs.get(n); }
    
    /** @brief consulta els viatges solicitats pel grup de clients
	@pre cert
	@post retorna un iterador de Viatges
    */
    public Iterator<Viatge> viatgesDemanats() { return viatges.iterator(); }
}
