/** @class MT_Indirecte
 @brief Recurs que permet desplaçar-nos entre llocs primaris
 @author Marc Cané Salamià
 */

import java.time.*;
import java.util.ArrayList;

public class MT_Indirecte extends Mitja_Transport {

    public static class Partença{
        public Partença(LocalDate h,LocalTime hora, LocalTime d, int p){
            dia=h; hora=hora;durada=d; preu=p;
        }
        public LocalDate dia;
        public LocalTime hora;
        public LocalTime durada;
        public int preu;
    }
    private LocalTime temps_trasllat_origen, temps_trasllat_desti;
    private ArrayList<Partença> partences;
    private Lloc desti;

    /** @brief Constructor amb paràmetres
     @pre WIP
     @post Mitjà de transport indirecte amb ?? */
    public MT_Indirecte(String n, LocalTime o, LocalTime d, Lloc de, ArrayList<Partença> part){
        super(n);
        temps_trasllat_origen=o; temps_trasllat_desti=d; desti=de; partences = part;
    }

    /** @brief Consultar horaris
     @pre cert
     @post Retorna els horaris del Mitjà de transport*/
    /*public LocalDateTime[] horaris(){
        return horaris;
    }*/ //WIP

    /** @brief Consultar durada d'un mitjà
     @pre n>=0 i n<mida
     @post Retorna la durada del MT*/
    public LocalTime durada(int n){
        return partences.get(n).durada;
    }

    /** @brief Consulta el preu per utilitzar el mitjà de transport
     @pre n>=0 i n<mida
     @post Retorna el preu per l'ús del MT*/
    public int preu(int n){ return partences.get(n).preu; }
}
