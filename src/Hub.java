/** @file Hub.java
 @brief Classe Hub
 */
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

/** @class Hub
 @brief Hub
 @author Lluís Trilla ft. Markus
 */

public class Hub {

    private LocalTime temps_trasllat_origen, temps_trasllat_desti;
    private ArrayList<MT_Indirecte> transports; //Superclasse??
    private Lloc desti;
    private String nom;

    /** @brief Constructor amb paràmetres
     @pre WIP
     @post Mitjà de transport indirecte amb ?? */
    public Hub(String n,LocalTime o, LocalTime d, Lloc de, ArrayList<MT_Indirecte> tr){
        temps_trasllat_origen=o; temps_trasllat_desti=d; desti=de; transports = tr; nom=n;
        transports = new ArrayList<MT_Indirecte>();
    }
    public Iterator<MT_Indirecte> transports(){
        return transports.iterator();
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
    
    /** @brief Consulta el lloc de desti
     @pre cert
     @post Retorna el lloc de desti*/
    public Lloc destinacio(){ return desti;}
    
    /** @brief Calcula el que es tardara en trasllat al origen i al destí 
     @pre cert
     @post Retorna el temps de trasllat total*/
    public LocalTime tempsTrasllatTotal(){
        return temps_trasllat_origen.plusHours(temps_trasllat_desti.getHour()).plusMinutes(temps_trasllat_desti.getMinute());
    }
    
    /** @brief Consulta el que es tardara en trasllat al destí 
     @pre cert
     @post Retorna el temps de trasllat al desti*/
    public LocalTime tempsTrasllatDesti(){
        return temps_trasllat_desti;
    }
    
    /** @brief Consulta el que es tardara en trasllat al origen
     @pre cert
     @post Retorna el temps de trasllat al origen*/
    public LocalTime tempsTrasllatOrigen(){
        return temps_trasllat_origen;
    }

}