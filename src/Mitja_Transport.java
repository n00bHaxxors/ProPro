/** @class Mitjà Transport
 @brief Recurs que permet als nostres clients desplaçar-se entre ubicacions
 @author Marc Cané Salamià
 */

import java.time.LocalTime;

abstract class Mitja_Transport {
    /** @invariant preu>=0
     */
    private String nom; //<nom del mitjà de transport
    private int preu; //<preu per usar el mitjà de transport
    private LocalTime durada; //<durada del viatge amb el mitjà de transport

    /** @brief Constructor per defecte
    @pre cert
    @post Mitja de transport buit creat*/
    public Mitja_Transport(String n, int p, LocalTime d){
        nom = n; preu=p; durada=d;
    }

    /** @brief Consulta el nom del mitja de transport
    @pre cert
    @post Retorna el nom del mitjà de transport*/
    public String nom(){
    return nom;
    }

    /** @brief Consulta el preu d'us del mitjà de trasport
     @pre cert
     @post Retorna el preu del mitja de transport*/
    public int preu(){
        return preu;
    }

    /** @brief Consulta la durada del mitjà de trasport
     @pre cert
     @post Retorna la durada del mitja de transport*/
    public LocalTime durada(){
        return durada;
    }

}
