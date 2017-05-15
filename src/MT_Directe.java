/** @class MT_Directe
 @brief Recurs que permet als clients desplaçar-se entre destinacions secundàries o primàries que no tinguin destinacions secundàries associades (cas especial a tenir en compte)
 @author Marc Cané Salamià
 */

//1-portar a un lloc amb tots els seus fills (sense origen desti)

//2-Conexio directe: té origen i desti (que son secundaris del mateix lloc)

import java.time.*;

public class MT_Directe extends Mitja_Transport {

    private PuntInteres desti;

    /** @brief Constructor amb parametres
     @pre cert
     @post Mitja de transport amb nom, preu, durada creat*/
    public MT_Directe(String n, int p, LocalTime d, PuntInteres dest){ //connexio directe
        super(n,p,d); desti=dest;
    }
 
    /** @brief Constructor amb parametres
     @pre cert
     @post Mitja de transport amb nom, preu i durada creat*/
    public MT_Directe(String n, int p, LocalTime d){ //transport random
        super(n,p,d); desti=null;
    }

    public Desplaçament desplaçament(LocalDate dia, LocalTime hora, PuntInteres origen){
        return new Desplaçament(preu(),dia,hora,this,origen,desti);
    }
    
    public Desplaçament desplaçament(LocalDate dia, LocalTime hora, PuntInteres origen, PuntInteres d){
        return new Desplaçament(preu(),dia,hora,this,origen,d);
    }
}
