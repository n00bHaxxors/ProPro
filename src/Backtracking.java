
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/** @file Backtracking.java
    @brief Backtracking
*/

/** @class Backtracking
    @brief Modul funcional per calcular els camins del Backtracking
    @author Ismael El Habri
*/

public abstract class Backtracking {
    private static Circuit solucio_optima, solucio_actual;
    HashMap<String, Visita> visitesFetes;
    
    
    public static Circuit CircuitMesBarata(Mapa g, PuntInteres a, PuntInteres b, Set<PuntInteres> c, LocalDateTime diaInici){
        solucio_optima = new Circuit(diaInici); solucio_actual = new Circuit(diaInici);
        AlgBTPreu(g,a,b,c);
        return solucio_optima;
    }
    
    
    private static void AlgBTPreu(Mapa g, PuntInteres a, PuntInteres b, Set<PuntInteres> c){
        Iterator<Activitat> itr = inicialitzarCandidats(solucio_actual.ultimaActivitat());
        while (itr.hasNext()){
            Activitat act = itr.next();
            if(Acceptable(act) && EsPotTrobarMillor(act)){
                AnotarCandidat(act, g);
                if (!SolucioCompleta()) AlgBTPreu(g,a,b,c);
                else{
                    if (MillorQueOptima()) solucio_optima = solucio_actual;
                }
                DesanotarCandidat(g);
            }
        }
    }
    
    private static Iterator<Activitat> inicialitzarCandidats(Activitat a){
        TreeSet<Activitat> arbre = new TreeSet();
        PuntInteres pActual = a.UbicacioActual();
        String llocActual = pActual.nomLloc();
        arbre.add(pActual.ActivitatCorresponent(solucio_actual.acabamentCircuit())); //opcio de fer l'activitat on estem ara
        //activitats x desplaçament directe desde el PI actual;
        //...
        //Activitats x desplaçament indirecte desde el lloc actual
        //...
        return null;
    }
    
    private static boolean Acceptable(Activitat a){
        return a.Acceptable(solucio_optima); //podem necessitar més parametres en futur, amés sembla que la funció no es necessaria, son pres i post de regal
    }
    
    private static boolean EsPotTrobarMillor(Activitat a){
        return false;
    }
    
    private static void AnotarCandidat(Activitat a, Mapa g){
        solucio_actual.afegirActivitat(a, g.clients());
    }
    
    private static void DesanotarCandidat(Mapa g){
        solucio_actual.treureUltimaActivitat(g.clients());
    }
    
    private static boolean SolucioCompleta(){
        return false;
    }
    
    private static boolean MillorQueOptima(){
        boolean empatPreu = solucio_optima.preu_persona()==solucio_actual.preu_persona(), empatSatisfaccio = solucio_optima.grau_satisfaccio()==solucio_actual.grau_satisfaccio();
        if (solucio_optima.preu_persona()>solucio_actual.preu_persona()) return true;
        else if (empatPreu && solucio_optima.grau_satisfaccio()<solucio_actual.grau_satisfaccio()) return true;
        else return empatPreu && empatSatisfaccio && solucio_optima.dies_total()>solucio_actual.dies_total();
    }

}
