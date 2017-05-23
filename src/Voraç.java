import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.*;

/** @class Voraç
 @brief Conté l'algoritme que busca una ruta relativament bona
 @author Marc Cané Salamià
 */

public abstract class Voraç {

    /*private Boolean completa(PuntInteres desti, Set<PuntInteres> a_visitar, ArrayList<Activitat> activitats){
        Boolean c1, c2;
        c1=activitats.get(activitats.size() - 1).nomAct().equals(desti.nom()); //!!!!! nom? nomlloc? no hi ha cap altre manera millor que comparar lstring?
        c2=activitats.containsAll(a_visitar); //Activitats vs PI //potser ho podem fer contant els punts a visitar que ja hem visitat aixi estalviem fer un O(n)
        return c1 && c2;
    }

    /** @brief Fa la comparació corresponent en funcio del tipus de variable a optimitzar
     @pre actual i millor no nuls i tipus=bcs
     @post Retorna cert si l'activitat actual és millor que la millor activitat trobada fins el moment*/
    /*private static Boolean comparar(Activitat actual, Activitat millor, GrupClients gc, char tipus){
        Boolean res=true;
        if(millor!=null){
            switch(tipus){
                case 'b':
                    res=actual.preuAct()<millor.preuAct();
                    break;
                case 's':
                    res=actual.Satisfaccio(gc)>millor.Satisfaccio(gc);
                    break;
                case 'c':
                    res=actual.Duracio().isBefore(millor.Duracio());
                    break;
            }
        }
        return res;
    }*/

    /** @brief Busca l'activitat que maximitza la qualitat (?) del circuit
     @pre Circuit, viatge, itr i visitats no nuls, tipus=b/c/p
     @post Retorna l'activitat més prometedora*/
    private static Activitat Buscar_Prometedor(Circuit circuit, Viatge viatge, Iterator<Activitat> itr_candidats, TreeMap<Activitat, Boolean> visitats, char tipus) {
        Activitat iCan, millor = null;

        while (itr_candidats.hasNext()) {
            iCan=itr_candidats.next();
            //SI activitat és un lloc per on hem de passar obligatoriament i encara no hi hem passat -> PASSARHI (retornar aquesta activitat ja) (?)
            //iCan.UbicacioActual()
            if (visitats.get(iCan)!=null && ModulCalculs.Acceptable(iCan,viatge,circuit)) { //falten condicions
                if (iCan.comparar(millor,viatge.clients(),tipus))
                    millor = iCan;
            }
        }

        return millor;
    }

    /** @brief Algoritme voraç que busca una ruta relativament bona
     @pre mapa i viatge no nuls, tipus=b/c/s
     @post Retorna un circuit amb la ruta trobada optimitzant en funció del tipus_voraç*/
    private static Circuit Alg_Voraç(Mapa mapa, Viatge viatge, char tipus_voraç){
        TreeMap<Activitat,Boolean> visitats=new TreeMap<>();
        Activitat iCan = new Visita(); //activitat stub per entrar al while
        Circuit circuit = new Circuit(viatge.dataHoraInici());
        Iterator<Activitat> itr_candidats;

        TreeSet<Visitable> obligatoris = new TreeSet();
        Iterator<Visitable> itr_visitables = viatge.iteradorVisitables();
        while(itr_visitables.hasNext()){
            Visitable aux = itr_visitables.next();
            obligatoris.add(aux);
        }

        while(!circuit.solucioCompleta(obligatoris,viatge.origen(),viatge.desti(),viatge.nombreDies(),mapa) && iCan!=null){
            itr_candidats=ModulCalculs.inicialitzarCandidats(circuit.ultimaActivitat(), mapa, viatge.origen(), viatge.desti(),circuit);
            iCan=Buscar_Prometedor(circuit,viatge,itr_candidats,visitats,tipus_voraç);
            if(iCan!=null){ //???
                circuit.afegirActivitat(iCan, viatge.clients(), mapa);
            }
        }

        return circuit;
    }

    /** @brief Calcula les rutes que requereix el viatge
     @pre m i v no nuls
     @post Retorna un HashMap amb les rutes demanades en el viatge v*/
    public static HashMap<String,Circuit> Circuit_Voraç(Mapa m, Viatge v){
        final char tipus_voraç[]={'b','c','s'};
        final String tipus_rutes[]={"barata","curta","satisfactoria"};
        final Boolean rutes[]={v.RutaBarata(),v.RutaCurta(),v.RutaSatisfactoria()};
        HashMap<String,Circuit> res = new HashMap();

        for(int i=0;i<3;i++)
            if (rutes[i]) 
                res.put("ruta "+tipus_rutes[i], Alg_Voraç(m, v, tipus_voraç[i]));

        return res;
    }
}
