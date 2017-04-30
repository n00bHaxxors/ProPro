/** @class Mitjà Transport
 @brief Recurs que permet als nostres clients desplaçar-se entre ubicacions
 @author Marc Cané Salamià
 */
public class Mitja_Transport {
    private String nom;
    private int preu, durada;

    /** @brief Constructor per defecte
     @pre cert
     @post Mitja de transport buit creat*/
    public Mitja_Transport(){}

    /** @brief Constructor amb parametres
     @pre cert
     @post Mitja de transport amb nom, preu i durada creat*/
    public Mitja_Transport(String n, int p, int d){
        nom=n; p=preu; durada=d;
    }

    /** @brief Consulta el preu d'us del mitjà de trasport
     @pre cert
     @post Retorna el preu del mitja de transport*/
    public int preu(){
        return preu;
    }

    /** @brief Consulta el nom del mitja de transport
     @pre cert
     @post Retorna el nom del mitjà de tranport*/
    public String nom(){
        return nom;
    }
}
