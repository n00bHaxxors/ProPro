/** @class MT_Indirecte
 @brief Recurs que permet desplaçar-nos entre llocs primaris
 @author Marc Cané Salamià
 */

import java.time.*;

public class MT_Indirecte extends Mitja_Transport {
    //private Coordenada ubicacio; //ja estarà a lloc origen?
    private Lloc origen, desti;

    private LocalTime temps_recomanat; //temps per arribar fins al mt
    private LocalTime temps_previst; //trasllat des del mitj`a de transport en el lloc primari de dest´ı fins a qualsevol dels seus llocs secundaris associats (´unic per a tots els llocs secundaris)
    private LocalTime temps_trasllat_origen, temps_trasllat_desti;
    //crec que sobra algun temps.... vaia embolic l'enunciat...

    //Aquests són un paquet, potser hauriem de fer una "struct"?
    private LocalDateTime horaris[]; //Arraylist? //horari ha de ser per cada dia...
    private int preu[]; //Arraylist?
    private LocalTime durada[];

    /** @brief Constructor amb paràmetres
     @pre h no buida i p i d tenen la mateixa mida que h
     @post Mitjà de transport indirecte amb horari, preus i duracio creat*/
    public MT_Indirecte(String n, LocalDateTime h[], int p[], LocalTime d[]){
        super(n);
        horaris=h; preu=p; durada=d;
    }

    /** @brief Consultar horaris
     @pre cert
     @post Retorna els horaris del Mitjà de transport*/
    public LocalDateTime[] horaris(){
        return horaris;
    }

    /** @brief Consultar durada d'un mitjà
     @pre n>=0 i n<mida
     @post Retorna la durada del MT*/
    public LocalTime durada(int n){
        return durada[n];
    }

    /** @brief Consulta el preu per utilitzar el mitjà de transport
     @pre n>=0 i n<mida
     @post Retorna el preu per l'ús del MT*/
    public int preu(int n){ return preu[n]; }
}
