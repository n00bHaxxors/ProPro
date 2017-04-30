/** @class MT_Indirecte
 @brief Recurs que permet desplaçar-ns entre llocs primaris
 @author Marc Cané Salamià
 */
public class MT_Indirecte extends Mitja_Transport {
    //private Coordenada ubicacio; //ja estarà a lloc origen?
    private Lloc origen, desti;
    private localtime temps_recomanat; //temps per arribar fins al mt
    private localtime temps_previst; //trasllat des del mitj`a de transport en el lloc primari de dest´ı fins a qualsevol dels seus llocs secundaris associats (´unic per a tots els llocs secundaris)
    private localtime temps_trasllat_origen, temps_trasllat_desti;
    //crec que sobra algun temps.... vaia embolic l'enunciat...

    //Aquests són un paquet, potser hauriem de fer una "struct"?
    private localDate horaris[]; //Arraylist? //horari ha de ser per cada dia...
    private int preu[]; //Arraylist?
    private localtime duracio[];

    public localDate[] horaris(){
        return horaris;
    }

    public localtime duracio(int n){
        return preu[n];
    }
}
