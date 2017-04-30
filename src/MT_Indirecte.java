/** @class MT_Indirecte
 @brief Recurs que permet desplaçar-nos entre llocs primaris
 @author Marc Cané Salamià
 */
public class MT_Indirecte extends Mitja_Transport {
    //private Coordenada ubicacio; //ja estarà a lloc origen?
    private Lloc origen, desti;

    private LocalTime temps_recomanat; //temps per arribar fins al mt
    private LocalTime temps_previst; //trasllat des del mitj`a de transport en el lloc primari de dest´ı fins a qualsevol dels seus llocs secundaris associats (´unic per a tots els llocs secundaris)
    private LocalTime temps_trasllat_origen, temps_trasllat_desti;
    //crec que sobra algun temps.... vaia embolic l'enunciat...

    //Aquests són un paquet, potser hauriem de fer una "struct"?
    private LocalDate horaris[]; //Arraylist? //horari ha de ser per cada dia...
    private int preu[]; //Arraylist?
    private LocalTime durada[];

    public LocalDate[] horaris(){
        return horaris;
    }

    public LocalTime durada(int n){
        return preu[n];
    }
    public int preu(int n, int ){ return preu[n]; }
}
