
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/** @file PuntInteres.java
    @brief Classe PuntInteres
*/

/** @class PuntInteres
    @brief Punt de Interès en el mapa
    @author Ismael El Habri
*/

public abstract class Backtracking {
    private static Circuit solucio_optima, solucio_actual;
    
    public static Circuit CircuitMesBarat(Mapa g, PuntInteres a, PuntInteres b, Set<PuntInteres> c){
        solucio_optima = new Circuit(); solucio_actual = new Circuit();
        AlgBTPreu(g,a,b,c);
        return solucio_optima;
    }
    
    
    private static void AlgBTPreu(Mapa g, PuntInteres a, PuntInteres b, Set<PuntInteres> c){
        Iterator<Activitat> itr = inicialitzarCandidats(solucio_actual.ultimaActivitat());
        while (itr.hasNext()){
            Activitat act = itr.next();
            if(Acceptable(act) && EsPotTrobarMillor(act)){
                AnotarCandidat(act);
                if (!SolucioCompleta()) AlgBTPreu(g,a,b,c);
                else{
                    if (MillorQueOptima()) solucio_optima = solucio_actual;
                }
                DesanotarCandidat();
            }
        }
    }
    
    private static Iterator<Activitat> inicialitzarCandidats(PuntInteres a){
        return null;
    }
    
    private static boolean Acceptable(Activitat a){
    }
    
    private static boolean EsPotTrobarMillor(Activitat a){
        
    }
    
    private static void AnotarCandidat(Activitat a){
        
    }
    
    private static void DesanotarCandidat(){
        
    }
    
    private static boolean SolucioCompleta(){
        
    }
    
    private static boolean MillorQueOptima(){
        
    }

}
