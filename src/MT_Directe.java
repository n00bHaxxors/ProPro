/** @class MT_Directe
 @brief Recurs que permet als clients desplaçar-se entre destinacions secundàries o primàries que no tinguin destinacions secundàries associades (cas especial a tenir en compte)
 @author Marc Cané Salamià
 */

import java.time.*;

public class MT_Directe extends Mitja_Transport {
    private int preu;
    private LocalTime durada;

    /** @brief Constructor amb parametres
     @pre cert
     @post Mitja de transport amb nom, preu i durada creat*/
    public Mitja_Transport(String n, int p, LocalTime d){
        nom=n; preu=p; durada=d;
    }

    /** @brief Consulta el preu d'us del mitjà de trasport
     @pre cert
     @post Retorna el preu del mitja de transport*/
    public int preu(){
        return preu;
    }
}
