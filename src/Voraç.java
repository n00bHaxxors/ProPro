import java.time.LocalTime;
import java.util.*;

/** @class Voraç
 @brief Conté l'algoritme que busca una ruta relativament bona
 @author Marc Cané Salamià
 */

public abstract class Voraç {

    /** @brief Busca l'activitat que maximitza la qualitat (?) del circuit
     @pre Circuit, viatge, itr i visitats no nuls, tipus=b/c/s
     @post Retorna l'activitat més prometedora*/
    private static Activitat Buscar_Prometedor(Mapa m,Circuit c, Viatge v, Iterator<Activitat> itr_cand, Set<Visitable> oblig, char tipus) {
        Activitat iCan, millor = null;
        int var_millor=0, var_trans[]=new int[1];
        LocalTime temps_millor=null, temps_trans[]=new LocalTime[1];
        boolean millor_ob=false;

        while (itr_cand.hasNext()) {
            iCan = itr_cand.next();

            if (ModulCalculs.Acceptable(iCan, v, c, oblig) && !c.visitat(iCan.UbicacioActual())) {
                boolean actual_ob, comp; //booleans finals
                boolean actual_capaobligatori = false; //booleans intermitjos

                if (!m.conteAllotjament(iCan.nomAct()) && !m.conteVisitable(iCan.nomAct())) //si és un desplaçament...
                    actual_capaobligatori = oblig.contains(m.puntInteres(iCan.nomAct())); //si el punt d'interès (suposem) a on va el T és obligatori(i no hi hem passat)...

                actual_ob = oblig.contains(iCan) || actual_capaobligatori; //es obligatori o VA cap a un obligatori
                comp = iCan.comparar(millor, v.clients(), m, var_trans, temps_trans, var_millor, temps_millor, tipus);

                if (!millor_ob && comp || actual_ob && (!millor_ob || comp) || m.conteVisitable(iCan.nomAct()) && !m.conteVisitable(millor.nomAct())) {
                    millor = iCan;
                    millor_ob = actual_ob;
                    var_millor = var_trans[0];
                    temps_millor = temps_trans[0];
                }
            }
        }

        return millor;
    }

    /** @brief Algoritme voraç que busca una ruta relativament bona
     @pre mapa i viatge no nuls, tipus=b/c/s
     @post Retorna un circuit amb la ruta trobada optimitzant en funció del tipus_voraç*/
    private static Circuit Alg_Voraç(Mapa mapa, Viatge viatge, char tipus_voraç){
        Circuit circuit = new Circuit(viatge.dataHoraInici());
        Iterator<Activitat> itr_candidats;
        Activitat iCan;

        Set<Visitable> obligatoris = new HashSet();
        Iterator<Visitable> itr_visitables = viatge.iteradorVisitables();
        while(itr_visitables.hasNext()){
            Visitable aux = itr_visitables.next();
            obligatoris.add(aux);
        }

        do{
            itr_candidats=ModulCalculs.inicialitzarCandidats(circuit.ultimaActivitat(), mapa,circuit,viatge);
            iCan=Buscar_Prometedor(mapa,circuit,viatge,itr_candidats,obligatoris,tipus_voraç);
            if(iCan!=null){
                circuit.afegirActivitat(iCan,mapa,viatge);
                if(obligatoris.contains(mapa.puntInteres(iCan.nomAct())))
                    obligatoris.remove(mapa.puntInteres(iCan.nomAct()));
            }
        }while(!circuit.solucioCompleta(obligatoris,viatge.origen(),viatge.desti(),viatge.nombreDies(),mapa) && iCan!=null);

        return circuit; //si el circuit no és complet hauriem de retornar null?
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

        System.out.println("Voraç completat");
        return res;
    }
}
