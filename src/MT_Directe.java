/** @class MT_Directe
 @brief Recurs que permet als clients desplaçar-se entre destinacions secundàries o primàries que no tinguin destinacions secundàries associades (cas especial a tenir en compte)
 @author Marc Cané Salamià
 */

//1-portar a un lloc amb tots els seus fills (sense origen desti)

//2-Conexio directe: té origen i desti (que son secundaris del mateix lloc)

import java.time.*;

public class MT_Directe extends Mitja_Transport {
    private int preu;
    private LocalTime durada;
    private PuntInteres desti;

    /** @brief Constructor amb parametres
     @pre cert
     @post Mitja de transport amb nom, preu, durada creat*/
    public Mitja_Transport(String n, int p, LocalTime d, PuntInteres d){ //connexio directe
        nom=n; preu=p; durada=d;
    }
 
    /** @brief Constructor amb parametres
     @pre cert
     @post Mitja de transport amb nom, preu i durada creat*/
    public Mitja_Transport(String n, int p, LocalTime d){ //transport random
        Mitja_Transport(n,p,d,NULL);    
    }
 
    /** @brief Consulta el preu d'us del mitjà de trasport
     @pre cert
     @post Retorna el preu del mitja de transport*/
    public int preu(){
        return preu;
    }
}
