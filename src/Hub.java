/** @file Hub.java
 @brief Classe Hub
 */
import java.time.LocalTime;
import java.util.ArrayList;

/** @class Hub
 @brief Hub
 @author Lluís Trilla ft. Markus
 */

public class Hub {

    private LocalTime temps_trasllat_origen, temps_trasllat_desti;
    private ArrayList<MT_Indirecte> transports; //Superclasse??
    private Lloc desti;

    /** @brief Constructor amb paràmetres
     @pre WIP
     @post Mitjà de transport indirecte amb ?? */
    public Hub(LocalTime o, LocalTime d, Lloc de, ArrayList<MT_Indirecte> tr){
        temps_trasllat_origen=o; temps_trasllat_desti=d; desti=de; transports = tr;
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
        return transports.get(n).durada();
    }

    /** @brief Consulta el preu per utilitzar el mitjà de transport
     @pre n>=0 i n<mida
     @post Retorna el preu per l'ús del MT*/
    public int preu(int n){ return transports.get(n).preu(); }

}