/** @class MT_Directe
 @brief Recurs que permet als clients desplaçar-se entre destinacions secundàries o primàries que no tinguin destinacions secundàries associades
 @author Marc Cané Salamià
 */

import java.time.*;

public class MT_Directe extends Mitja_Transport {

    private PuntInteres desti; //<Destí del mitjà de transport

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

    /** @brief Crea un desplaçament amb les dades del mitjà de transport
     @pre cert
     @post Retorna el desplaçament que fem amb el mitjà de transport*/
    public Desplaçament desplaçament(LocalDate dia, LocalTime hora, PuntInteres origen){
        return new Desplaçament(preu(),dia,hora,this,origen,desti,durada());
    }

    /** @brief Crea un desplaçament amb les dades del mitjà de transport i punt d'interes desti
     @pre cert
     @post Retorna el desplaçament que fem amb el mitjà de transport*/
    public Desplaçament desplaçament(LocalDate dia, LocalTime hora, PuntInteres origen, PuntInteres d){
        return new Desplaçament(preu(),dia,hora,this,origen,d,durada());
    }
}
