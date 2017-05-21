/** @class MT_Indirecte
 @brief Recurs que permet desplaçar-nos entre llocs primaris
 @author Marc Cané Salamià
 */

import java.time.*;

public class MT_Indirecte extends Mitja_Transport {

    private LocalDate dia;
    private LocalTime hora;

    public MT_Indirecte (String n, LocalDate h, LocalTime hor, LocalTime d, int p){
        super(n,p,d);
        dia=h; hora=hor;
    }
    
    public LocalDateTime diaHoraSortida(){ return dia.atTime(hora); }

}
