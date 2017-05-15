
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    
    /** @brief Calcula el circuit més Barat
     @pre parametres no buits i a, b i els PuntInteres de c existents a g 
     @post Retorna el Circuit més barat*/
    public static Circuit CircuitMesBarata(Mapa g, PuntInteres a, PuntInteres b, Set<PuntInteres> c, LocalDateTime diaInici){
        solucio_optima = new Circuit(diaInici); 
        solucio_actual = new Circuit(diaInici);
        AlgBTPreu(g,a,b,c);
        return solucio_optima;
    }
    
    /** @brief Algoritme Backtracking (per preu[temp])
     @pre parametres no buits i a, b i els PuntInteres de c existents a g 
     @post solucio_optima passa amb el circuit demanat*/
    private static void AlgBTPreu(Mapa g, PuntInteres a, PuntInteres b, Set<PuntInteres> c){
        Iterator<Activitat> itr = inicialitzarCandidats(solucio_actual.ultimaActivitat(), g);
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
    
    /** @brief Inicialitza els candidats possibles en funció de la activitat anterior
     @pre a != null
     @post retorna un iterador a un conjunt amb els candidats possibles*/
    private static Iterator<Activitat> inicialitzarCandidats(Activitat a, Mapa g){
        TreeSet<Activitat> arbre = new TreeSet();
        PuntInteres pActual = a.UbicacioActual();
        Lloc llocActual = g.lloc(pActual.nomLloc());
        LocalDateTime ara = solucio_actual.acabamentCircuit();
        Activitat actPActual = pActual.ActivitatCorresponent(ara);
        if (pActual.obreAvui(ara) && actPActual.Satisfaccio(g.clients())> 0) arbre.add(actPActual); 
        //activitats x desplaçament directe desde el PI actual;
        Iterator<MT_Directe> itr1 = pActual.TransportsDirectes();
        while (itr1.hasNext()){
            MT_Directe mtd = itr1.next();
            Activitat aux = mtd.desplaçament(ara.toLocalDate(), ara.toLocalTime(), pActual);
            arbre.add(aux);
        }
        //Transports directes amb el transport default del lloc
        itr1 = llocActual.mitjansDirectes();
        while (itr1.hasNext()){
            MT_Directe mtd = itr1.next();
            Iterator<PuntInteres> itr2 = llocActual.puntsInteres();
            while(itr2.hasNext()){
                PuntInteres pi = itr2.next();
                Activitat aux = mtd.desplaçament(ara.toLocalDate(), ara.toLocalTime(), pActual, pi);
                arbre.add(aux);
            }
        }
        //Activitats x desplaçament indirecte desde el lloc actual
        Iterator<Hub> itr2 = llocActual.hubs();
        while (itr2.hasNext()){
            Hub h = itr2.next();
            Lloc l = h.destinacio();
            Iterator<MT_Indirecte> itr3 = h.transports();
             while(itr3.hasNext()){
                MT_Indirecte mti = itr3.next();
                Iterator<PuntInteres> itr4 = l.puntsInteres();
                while (itr4.hasNext()){
                    PuntInteres pi = itr4.next();
                    Desplaçament aux = new Desplaçament(mti.preu(),mti.diaHoraSortida().toLocalDate(),mti.diaHoraSortida().toLocalTime(),
                    mti, pActual, pi);
                    arbre.add(aux);
                }
            }
        }
        return arbre.iterator();
    }
    
    /** @brief consulta si una activitat es acceptable
     @pre a != null
     @post retorna cert si la activitat compleix amb les condicions corresponents i fals en c.c.*/
    private static boolean Acceptable(Activitat a){
        return a.Acceptable(solucio_optima); //podem necessitar més parametres en futur, amés sembla que la funció no es necessaria, son pres i post de regal
    }
    
    /** @brief consulta si el circuit actual encara podrà millorar el circuit_optim afegint la activitat a
     @pre a != null
     @post retorna cert si amb l'activitat encara es podrà millorar i fals en c.c.*/
    private static boolean EsPotTrobarMillor(Activitat a){
        return false;
    }
    
    /** @brief Afegeix l'activitat a solucio_actual
     @pre a i g != null
     @post solució actual actualitzada amb la nova activitat*/
    private static void AnotarCandidat(Activitat a, Mapa g){
        solucio_actual.afegirActivitat(a, g.clients());
    }
    
    /** @brief treu l'última activitat 
     @pre g != null
     @post solució actual actualitzada treient l'úlitma activitat */
    private static void DesanotarCandidat(Mapa g){
        solucio_actual.treureUltimaActivitat(g.clients());
    }
    
    /** @brief Consulta si solucio actual es solucioCompleta
     @pre cert
     @post retorna cert si la solucio actual es completa i fals en cas contrari*/
    private static boolean SolucioCompleta(){
        return false;
    }
    
    /** @brief consulta si la solucio actual es millor que la optima
     @pre solucio actual es completa
     @post retorna cert si la solucio actual es millor que la optima i fals en c.c.*/
    private static boolean MillorQueOptima(){
        boolean empatPreu = solucio_optima.preu_persona()==solucio_actual.preu_persona(), empatSatisfaccio = solucio_optima.grau_satisfaccio()==solucio_actual.grau_satisfaccio();
        if (solucio_optima.preu_persona()>solucio_actual.preu_persona()) return true;
        else if (empatPreu && solucio_optima.grau_satisfaccio()<solucio_actual.grau_satisfaccio()) return true;
        else return empatPreu && empatSatisfaccio && solucio_optima.dies_total()>solucio_actual.dies_total();
    }

}
