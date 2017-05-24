/** @class MT_Indirecte
 @brief Recurs que permet als nostres clients desplaçar-se entre llocs primaris
 @author Marc Cané Salamià
 */

import java.time.*;

public class MT_Indirecte extends Mitja_Transport {
    /** @invariant 0<dia<=31, 00:00<hora<=24:00
     */
    private LocalDate dia; //<dia de sortida del mitjà de transport
    private LocalTime hora; //<hora de sortida del mitjà de transport

    /** @brief Constructor amb paràmetres
     @pre n, h, hor i d no nuls
     @post Mitjà de transport amb nom, data, hora, duracio i preu creat*/
    public MT_Indirecte (String n, LocalDate h, LocalTime hor, LocalTime d, int p){
        super(n,p,d);
        dia=h; hora=hor;
    }

    /** @brief Consulta la data de sortida
     @pre cert
     @post Retorna la data de sortida del mitjà de transport*/
    public LocalDateTime diaHoraSortida(){ return dia.atTime(hora); }

}
