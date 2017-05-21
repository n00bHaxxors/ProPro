import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.*;

/**
 * Created by Markus on 10/05/2017.
 */

public abstract class Voraç {

    //Post: retorna cert si queden candidats no visitats
    /*private Boolean Queden_candidats(){
        int i;
        Boolean trobat=false;
        while(!trobat){
            //if(candidat_valid(candidat[i])
                //trobat=true;
            
            i++;
        }
        return trobat;
    }*/

    //retorna cert si tenim suficients diners, tenim suficient temps per visitar, està obert,
    /*private Boolean candidat_valid(Activitat a, TreeMap<Activitat,Boolean> visitats, int diners_gastats, int preu_maxim, LocalTime durada, LocalTime durada_max){
        return visitats.(a)!=null && diners_gastats+a.preu()<preu_maxim && durada.plus(a.Duracio()).isBefore(durada_max); //no cal comprovar si esta visitat perquè ja ho haurem fet al buscar_prometedor...
    }*/

    private Boolean completa(PuntInteres desti, Set<PuntInteres> a_visitar, ArrayList<Activitat> activitats){
        Boolean c1, c2;
        c1=activitats.get(activitats.size() - 1).nomAct().equals(desti.nom()); //!!!!! nom? nomlloc? no hi ha cap altre manera millor que comparar lstring?
        c2=activitats.containsAll(a_visitar); //Activitats vs PI //potser ho podem fer contant els punts a visitar que ja hem visitat aixi estalviem fer un O(n)
        return c1 && c2;
    }

    /** @brief Fa la comparació corresponent en funcio del tipus de variable a optimitzar
     @pre actual i millor != null i tipus=p,s,d
     @post Retorna cert si l'activitat actual és millor que la millor activitat trobada fins el moment*/
    private Boolean comparar(Activitat actual, Activitat millor, GrupClients gc, char tipus){
        Boolean res=true;
        if(millor!=null){
            switch(tipus){
                case 'p':
                    res=actual.preuAct()<millor.preuAct();
                    break;
                case 's':
                    res=actual.Satisfaccio(gc)>millor.Satisfaccio(gc);
                    break;
                case 'd':
                    res=actual.Duracio().isBefore(millor.Duracio());
                    break;
            }
        }
        return res;
    }

    private Activitat Buscar_Prometedor(Circuit resultat, Viatge viatge, Iterator<Activitat> itr_candidats, TreeMap<Activitat,Boolean> visitats, char tipus) {
        Activitat iCan, millor = null;

        while (itr_candidats.hasNext()) {
            iCan=itr_candidats.next();
            //SI activitat és un lloc per on hem de passar obligatoriament PASSARHI (retornar aquesta activitat ja) (?)
            if (visitats.get(iCan)!=null && ModulCalculs.Acceptable(iCan,viatge,resultat)) { //falten condicions
                if (comparar(iCan,millor,viatge.clients(),tipus))
                    millor = iCan;
            }
        }

        return millor;
    }

    public Circuit Alg_Voraç(Mapa mapa, Viatge viatge, char tipus_voraç){
        int diners_gastats = 0, grau_satisfaccio = 0;
        //PuntInteres origen, PuntInteres desti, Set<PuntInteres> a_visitar,
        TreeMap<Activitat,Boolean> visitats=new TreeMap<Activitat,Boolean>();
        LocalTime durada = null;
        Activitat iCan= new Visita(); //activitat stub per complir la condicio del while
        Circuit resultat=new Circuit(viatge.dataHoraInici());
        Iterator<Activitat> itr_candidats;

        TreeSet<Visitable> obligatoris = new TreeSet();
        Iterator<Visitable> itr_visitables = viatge.iteradorVisitables();
        while(itr_visitables.hasNext()){
            Visitable aux = itr_visitables.next();
            obligatoris.add(aux);
        }

        while(!resultat.solucioCompleta(obligatoris,viatge.origen(),viatge.desti(),viatge.nombreDies(),mapa) && iCan!=null){ //&& tenimdiners() && tenimtemps()
            //ModulCalculs.Acceptable(iCan,)
            //ModulCalculs.inicialitzarCandidats(solucio_actual.ultimaActivitat(), g, v.origen(), v.desti(),solucio_actual);
            itr_candidats=ModulCalculs.inicialitzarCandidats(resultat.ultimaActivitat(), mapa, viatge.origen(), viatge.desti(),resultat);
            iCan=Buscar_Prometedor(resultat,viatge,itr_candidats,visitats,tipus_voraç);
            if(iCan!=null){ //???
                diners_gastats+=iCan.preuAct();
                grau_satisfaccio+=iCan.Satisfaccio(viatge.clients()); //esta bé?
                //durada.plus(iCan.Duracio()); //WIP
                visitats.put(iCan,true);
                resultat.afegirActivitat(iCan, viatge.clients(), mapa);
            }
        }
        //if(completa())resultat=new Circuit(diners_gastats,grau_satisfaccio,durada,activitats);

        return resultat;
    }

    public HashMap<String,Circuit> Circuit_Voraç(Mapa mapa, Viatge viatge){
        char tipus_voraç=viatge.RutaBarata()?'b':(viatge.RutaCurta()?'c':'s');
        String rutes[]={"barata","curta","satisfactoria"}, tipus=null;
        for(String s: rutes){if(s.charAt(0)==tipus_voraç){tipus=s;break;}}
        HashMap<String,Circuit> resultat = new HashMap();
        Circuit circuit=Alg_Voraç(mapa,viatge,tipus_voraç);
        resultat.put("ruta "+tipus,circuit);
        return resultat;
    }
}
