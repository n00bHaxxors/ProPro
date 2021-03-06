/** @file Hub.java
 @brief Classe Hub
 */
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

/** @class Hub
 @brief Classe que agrupa diversos transports indirectes del mateix tipus
 @author Lluís Trilla ft. Marc Cané
 */

public class Hub {

    private LocalTime temps_trasllat_origen, temps_trasllat_desti; //< Temps que es tarda per arribar o marxar del hub
    private ArrayList<MT_Indirecte> transports; //< Llista de transports del mateix hub
    private Lloc desti; //< Destinacio dels transports del hub
    private String nom; //< Nom del mitjà de transport

    /** @brief Constructor amb paràmetres
     @pre nom, o, d i de no nuls
     @post Mitjà de transport indirecte amb nom, temps origen, temps desti, lloc desti i conjunt de MT_Indirectes creat*/
    public Hub(String n,LocalTime o, LocalTime d, Lloc de, ArrayList<MT_Indirecte> tr){
        temps_trasllat_origen=o; temps_trasllat_desti=d; desti=de; transports = tr; nom=n;
        transports = new ArrayList<MT_Indirecte>();
    }

    /** @brief Consulta el preu per utilitzar el mitjà de transport
     @pre n>=0 i n<mida
     @post Retorna el preu per l'ús del MT */
    public int preu(int n){ return transports.get(n).preu(); }
    
    /** @brief Consulta el lloc de desti
     @pre cert
     @post Retorna el lloc de desti*/
    public Lloc destinacio(){ return desti;}

    /** @brief Ens dona un iterador per accedir als transports indirectes del hub
     @pre cert
     @post Retorna un iterador a la llista de MT_Indirecte */
    public Iterator<MT_Indirecte> transports(){
        return transports.iterator();
    }

    /** @brief Consultar durada d'un mitjà
     @pre n>=0 i n<mida
     @post Retorna la durada del MT*/
    public LocalTime durada(int n){
        return transports.get(n).durada();
    }
    
    /** @brief Consulta el que es tardara en trasllat al destí 
     @pre cert
     @post Retorna el temps de trasllat al desti */
    public LocalTime tempsTrasllatDesti(){
        return temps_trasllat_desti;
    }
    
    /** @brief Consulta el que es tardara en trasllat al origen
     @pre cert
     @post Retorna el temps de trasllat al origen*/
    public LocalTime tempsTrasllatOrigen(){
        return temps_trasllat_origen;
    }

    /** @brief Calcula el que es tardara en trasllat al origen i al destí
     @pre cert
     @post Retorna el temps de trasllat total*/
    public LocalTime tempsTrasllatTotal(){
        return temps_trasllat_origen.plusHours(temps_trasllat_desti.getHour()).plusMinutes(temps_trasllat_desti.getMinute());
    }
}