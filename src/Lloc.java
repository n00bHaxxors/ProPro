/** @file Lloc.java
 @brief Classe Lloc
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimeZone;
/** @class Lloc
 @brief Lloc
 @author Lluís Trilla
 */

public class Lloc extends Localitzacio{
    private String Nom;
    private Coordenada CoordenadaLloc;
    private TimeZone Zona;
    private ArrayList<PuntInteres> puntsInteres;
    private ArrayList<MT_Directe> mitjansDirectes;
    private ArrayList<Hub> hubs;

    /**
     * @brief Associa un Punt d'interés al lloc
     * @pre cert
     * @post Associa el punt d'interés
     */
    public void associarPuntInteres(PuntInteres v){puntsInteres.add(v);}
    /**
     * @brief Associa un transport directe sense desti a lloc
     * @pre cert
     * @post Associa el transport directe
     */
    public void associarTransport(MT_Directe d){mitjansDirectes.add(d);}
    /**
     * @brief Associa un transport directe a lloc
     * @pre cert
     * @post Associa el transport directe
     */
    public void associarTransportDirecte(MT_Directe d){mitjansDirectes.add(d);}
    /**
     * @brief Afegeix un hub al lloc
     * @pre cert
     * @post Afegeix el hub a la llista de hubs
     */
    public void associarHub(Hub h){hubs.add(h);}
    /**
     * @brief Retorna un iterador als punts d'interés del lloc
     * @pre cert
     * @post Retorna l'iterador
     */
    public Iterator<PuntInteres> puntsInteres(){
        return puntsInteres.iterator();
    }
    /**
     * @brief Retorna un iterador als mitjans de transport directes
     * @pre cert
     * @post Retorna l'iterador
     */
    public Iterator<MT_Directe> mitjansDirectes(){
        return mitjansDirectes.iterator();
    }
    /**
     * @brief Retorna un iterador als hubs del lloc
     * @pre cert
     * @post Retorna l'iterador
     */
    public Iterator<Hub> hubs(){
        return hubs.iterator();
    }

    /**
     * @brief Crea un lloc a partir d'una coordenada, un nom i una zona
     * @pre cert
     * @post Lloc amb el nom, la coordenada i la zona que li hem assignat
     */
    public Lloc(String n, Coordenada c, TimeZone z) {
        Nom=n;
        CoordenadaLloc = c;
        Zona = z;
        puntsInteres = new ArrayList<PuntInteres>();
        mitjansDirectes = new ArrayList<MT_Directe>();
        hubs = new ArrayList<Hub>();
    }

    /** @brief Retorna el nom del lloc
     @pre cert
     @post Retorna el nom del lloc
     */
    @Override
    public String nom(){
        return Nom;
    }
    /** @brief Retorna la coordenada geogràfica del lloc
     @pre cert
     @post Retorna la coordenada geogràfica del lloc
     */
    @Override
    public Coordenada coordenada(){
        return CoordenadaLloc;
    }
    /** @brief Retorna la zona horària del lloc
     @pre cert
     @post Retorna la zona horària del lloc
     */
    @Override
    public TimeZone zona(){
        return Zona;
    }
}
