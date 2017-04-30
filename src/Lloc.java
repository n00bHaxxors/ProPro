/** @file Lloc.java
 @brief Classe Lloc
 */
import java.util.TimeZone;
/** @class Lloc
 @brief Lloc
 @author Lluís Trilla
 */

public class Lloc {
    private String nom;
    private Coordenada coordenadaLloc;
    private TimeZone Zona;

    /**
     * @brief Crea un lloc a partir d'una coordenada, un nom i una zona
     * @pre cert
     * @post Lloc amb el nom, la coordenada i la zona que li hem assignat
     */
    public Lloc(String n, Coordenada c, TimeZone z) {
        nom=n;
        coordenadaLloc = c;
        zona = z;
        
    }
}
