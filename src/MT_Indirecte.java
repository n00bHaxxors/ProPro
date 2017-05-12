/** @class MT_Indirecte
 @brief Recurs que permet desplaçar-nos entre llocs primaris
 @author Marc Cané Salamià
 */

import java.time.*;
import java.util.ArrayList;

public class MT_Indirecte extends Mitja_Transport {

    public LocalDate dia;
    public LocalTime hora;
    public LocalTime durada;
    public int preu; //hauria d'anar a la superclasse

    public MT_Indirecte (String n, LocalDate h, LocalTime hora, LocalTime d, int p){
        super(n);
        dia=h; hora=hora; durada=d; preu=p;
    }

}
