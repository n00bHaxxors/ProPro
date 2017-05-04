/** @file Visitable.java
    @brief Classe Visitable
*/
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TimeZone;
/** @class Visitable
    @brief Lloc Visitable (subtipus de punt d'interès)
    @author Ismael El Habri
*/
public class Visitable extends PuntInteres{
    
        /** @class BlocHorari
            @brief Franja temporal en qué el un lloc visitable está obert.
            @author Ismael El Habri
        */
    public static class BlocHorari{
        
        private LocalDate inici, fi;
        private LocalTime horaInici, horaFi;
        
        /** @brief Crea una franja amb els parametres
	@pre cert
	@post BlocHorari amb la franja corresponent */
        public BlocHorari(LocalDate diaInici, LocalTime hInici, LocalDate diaFi, LocalTime hFi) {
            inici=diaInici; fi=diaFi;
            horaInici=hInici; horaFi=hFi;
        }
        
        /** @brief Indica si en el bloc del horari actual el lloc estarà obert durant una visita
	@pre bloc horari diferent de null i temps iniciVisita posterior a fiVisita
	@post retorna cert si estarà obert durant tota la visita i fals en cas contrari */
        public boolean estaraObert(LocalDateTime iniciVisita, LocalDateTime fiVisita){
            boolean resultat = false;
            if(!iniciVisita.isBefore(LocalDateTime.of(inici,horaInici)) && !fiVisita.isAfter(LocalDateTime.of(fi,horaFi))){
                resultat = !iniciVisita.toLocalTime().isBefore(horaInici)&&!fiVisita.toLocalTime().isAfter(horaFi);
            }
            return resultat;
        }
    }
    
        /** @class ExcepcioHorari
            @brief Dia amb horari excepcional
            @author Ismael El Habri
        */
    public static class ExcepcioHorari{
        private LocalDate dia;
        private LocalTime inici, fi;
        public ExcepcioHorari(LocalDate diaEx, LocalTime hInici, LocalTime hFi){
            dia=diaEx; inici=hInici; fi=hFi;
        }
        
    }
            
    private boolean llocPas = false;
    private ArrayList<BlocHorari> horari;
    private HashSet<LocalDate> dies_tancat;
    private LocalTime tempsRec;
    //potser cal inicialitzar-lo a null
    
    /** @brief Crea un Lloc Visitable amb els parametres
	@pre cert
	@post Lloc Visitable amb nom n, preu p, coordenades pos, caracteresitques Carac, zona z, visita recomenada tr, tancat festius i horai calendari creat */
    public Visitable(String nom, float preu, Coordenada pos, ArrayList<String> Caracteristiques, TimeZone zona, LocalTime tempsRecomenat, ArrayList<ExcepcioHorari> excepcions, ArrayList<BlocHorari> calendari){
        super(nom, preu, pos,Caracteristiques,zona);
	tempsRec=tempsRecomenat;
        dies_tancat=new HashSet(festius);
        horari = new ArrayList(calendari);
    }
    /** @brief Crea un Lloc de pass amb els parametres
	@pre cert
	@post Lloc de pas a partir d'un lloc*/
    public Visitable(Lloc llocDePas){
        super(llocDePas.nom(), 0, llocDePas.coordenada(), null, llocDePas.zona());
        llocPas=true;
        tempsRec=null;
        dies_tancat=null;
        horari=null;        
    }
    
    
    /** @brief Consulta si en un moment de un dia el Visitable estarà obert
	@pre el visitable no és lloc de pas
	@post retorna cert si estarà obert en aquell moment, i fals en c.c.*/
    public boolean estaraObert(LocalDateTime dia){
        int hores = tempsRec.getHour(), minuts = tempsRec.getMinute(), segons = tempsRec.getSecond();
        LocalDateTime fiVisita= dia.plusHours(hores).plusMinutes(minuts).plusSeconds(segons);
        boolean resultat = false;
        if (!dies_tancat.contains(fiVisita.toLocalDate())){
            Iterator itr = horari.iterator();
            while (!resultat && itr.hasNext()){
                BlocHorari franja = (BlocHorari) itr.next();
                resultat = franja.estaraObert(dia, fiVisita);
            }
        }
        return resultat;
    }
    
    public boolean esLlocPas(){ return llocPas; }
    
   
    
}
