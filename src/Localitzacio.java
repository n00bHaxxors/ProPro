/** @file Localitzacio.java
 @brief Classe Localitzacio
 */

import java.util.TimeZone;

/** @class Localitzacio
 @brief Classe que engloba Lloc i PuntInteres
 @author Ismael El Habri
 */


public abstract class Localitzacio {
    
    public abstract String nom();
    
    public abstract Coordenada coordenada();
    
    public abstract TimeZone zona();
}
