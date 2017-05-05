/** @class MT_Indirecte
 @brief Recurs que permet desplaçar-nos entre llocs primaris
 @author Marc Cané Salamià
 */

import java.time.*;

public class MT_Indirecte extends Mitja_Transport {

    public static class Partença{
        public Partença(LocalDateTime h, LocalTime d, int p){
            horari=h; durada=d; preu=p;
        }
        public LocalDateTime horari;
        public LocalTime durada;
        public int preu;
    }
    //private Coordenada ubicacio; //ja estarà a lloc origen?
    private Partença partences[]; //Arraylist?
    private Lloc origen, desti;

    private LocalTime temps_recomanat; //temps per arribar fins al mt
    private LocalTime temps_previst; //trasllat des del mitj`a de transport en el lloc primari de dest´ı fins a qualsevol dels seus llocs secundaris associats (´unic per a tots els llocs secundaris)
    private LocalTime temps_trasllat_origen, temps_trasllat_desti;
    //crec que sobra algun temps.... vaia embolic l'enunciat...


    /** @brief Constructor amb paràmetres
     @pre h no buida i p i d tenen la mateixa mida que h
     @post Mitjà de transport indirecte amb horari, preus i duracio creat*/
    /*public MT_Indirecte(String n, LocalDateTime h[], int p[], LocalTime d[]){
        super(n);
        horaris=h; preu=p; durada=d;
    }*/
    public MT_Indirecte(String n, Partença [] part){
        super(n);
        partences = part;
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
        return partences[n].durada;
    }

    /** @brief Consulta el preu per utilitzar el mitjà de transport
     @pre n>=0 i n<mida
     @post Retorna el preu per l'ús del MT*/
    public int preu(int n){ return partences[n].preu; }
}
