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
    public MT_Directe(String n, int p, LocalTime d, PuntInteres dest){ //connexio directe
        super(n); preu=p; durada=d; desti=dest;
    }
 
    /** @brief Constructor amb parametres
     @pre cert
     @post Mitja de transport amb nom, preu i durada creat*/
    public MT_Directe(String n, int p, LocalTime d){ //transport random
        super(n); preu=p; durada=d; desti=null;  
    }
 
    /** @brief Consulta el preu d'us del mitjà de trasport
     @pre cert
     @post Retorna el preu del mitja de transport*/
    public int preu(){
        return preu;
    }
}
