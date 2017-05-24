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
    /** @invariant atributs != null
        */
    private GrupClients clients; //<contenidor de clients
    private HashMap<String,Visitable> mapaV; //< contenidor de visitables
    private HashMap<String,Allotjament> mapaA;//<contenidor de allotjaments 
    private HashMap<String, Lloc> mapaLlocs; //<contenidor de llocs
    private ArrayList<Viatge> viatges; //< contenidor de viatges solicitats

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
    
    /** @brief consulta si hi ha un visitable amb el nom donat
	@pre cert
	@post retorna cert si existeix un visitable anomenat n i fals en c.c.
    */
    public boolean conteVisitable(String n){ return mapaV.containsKey(n); }
    
    /** @brief consulta si hi ha un Allotjament amb el nom donat
	@pre cert
	@post retorna cert si existeix un visitable anomenat n i fals en c.c.
    */
    public boolean conteAllotjament(String n){ return mapaA.containsKey(n); }
    
    /** @brief consulta el Punt d'Interes demanat
	@pre cert
	@post retorna el punt d'interes amb nom n i si no existeix retorna nulls
    */
    public PuntInteres puntInteres(String n) {
        if (mapaV.containsKey(n)) return mapaV.get(n);
        else if (mapaA.containsKey(n)) return mapaA.get(n);
        else return null;
    }
}
