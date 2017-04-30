/** @class Desplaçament
 @brief Canvi d'una ubicació a una altre fet amb un mitjià de transport
 @author Marc Cané Salamià
 */
public class Desplaçament {
    private int duracio, preu;
    private Mitja_Transport mitja;

    /** @brief Consulta la duracio del desplaçament
     @pre cert
     @post Retorna la duració del desplaçament en segons*/
    public int duracio() {
        return duracio;
    }

    /** @brief Consulta el preu del desplaçament
     @pre cert
     @post Retorna el preu del desplaçament*/
    public int preu(){
        return preu;
    }

    /** @brief Consulta el mitjà de transport usat en el desplaçament
     @pre cert
     @post Retorna el mitjà de transport usat*/
    public String mitja_usat(){
        return mitja;
    }

}
