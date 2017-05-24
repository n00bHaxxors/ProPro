/** @file ModulCalculs.java
    @brief ModulCalculs
*/

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.TreeSet;

/** @class ModulCalculs
 @brief Modul funcional amb calculs comuns tant en el Algoritme de Backtracking com el Greedy
 @author Ismael El Habri
 */

public abstract class ModulCalculs {
    /** @brief Inicialitza els candidats possibles en funció de la activitat anterior
     @pre a != null
     @post retorna un iterador a un conjunt amb els candidats possibles*/
    public static Iterator<Activitat> inicialitzarCandidats(Activitat a, Mapa g, Circuit solucio_actual, Viatge v){
        ArrayList<Activitat> arbre = new ArrayList();
        PuntInteres pActual = null;
        if (a != null && (g.conteVisitable(a.UbicacioActual()) || g.conteAllotjament(a.UbicacioActual())) ) pActual = g.puntInteres(a.UbicacioActual());
        else if (a != null && !g.conteVisitable(a.UbicacioActual()) && !g.conteAllotjament(a.UbicacioActual())) return arbre.iterator(); //hem acabat en un lloc, i no en un PI en concret
        else if (a == null && g.conteVisitable(v.origen().nom())) {
            pActual = (PuntInteres) v.origen();
            Activitat aux = pActual.ActivitatCorresponent(pActual.ProximaObertura(solucio_actual.acabamentCircuit()));
            arbre.add(aux);
            return arbre.iterator();
        }
        Activitat actPActual = null, actPActual2 = null;
        Lloc llocActual;
        LocalDateTime ara = solucio_actual.acabamentCircuit();
        if (pActual != null) {
            llocActual = g.lloc(pActual.nomLloc());
            if (pActual.obreAvui(ara) && !pActual.esLlocPas()) {
                LocalDateTime proxObert = pActual.ProximaObertura(ara), horaFiDinar = proxObert.toLocalDate().atTime(14, 0);
                actPActual = pActual.ActivitatCorresponent(proxObert);
                actPActual2 = pActual.ActivitatCorresponent(horaFiDinar);
            }
            if (actPActual != null /*&& actPActual.Satisfaccio(v.clients()) > 0*/) {
                arbre.add(actPActual);
                arbre.add(actPActual2);
            }
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
                    boolean condicioMTD = !pi.nom().equals(pActual.nom());
                    if (!pi.nom().equals(pActual.nom())) {
                        Activitat aux = mtd.desplaçament(ara.toLocalDate(), ara.toLocalTime(), pActual, pi);
                        String temp = aux.toString();
                        arbre.add(aux);
                    }
                }
            }
        }
        else llocActual = (Lloc) v.origen();
        //Activitats x desplaçament indirecte desde el lloc actual
        Iterator<Hub> itr2 = llocActual.hubs();
        while (itr2.hasNext()){
            Hub h = itr2.next();
            Lloc l = h.destinacio();
            Iterator<MT_Indirecte> itr3 = h.transports();
            while(itr3.hasNext()){
                MT_Indirecte mti = itr3.next();
                LocalTime duradaTotal;
                if (pActual!=null) duradaTotal = mti.durada().plusHours(h.tempsTrasllatTotal().getHour()).plusMinutes(h.tempsTrasllatTotal().getMinute());
                else duradaTotal = mti.durada().plusHours(h.tempsTrasllatDesti().getHour()).plusMinutes(h.tempsTrasllatDesti().getMinute());
                if (l.nom().equals(v.desti().nom())){
                    LocalTime duradaTotal2 = mti.durada().plusHours(h.tempsTrasllatOrigen().getHour()).plusMinutes(h.tempsTrasllatOrigen().getMinute());
                    Desplaçament temp = new Desplaçament(mti.preu(),mti.diaHoraSortida().toLocalDate(),mti.diaHoraSortida().toLocalTime(),
                            mti, pActual, l, duradaTotal2);
                    arbre.add(temp);
                }
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
     @pre a != null && v !=null
     @post retorna cert si la activitat compleix amb les condicions corresponents i fals en c.c.*/
    public static boolean Acceptable(Activitat a, Viatge v, Circuit solucio_actual){
        LocalDateTime fi = solucio_actual.acabamentCircuit().toLocalDate().atTime(a.horaActivitat()).plusHours(a.Duracio().getHour()).plusMinutes(a.Duracio().getMinute());
        long dies = ChronoUnit.DAYS.between(fi, solucio_actual.iniciCircuit());
        LocalTime iniciHoraDinar = (LocalTime.of(12, 0)), fiHoraDinar = (LocalTime.of(14, 0));
        boolean esHoraDinar = !a.horaActivitat().isBefore(iniciHoraDinar) &&
                !a.horaActivitat().plusHours(a.Duracio().getHour()).plusMinutes(a.Duracio().getMinute()).isAfter(fiHoraDinar);
        boolean resultatParcial = (solucio_actual.preu_persona() + a.preuAct()) <= v.preuMaxim() && dies <= v.nombreDies() && !esHoraDinar;
        return resultatParcial && a.Acceptable(solucio_actual,v);
    }
}
