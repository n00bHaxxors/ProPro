
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/** @file PuntInteres.java
    @brief Classe PuntInteres
*/

/** @class PuntInteres
    @brief Punt de Interès en el mapa
    @author Ismael El Habri
*/

public abstract class Backtracking {
    private static Circuit solucio_optima, solucio_actual;
    HashMap<String, Visita> visitesFetes;
    
    
    public static Circuit CircuitMesBarat(Mapa g, PuntInteres a, PuntInteres b, Set<PuntInteres> c, LocalDateTime diaInici){
        solucio_optima = new Circuit(diaInici); solucio_actual = new Circuit(diaInici);
        AlgBTPreu(g,a,b,c);
        return solucio_optima;
    }
    
    
    private static void AlgBTPreu(Mapa g, PuntInteres a, PuntInteres b, Set<PuntInteres> c){
        Iterator<Activitat> itr = inicialitzarCandidats();
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
    
    private static Iterator<Activitat> inicialitzarCandidats(){
        return null;
    }
    
    private static boolean Acceptable(Activitat a){
        return a.Acceptable(solucio_optima); //podem necessitar més parametres en futur, amés sembla que la funció no es necessaria, son pres i post de regal
    }
    
    private static boolean EsPotTrobarMillor(Activitat a){
        return false;
    }
    
    private static void AnotarCandidat(Activitat a){
        solucio_actual.afegirActivitat(a);
    }
    
    private static void DesanotarCandidat(){
        solucio_actual.treureUltimaActivitat();
    }
    
    private static boolean SolucioCompleta(){
        return false;
    }
    
    private static boolean MillorQueOptima(){
        boolean empatPreu = solucio_optima.preu_persona()==solucio_actual.preu_persona(), empatSatisfaccio = solucio_optima.grau_satisfacio()==solucio_actual.grau_satisfacio();
        if (solucio_optima.preu_persona()>solucio_actual.preu_persona()) return true;
        else if (empatPreu && solucio_optima.grau_satisfacio()<solucio_actual.grau_satisfacio()) return true;
        else return empatPreu && empatSatisfaccio && solucio_optima.dies_total()>solucio_actual.dies_total();
    }

}
