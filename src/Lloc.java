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

public class Lloc {
    private String Nom;
    private Coordenada CoordenadaLloc;
    private TimeZone Zona;
    private ArrayList<PuntInteres> puntsInteres;
    private ArrayList<MT_Directe> mitjansDirectes;
    private ArrayList<Hub> hubs;


    public void associarPuntInteres(PuntInteres v){puntsInteres.add(v);}
    public void associarTransport(MT_Directe d){mitjansDirectes.add(d);}
    public void associarTransportDirecte(MT_Directe d){mitjansDirectes.add(d);}
    public void associarHub(Hub h){hubs.add(h);}
    public Iterator<PuntInteres> puntsInteres(){
        return puntsInteres.iterator();
    }
    public Iterator<MT_Directe> mitjansDirectes(){
        return mitjansDirectes.iterator();
    }
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
    public String nom(){
        return Nom;
    }
    /** @brief Retorna la coordenada geogràfica del lloc
     @pre cert
     @post Retorna la coordenada geogràfica del lloc
     */
    public Coordenada coordenada(){
        return CoordenadaLloc;
    }
    /** @brief Retorna la zona horària del lloc
     @pre cert
     @post Retorna la zona horària del lloc
     */
    public TimeZone zona(){
        return Zona;
    }
}
