/** @file Localitzacio.java
 @brief Classe Localitzacio
 */

import java.util.TimeZone;

/** @class Localitzacio
 @brief Classe que engloba Lloc i PuntInteres
 @author Ismael El Habri
 */

public abstract class Localitzacio {
    /** @brief Consulta el nom de la localització
	@pre cert
	@post retorna el nom */
    public abstract String nom();
    
    /** @brief Consulta les coordenades de la localització
	@pre cert
	@post retorna les coordenades */
    public abstract Coordenada coordenada();
    
    /** @brief Consulta la zona horària de la Localització
	@pre cert
	@post retorna la zona */
    public abstract TimeZone zona();
}
