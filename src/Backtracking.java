
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        if (v.RutaBarata()){
            AlgBT(g,c,v,'c');
            resultat.put("ruta curta", solucio_optima);
            solucio_optima = new Circuit(v.dataHoraInici()); 
            solucio_actual = new Circuit(v.dataHoraInici());
        }
        
        if (v.RutaSatisfactoria()){
            AlgBT(g,c,v,'c');
            resultat.put("ruta satisfactoria", solucio_optima);
            solucio_optima = new Circuit(v.dataHoraInici()); 
            solucio_actual = new Circuit(v.dataHoraInici());
        }
        return resultat;
    }
    
    /** @brief Algoritme Backtracking (per preu[temp])
     @pre parametres no buits i a, b i els PuntInteres de c existents a g 
     @post solucio_optima passa amb el circuit demanat*/
    private static void AlgBT(Mapa g, Set<Visitable> c, Viatge v, char o){
        Iterator<Activitat> itr = inicialitzarCandidats(solucio_actual.ultimaActivitat(), g, v.origen());
        while (itr.hasNext()){
            Activitat act = itr.next();
            if(Acceptable(act,v) && EsPotMillorar(act, o, v.clients())){
                AnotarCandidat(act, v.clients(), g);
                if (!SolucioCompleta(c,v.origen(),v.desti(),v.nombreDies())) AlgBT(g,c,v,o);
                else{
                    if (MillorQueOptima(o)) solucio_optima = solucio_actual;
                }
                DesanotarCandidat(v.clients(), g);
            }
        }
    }
    
    /** @brief Inicialitza els candidats possibles en funció de la activitat anterior
     @pre a != null
     @post retorna un iterador a un conjunt amb els candidats possibles*/
    private static Iterator<Activitat> inicialitzarCandidats(Activitat a, Mapa g, Visitable inici){
        TreeSet<Activitat> arbre = new TreeSet();
        PuntInteres pActual;
        if (a!=null) pActual = a.UbicacioActual();
        else pActual = inici;
        Lloc llocActual = g.lloc(pActual.nomLloc());
        LocalDateTime ara = solucio_actual.acabamentCircuit();
        Activitat actPActual = null;
        if (pActual.obreAvui(ara) && !pActual.esLlocPas()) actPActual = pActual.ActivitatCorresponent(pActual.ProximaObertura(ara));
        if(actPActual != null &&actPActual.Satisfaccio(g.clients())> 0) arbre.add(actPActual); 
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
                LocalTime duradaTotal = mti.durada().plusHours(h.tempsTrasllatTotal().getHour()).plusMinutes(h.tempsTrasllatTotal().getMinute());
                Iterator<PuntInteres> itr4 = l.puntsInteres();
                while (itr4.hasNext()){
                    PuntInteres pi = itr4.next();
                    Desplaçament aux = new Desplaçament(mti.preu(),mti.diaHoraSortida().toLocalDate(),mti.diaHoraSortida().toLocalTime(),
                    mti, pActual, pi, duradaTotal);
                    arbre.add(aux);
                }
            }
        }
        return arbre.iterator();
    }
    
    /** @brief consulta si una activitat es acceptable
     @pre a != null
     @post retorna cert si la activitat compleix amb les condicions corresponents i fals en c.c.*/
    private static boolean Acceptable(Activitat a, Viatge v){
        LocalDateTime fi = solucio_actual.acabamentCircuit().toLocalDate().atTime(a.horaActivitat()).plusHours(a.Duracio().getHour()).plusMinutes(a.Duracio().getMinute());
        long dies =ChronoUnit.DAYS.between(fi, solucio_actual.iniciCircuit());
        LocalTime iniciHoraDinar = (LocalTime.of(12, 0)), fiHoraDinar = (LocalTime.of(14, 0));
        boolean esHoraDinar = !a.horaActivitat().isBefore(iniciHoraDinar) && 
                !a.horaActivitat().plusHours(a.Duracio().getHour()).plusMinutes(a.Duracio().getMinute()).isAfter(fiHoraDinar);
        boolean resultatParcial = (solucio_actual.preu_persona() + a.preuAct()) < v.preuMaxim() && dies <= v.nombreDies() && !esHoraDinar;
        return resultatParcial && a.Acceptable(solucio_actual,v); 
    }
    
    /** @brief consulta si el circuit actual encara podrà millorar el circuit_optim afegint la activitat a
     @pre a != null
     @post retorna cert si amb l'activitat encara es podrà millorar i fals en c.c.*/
    private static boolean EsPotMillorar(Activitat a, char o, GrupClients g){
        boolean resultat = false;
         switch (o){
            case 'b' : //barata
                resultat = a.preuAct()+solucio_actual.preu_persona() < solucio_optima.preu_persona();
            case 'c' : //curta
                resultat = a.horaActivitat().plusHours(a.Duracio().getHour()).plusMinutes(a.Duracio().getMinute()).isBefore(solucio_optima.acabamentCircuit().toLocalTime());
            case 's' : //satisfactoria
                resultat = a.Satisfaccio(g) + solucio_actual.grau_satisfaccio() < solucio_optima.grau_satisfaccio();
                
        }
        return resultat;
    }
    
    /** @brief Afegeix l'activitat a solucio_actual
     @pre a i g != null
     @post solució actual actualitzada amb la nova activitat*/
    private static void AnotarCandidat(Activitat a, GrupClients g, Mapa m){
        solucio_actual.afegirActivitat(a, g, m);
    }
    
    /** @brief treu l'última activitat 
     @pre g != null
     @post solució actual actualitzada treient l'úlitma activitat */
    private static void DesanotarCandidat(GrupClients g, Mapa m){
        solucio_actual.treureUltimaActivitat(g, m);
    }
    
    /** @brief Consulta si solucio actual es solucioCompleta
     @pre cert
     @post retorna cert si la solucio actual es completa i fals en cas contrari*/
    private static boolean SolucioCompleta(Set<Visitable> c, PuntInteres origen, PuntInteres desti, int dies){
        return solucio_actual.solucioCompleta(c,origen,desti, dies);
    }
    
    /** @brief consulta si la solucio actual es millor que la optima
     @pre solucio actual es completa
     @post retorna cert si la solucio actual es millor que la optima i fals en c.c.*/
    private static boolean MillorQueOptima(char o){
        boolean empatPreu, empatSatisfaccio, empatDies, resultat = false;
        empatPreu = solucio_optima.preu_persona()==solucio_actual.preu_persona(); 
        empatSatisfaccio = solucio_optima.grau_satisfaccio()==solucio_actual.grau_satisfaccio();
        empatDies = solucio_optima.dies_total()==solucio_actual.dies_total();
        switch (o){
            case 'b' : //barata
                if (solucio_optima.preu_persona()>solucio_actual.preu_persona()) resultat = true;
                else if (empatPreu && solucio_optima.grau_satisfaccio()<solucio_actual.grau_satisfaccio()) resultat = true;
                else resultat = empatPreu && empatSatisfaccio && solucio_optima.dies_total()>solucio_actual.dies_total();
            case 'c' : //curta
                if (solucio_optima.dies_total()>solucio_actual.dies_total()) resultat = true;
                else if (empatDies && solucio_optima.grau_satisfaccio()<solucio_actual.grau_satisfaccio()) resultat = true;
                else resultat = empatDies && empatSatisfaccio && solucio_optima.preu_persona() > solucio_actual.preu_persona();
            case 's' : //satisfactoria
                if (solucio_optima.grau_satisfaccio()>solucio_actual.grau_satisfaccio()) resultat = true;
                else if (empatSatisfaccio && solucio_optima.grau_satisfaccio()<solucio_actual.grau_satisfaccio()) resultat = true;
                else resultat = empatSatisfaccio && empatPreu && solucio_optima.dies_total()>solucio_actual.dies_total();
        }
        return resultat;
    }

}
