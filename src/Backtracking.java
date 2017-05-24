
/** @file Backtracking.java
    @brief Backtracking
*/
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/** @class Backtracking
    @brief Modul funcional per calcular els camins del Backtracking
    @author Ismael El Habri
*/

public abstract class Backtracking {
    private static Circuit solucio_optima, solucio_actual;
    
    /** @brief Calcula el circuit més Barat
     @pre parametres no buits i a, b i els PuntInteres de c existents a g 
     @post Retorna el Circuit més barat*/
    public static HashMap<String,Circuit> CircuitExacte(Mapa g, Viatge v){
        solucio_optima = new Circuit(v.dataHoraInici()); 
        solucio_actual = new Circuit(v.dataHoraInici());
        TreeSet<Visitable> c = new TreeSet();
        Iterator<Visitable> itr = v.iteradorVisitables();
        while(itr.hasNext()){
            Visitable aux = itr.next();
            c.add(aux);
        }
        HashMap<String,Circuit> resultat = new HashMap();
        if (v.RutaBarata()){
            AlgBT(g,c,v,'b');
            resultat.put("ruta barata", solucio_optima);
            solucio_optima = new Circuit(v.dataHoraInici()); 
            solucio_actual = new Circuit(v.dataHoraInici());
        }
        if (v.RutaCurta()){
            AlgBT(g,c,v,'c');
            resultat.put("ruta curta", solucio_optima);
            solucio_optima = new Circuit(v.dataHoraInici()); 
            solucio_actual = new Circuit(v.dataHoraInici());
        }
        if (v.RutaSatisfactoria()){
            AlgBT(g,c,v,'s');
            resultat.put("ruta satisfactoria", solucio_optima);
        }
        return resultat;
    }
    
    /** @brief Algoritme Backtracking (per preu[temp])
     @pre parametres no buits i a, b i els PuntInteres de c existents a g 
     @post solucio_optima passa amb el circuit demanat*/
    private static void AlgBT(Mapa g, Set<Visitable> c, Viatge v, char o){
        Iterator<Activitat> itr = ModulCalculs.inicialitzarCandidats(solucio_actual.ultimaActivitat(), g, solucio_actual, v);
        while (itr.hasNext()){
            Activitat act = itr.next();
            boolean acce = ModulCalculs.Acceptable(act,v,solucio_actual), millorable = EsPotMillorar(act, o, v.clients());
            if(ModulCalculs.Acceptable(act,v,solucio_actual) && EsPotMillorar(act, o, v.clients())){
                AnotarCandidat(act,g,v);
                if (!SolucioCompleta(c,v.origen(),v.desti(),v.nombreDies(),g)) AlgBT(g,c,v,o);
                else if (MillorQueOptima(o)) {
                    solucio_optima = new Circuit (solucio_actual);
                } 
                DesanotarCandidat(g,v);
            }
        }
    }
    
    /** @brief consulta si el circuit actual encara podrà millorar el circuit_optim afegint la activitat a
     @pre a != null
     @post retorna cert si amb l'activitat encara es podrà millorar i fals en c.c.*/
    private static boolean EsPotMillorar(Activitat a, char o, GrupClients g){
        if (!solucio_optima.Activitats().hasNext()) return true;
        boolean resultat = false;
         switch (o){
            case 'b' : //barata
                resultat = a.preuAct()+solucio_actual.preu_persona() < solucio_optima.preu_persona();
                break;
            case 'c' : //curta
                resultat = a.horaActivitat().plusHours(a.Duracio().getHour()).plusMinutes(a.Duracio().getMinute()).isBefore(solucio_optima.acabamentCircuit().toLocalTime());
                break;
            case 's' : //satisfactoria
                resultat = true;
                //resultat = a.Satisfaccio(g) + solucio_actual.grau_satisfaccio() < solucio_optima.grau_satisfaccio();
                break;
                
        }
        return resultat;
    }
    
    /** @brief Afegeix l'activitat a solucio_actual
     @pre a i g != null
     @post solució actual actualitzada amb la nova activitat*/
    private static void AnotarCandidat(Activitat a, Mapa m, Viatge v){
        solucio_actual.afegirActivitat(a, m, v);
    }
    
    /** @brief treu l'última activitat 
     @pre g != null
     @post solució actual actualitzada treient l'úlitma activitat */
    private static void DesanotarCandidat(Mapa m, Viatge v){
        solucio_actual.treureUltimaActivitat(m, v);
    }
    
    /** @brief Consulta si solucio actual es solucioCompleta
     @pre cert
     @post retorna cert si la solucio actual es completa i fals en cas contrari*/
    private static boolean SolucioCompleta(Set<Visitable> c, Localitzacio origen, Localitzacio desti, int dies, Mapa g){
        return solucio_actual.solucioCompleta(c,origen,desti, dies, g);
    }
    
    /** @brief consulta si la solucio actual es millor que la optima
     @pre solucio actual es completa
     @post retorna cert si la solucio actual es millor que la optima i fals en c.c.*/
    private static boolean MillorQueOptima(char o){
        if (!solucio_optima.Activitats().hasNext()) return true;
        boolean empatPreu, empatSatisfaccio, empatDies, resultat = false;
        empatPreu = solucio_optima.preu_persona()==solucio_actual.preu_persona(); 
        empatSatisfaccio = solucio_optima.grau_satisfaccio()==solucio_actual.grau_satisfaccio();
        empatDies = solucio_optima.acabamentCircuit().equals(solucio_actual.acabamentCircuit());
        switch (o){
            case 'b' : //barata
                if (solucio_optima.preu_persona()>solucio_actual.preu_persona()) resultat = true;
                else if (empatPreu && solucio_optima.grau_satisfaccio()<solucio_actual.grau_satisfaccio()) resultat = true;
                else resultat = empatPreu && empatSatisfaccio && solucio_optima.acabamentCircuit().isAfter(solucio_actual.acabamentCircuit());
                break;
            case 'c' : //curta
                if (solucio_optima.acabamentCircuit().isAfter(solucio_actual.acabamentCircuit())) resultat = true;
                else if (empatDies && solucio_optima.grau_satisfaccio()<solucio_actual.grau_satisfaccio()) resultat = true;
                else resultat = empatDies && empatSatisfaccio && solucio_optima.preu_persona() > solucio_actual.preu_persona();
                break;
            case 's' : //satisfactoria
                if (solucio_optima.grau_satisfaccio()<solucio_actual.grau_satisfaccio()) resultat = true;
                else if (empatSatisfaccio && solucio_optima.preu_persona()>solucio_actual.preu_persona()) resultat = true;
                else resultat = empatSatisfaccio && empatPreu && solucio_optima.acabamentCircuit().isAfter(solucio_actual.acabamentCircuit());
                break;
        }
        return resultat;
    }

}
