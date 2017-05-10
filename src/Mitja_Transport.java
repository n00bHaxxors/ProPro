/** @class Mitjà Transport
 @brief Recurs que permet als nostres clients desplaçar-se entre ubicacions
 @author Marc Cané Salamià
 */
abstract class Mitja_Transport {
    private String nom;

    /** @brief Constructor per defecte
     @pre cert
     @post Mitja de transport buit creat*/
    public Mitja_Transport(String n){ nom = n; }

    /** @brief Consulta el nom del mitja de transport
     @pre cert
     @post Retorna el nom del mitjà de transport*/
    public String nom(){
        return nom;
    }

}
