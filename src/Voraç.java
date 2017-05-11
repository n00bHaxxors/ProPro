import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Markus on 10/05/2017.
 */

public abstract class Voraç {

    private boolean comparar(Activitat actual, Activitat millor, char tipus){
        boolean res;
        switch(tipus){
            case 'p':
                res=actual.preu()<millor.preu();
                break;
            case 's':
                res=actual.grau_satisfaccio()>millor.grau_satisfaccio();
                break;
            case 'd':
                res=actual.durada().isBefore(millor.durada());
                break;
        }
        return res;
    }

    private Activitat Buscar_Prometedor(ArrayList<Activitat> candidats, char tipus) {
        float millor_preu, millor_satisfaccio;
        LocalTime millor_durada;
        Activitat iCan, millor;

        while (!iCan.esFi()) {
            if (iCan) {
                if (comparar(iCan,millor,tipus)){
                    millor_preu=iCan.preu();
                    millor_satisfaccio=iCan.grau_satisfaccio();
                    millor_durada=iCan.durada(); //EZ maaaaaaaaaaaaaneeeeeeee
                    millor = iCan;
                }
            }
            iCan.seguent();
        }

        return millor;
    }

    //Post: retorna cert si queden candidats no visitats
    private boolean Queden_candidats(){
        int i;
        boolean trobat=false;
        while(!trobat){
            /*if(candidat_valid(candidat[i])
                trobat=true;
            */
            i++;
        }
        return trobat;
    }

    //retorna cert si tenim suficients diners, tenim suficient temps per visitar, està obert,
    private boolean candidat_valid(Activitat a, Map<Activitat,boolean> visitats, int diners_gastats, int preu_maxim){
        return !visitats[a] && diners_gastats+a.preu()<preu_maxim;
    }

    private boolean completa(PuntInteres desti, Set<PuntInteres> a_visitar, ArrayList<Activitat> activitats){
        boolean c1, c2;
        c1=activitats.get(activitats.size() - 1).PuntInteres().equals(desti);
        c2=activitats.containsAll(a_visitar); //Activitats vs PI //potser ho podem fer contant els punts a visitar que ja hem visitat aixi estalviem fer un O(n)
        return c1 && c2;
    }

    public Circuit veri_voras(Mapa g, PuntInteres origen, PuntInteres desti, Set<PuntInteres> a_visitar, char tipus_voraç){
        int diners_gastats, grau_satisfaccio;
        LocalTime durada;
        Activitat iCan;
        Circuit resultat=null;
        ArrayList<Activitat> activitats;
        Iterator<Activitat> itr; //THIS!!

        while(!completa() && Queden_candidats() /*&& tenimdiners() && tenimtemps()*/){
            iCan=Buscar_Prometedor(Llista_activitats/itr, tipus_voraç);
            if(candidat_valid()){
                diners_gastats+=iCan.preu();
                //grau_satisfaccio+=iCan.grau_satisfaccio(); //ohshitteee!
                durada.plus(iCan.durada());
                activitats.add(iCan);
            }
            iCan++;
        }
        if(completa())
            resultat=new Circuit(diners_gastats,grau_satisfaccio,durada,activitats);

        return resultat;
    }
}