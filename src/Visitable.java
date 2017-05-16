/** @file Visitable.java
    @brief Classe Visitable
*/
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.util.ArrayList;
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
        
        private MonthDay inici, fi;
        private LocalTime horaInici, horaFi;
        
        /** @brief Crea una franja amb els parametres
	@pre cert
	@post BlocHorari amb la franja corresponent */
        public BlocHorari(MonthDay diaInici, LocalTime hInici, MonthDay diaFi, LocalTime hFi) {
            inici=diaInici; fi=diaFi;
            horaInici=hInici; horaFi=hFi;
        }
        
        /** @brief Indica si en el bloc del horari actual el lloc estarà obert durant una visita
	@pre bloc horari diferent de null i temps iniciVisita posterior a fiVisita
	@post retorna cert si estarà obert durant tota la visita i fals en cas contrari */
        public boolean estaraObert(MonthDay visita, LocalTime iniciVisita, LocalTime fiVisita){
            
            boolean resultat = !visita.isBefore(inici) && !visita.isAfter(fi);
            resultat = resultat && !iniciVisita.isBefore(horaInici) && !iniciVisita.isAfter(horaFi) && !fiVisita.isBefore(horaInici) && !fiVisita.isAfter(horaFi);
            return resultat;
        }
        
        /** @brief Consulta si estem en aquesta Franja horaria
	@pre cert
	@post retorna cert si estem en la franja */
        public boolean esAquestFranja(MonthDay dia){
            return !dia.isBefore(inici) && !dia.isAfter(fi);
        }
        
        /** @brief consulta la hora de opertura de aquesta franja
	@pre cert
	@post retorna un localTime amb la hora de opertura de aquesta franja*/
        public LocalTime horaOpertura(){ return horaInici; }
        
        /** @brief consulta la hora de opertura d'aquesta franja
	@pre cert
	@post retorna un localTime amb la hora de opertura d'aquesta franja*/
        public LocalTime horaTencament(){ return horaFi; }
    }
    
        /** @class ExcepcioHorari
            @brief Dia amb horari excepcional
            @author Ismael El Habri
        */
    public static class ExcepcioHorari{
        private MonthDay dia;
        private LocalTime inici, fi;
         
        /** @brief Crea una excepció d'horari
            @pre cert
            @post Excepció d'horari el dia diaEx amb nou horari de hInici a hFi per el dia en concret
        */
        public ExcepcioHorari(MonthDay diaEx, LocalTime hInici, LocalTime hFi){
            dia=diaEx; inici=hInici; fi=hFi;
        }
        
        /** @brief Indica si una visita està en el dia d'horari excepcional i si estara obert durant les hores de la visita
            @pre cert
            @post cert si estara obert i el dia en concret i fals en c.c.
        */
        public boolean estaObert(MonthDay Visita, LocalTime iniciVisita, LocalTime fiVisita){
            return Visita.equals(dia) &&  iniciVisita.isAfter(inici) && iniciVisita.isBefore(fi) && fiVisita.isAfter(inici) && fiVisita.isBefore(fi);
        }
        /** @brief Indica si estem en el dia amb horari this
            @pre cert
            @post cert si estem en aquest dia i fals en cas contrari
        */
        public boolean EsAvui(MonthDay d){ return d.equals(dia); }
        
        /** @brief consulta la hora de opertura de aquesta franja
	@pre cert
	@post retorna un localTime amb la hora de opertura de aquesta franja*/
        public LocalTime horaOpertura(){ return inici; }
        
        /** @brief consulta la hora de opertura d'aquest dia excepcional
	@pre cert
	@post retorna un localTime amb la hora de opertura d'aquest dia excepcional*/
        public LocalTime horaTencament(){ return fi; }
        
    }
            
    private boolean llocPas = false;
    private ArrayList<BlocHorari> horari;
    private ArrayList<ExcepcioHorari> diesExcepcionals;
    private LocalTime tempsRec;
    //potser cal inicialitzar-lo a null
    
    /** @brief Crea un Lloc Visitable amb els parametres
	@pre cert
	@post Lloc Visitable amb nom n, preu p, coordenades pos, caracteresitques Carac, zona z, visita recomenada tr, tancat festius i horai calendari creat */
    public Visitable(String nom, int preu, Coordenada pos, ArrayList<String> Caracteristiques, TimeZone zona, LocalTime tempsRecomenat, ArrayList<ExcepcioHorari> excepcions, ArrayList<BlocHorari> calendari){
        super(nom, preu, pos,Caracteristiques,zona);
	tempsRec=tempsRecomenat;
        diesExcepcionals=new ArrayList(excepcions);
        horari = new ArrayList(calendari);
    }
    /** @brief Crea un Lloc de pass amb els parametres
	@pre cert
	@post Lloc de pas a partir d'un lloc*/
    public Visitable(Lloc llocDePas){
        super(llocDePas.nom(), 0, llocDePas.coordenada(), null, llocDePas.zona());
        llocPas=true;
        tempsRec=null;
        diesExcepcionals=null;
        horari=null;        
    }
    
    /** @brief Consulta si en un moment de un dia el Visitable estarà obert
	@pre el visitable no és lloc de pas
	@post retorna cert si estarà obert en aquell moment, i fals en c.c.*/
    public boolean estaraObert(MonthDay dia, LocalTime hora){
        int hores = tempsRec.getHour(), minuts = tempsRec.getMinute(), segons = tempsRec.getSecond();
        LocalTime fiVisita = hora.plusHours(tempsRec.getHour()).plusMinutes(tempsRec.getMinute()).plusSeconds(tempsRec.getSecond());
        boolean resultat = false;
        //cal iterarho primer els excepció i despres mirar si va o no
        Iterator<ExcepcioHorari> itr = diesExcepcionals.iterator();
        while (!resultat && itr.hasNext()){
            ExcepcioHorari excepcio = itr.next();
            resultat = excepcio.estaObert(dia, hora, fiVisita);                    
        }
        if (!resultat){
            Iterator<BlocHorari> itr2 = horari.iterator();
            while (!resultat && itr.hasNext()){
                BlocHorari franja = itr2.next();
                resultat = franja.estaraObert(dia, hora, fiVisita);
            }
        }
        return resultat;
    }
    
    /** @brief Consulta si el visitable es lloc de pas
	@pre cert
	@post retorna cert si es lloc de pas, i fals en c.c.*/
    public boolean esLlocPas(){ return llocPas; }
    
    /** @brief Consulta el temps de visita recomenat
	@pre this no es lloc de pas
	@post retorna el temps de visita.*/
    public LocalTime tempsVisitaRec(){ return tempsRec; }
    
    /** @brief Consulta si el visitable estarà obert avui
	@pre this no es lloc de pas
	@post retorna cert si estarà avui i fals en c.c.*/
    @Override
    public boolean obreAvui(LocalDateTime ara){
        boolean resultat = false, casEspecial = false; MonthDay avui = MonthDay.of(ara.getMonth(), ara.getDayOfMonth());
        Iterator<ExcepcioHorari> itr = diesExcepcionals.iterator();
        while (!resultat && itr.hasNext()){
            ExcepcioHorari excepcio = itr.next();
            resultat = excepcio.EsAvui(avui);
            if (!ara.toLocalTime().isBefore(excepcio.horaTencament())) casEspecial = true;
        }
        if (!resultat){
            Iterator<BlocHorari> itr2 = horari.iterator();
            while (!resultat && itr.hasNext()){
                BlocHorari franja = itr2.next();
                resultat = franja.esAquestFranja(avui);
                if (!ara.toLocalTime().isBefore(franja.horaTencament())) casEspecial = true;
            }
        }
        return resultat && !casEspecial;
    }
    
    /** @brief consulta a quina hora obrira avui apartir del moment indicat
	@pre this no es lloc de pas i obreAvui == true
	@post retorna una LocalDateTime amb dia i hora d'opertura avui*/
    @Override
    public LocalDateTime ProximaObertura(LocalDateTime ara){
        if (estaraObert(MonthDay.of(ara.getMonth(), ara.getDayOfMonth()), ara.toLocalTime())) {
            return ara;
        } else{
            ExcepcioHorari excepcio = null; BlocHorari franja = null;
            boolean fi = false, excepcional = false; LocalDateTime aux = ara;
            Iterator<ExcepcioHorari> itr = diesExcepcionals.iterator();
            while (!fi && itr.hasNext()) {
                excepcio = itr.next();
                fi = excepcio.EsAvui(MonthDay.of(aux.getMonth(), aux.getDayOfMonth()));
            }
            if (!fi) {
                Iterator<BlocHorari> itr2 = horari.iterator();
                while (!fi && itr.hasNext()) {
                    franja = itr2.next();
                    fi = franja.esAquestFranja(MonthDay.of(aux.getMonth(), aux.getDayOfMonth()));
                }
            }
            if (fi) {
                LocalTime horaOpertura, horaTencament;
                if (excepcional) {
                    horaOpertura = excepcio.horaOpertura();
                    horaTencament = excepcio.horaTencament();
                } else {
                    horaOpertura = franja.horaOpertura();
                    horaTencament = franja.horaTencament();
                }
                aux = ara.toLocalDate().atTime(horaOpertura.getHour(), horaOpertura.getMinute());
            }
            return aux;
        }
    }
    
    /** @brief Crea l'activitat corresponent al visitable
	@pre ObreAvui == true i this no es lloc de pas
	@post retorna l'activitat creada*/
    @Override
    public Activitat ActivitatCorresponent(LocalDateTime ara) { 
        LocalDateTime obertura = ProximaObertura(ara);
        return new Visita (this, obertura.toLocalDate(),obertura.toLocalTime());
    }
}
