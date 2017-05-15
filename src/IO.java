/** @file IO.java
 @brief Classe IO
 */

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** @class IO
 @brief Classe que manipula la entrada i sortida del programa, conté el Main() de moment
 @author Lluís Trilla
 */

class IO {
    private static final String SEPARADOR = "*";
    private Scanner scan;
    ArrayList<Client> LlistaClients;
    ArrayList<Lloc> LlistaLlocs;
    ArrayList<Allotjament> LlistaAllotjaments;
    ArrayList<Visitable> LlistaVisitables;
    ArrayList<Visita> LlistaVisites;
    ArrayList<Viatge> LlistaViatges;

    private void casClient(){
        ArrayList<String> preferencies = new ArrayList<String>();
        String nom_client, pref;
        nom_client = scan.nextLine();
        pref = scan.nextLine();
        do {
           preferencies.add(pref);
            pref = scan.nextLine();

        } while (!pref.equals(SEPARADOR));
        LlistaClients.add(new Client(nom_client, preferencies));
    }
    private void casLloc(){
        String nom_lloc = scan.nextLine();
        String [] stringCoord = scan.nextLine().split(",");
        Coordenada cord_lloc = new Coordenada(Float.parseFloat(stringCoord[0]),Float.parseFloat(stringCoord[1]));
        String IDZonaHoraria = scan.nextLine();
        scan.nextLine(); //llegir separador
        LlistaLlocs.add(new Lloc(nom_lloc, cord_lloc, TimeZone.getTimeZone(IDZonaHoraria)));
    }
    private void casAllotjament(){
        String nomAllotjament = scan.nextLine();
        String [] stringCoord = scan.nextLine().split(",");
        Coordenada coordAllotjament = new Coordenada(Float.parseFloat(stringCoord[0]),Float.parseFloat(stringCoord[1]));
        String zonaHoraria = scan.nextLine();
        String categoria = scan.nextLine();
        Float preuHabDoble = Float.parseFloat(scan.nextLine());
        String caracteristica = scan.nextLine();
        ArrayList<String> llistaCaracteristiques = new ArrayList<String>();
        do {
            llistaCaracteristiques.add(caracteristica);
            caracteristica = scan.nextLine();
        } while (!caracteristica.equals(SEPARADOR));
        LlistaAllotjaments.add(new Allotjament(nomAllotjament, (int)(100*preuHabDoble), coordAllotjament, llistaCaracteristiques, TimeZone.getTimeZone(zonaHoraria), categoria));
    }
    private void casVisitable() throws ParseException {
        String nomVisitable = scan.nextLine();
        String[] parts = scan.nextLine().split(",");
        Coordenada coordVisitable = new Coordenada(Float.parseFloat(parts[0]),Float.parseFloat(parts[1]));
        String zonaHoraria = scan.nextLine();
        String tempsVisita = scan.nextLine();
        Float preu = Float.parseFloat(scan.nextLine());
        String caracteristica = scan.nextLine();
        ArrayList<String> llistaCaracteristiques = new ArrayList<String>();
        do {
            llistaCaracteristiques.add(caracteristica);
            caracteristica = scan.nextLine();
        } while (!caracteristica.equals(SEPARADOR));
        String horari = scan.nextLine();
        ArrayList<Visitable.BlocHorari> llistaHoraris = new ArrayList<Visitable.BlocHorari>();
        do {
            MonthDay diaInici, diaFi;
            LocalTime horaInici,horaFi;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
            formatter.withLocale(Locale.US);
            String partsHorari[] = horari.split("-|:|\\s+");
            diaInici = MonthDay.parse(partsHorari[0] + " " + partsHorari[1],formatter);
            diaFi = MonthDay.parse(partsHorari[4] + " " + partsHorari[5],formatter);
            horaInici = LocalTime.parse(partsHorari[7]+':'+partsHorari[8]);
            horaFi = LocalTime.parse(partsHorari[9]+':'+partsHorari[10]);
            llistaHoraris.add(new Visitable.BlocHorari(diaInici,horaInici,diaFi,horaFi));
            horari = scan.nextLine();
        } while (!horari.equals(SEPARADOR) && (horari.length() - horari.replace("-", "").length())== 2);
        String excepcio = horari;
        ArrayList<Visitable.ExcepcioHorari> llistaExcepcions = new ArrayList<Visitable.ExcepcioHorari>();
        if(!excepcio.equals(SEPARADOR)) {
            do {
                MonthDay dia;
                LocalTime horaInici, horaFi;
                final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM d");
                String partsHorari[] = horari.split("-|:|\\s+");
                dia = MonthDay.parse(partsHorari[0] + " " + partsHorari[1], DATE_FORMAT);
                horaInici = LocalTime.parse(partsHorari[3] + ':' + partsHorari[4]);
                horaFi = LocalTime.parse(partsHorari[5] + ':' + partsHorari[6]);
                llistaExcepcions.add(new Visitable.ExcepcioHorari(dia, horaInici, horaFi));
                excepcio = scan.nextLine();
            } while (!excepcio.equals(SEPARADOR));
        }
        LlistaVisitables.add(new Visitable(nomVisitable, (int)(100*preu), coordVisitable, llistaCaracteristiques, TimeZone.getTimeZone(zonaHoraria), LocalTime.parse(tempsVisita), llistaExcepcions, llistaHoraris));
    }
    private void casVisita(){
        String nom_client = scan.nextLine();
        String nom_lloc = scan.nextLine();
        LocalDate data = LocalDate.parse(scan.nextLine()); //data de la visita més recent
        scan.nextLine(); //llegir separador
        Iterator<Client> it = LlistaClients.iterator();
        Client clientActual= it.next();
        Boolean trobat = false;
        while(it.hasNext() && !trobat){
            if(clientActual.nom().equals(nom_client)) trobat = true;
            else clientActual = it.next();
        }
        Iterator<Visitable> jt = LlistaVisitables.iterator();
        Visitable visitableActual = jt.next();
        trobat = false;
        while(jt.hasNext() && !trobat){
            if(visitableActual.nom().equals(nom_lloc)) trobat = true;
            else visitableActual = jt.next();
        }
        clientActual.afegirVisita(visitableActual,data);
    }
    private void casAssociarLloc(){
        String nomAllotjVisitable = scan.nextLine();
        Iterator<Allotjament> it = LlistaAllotjaments.iterator();
        Iterator<Visitable> jt = LlistaVisitables.iterator();
        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobat = false;

        Allotjament allotjamentActual = it.next();
        while (it.hasNext() && !trobat) {
            if (allotjamentActual.nom().equals(nomAllotjVisitable)) trobat = true;
            else allotjamentActual = it.next();
        }
        trobat = false;
        Visitable visitableActual = jt.next();
        while (jt.hasNext() && !trobat) {
            if (visitableActual.nom().equals(nomAllotjVisitable)) trobat = true;
            else visitableActual = jt.next();
        }
        String lloc = scan.nextLine();
        trobat = false;
        Lloc LlocActual = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (LlocActual.nom().equals(lloc)) trobat = true;
            else LlocActual = llocIt.next();
        }
        if (jt.hasNext()) {
            LlocActual.associarPuntInteres(visitableActual);
        } else if (it.hasNext()) {
            LlocActual.associarPuntInteres(allotjamentActual);
        } else {
            //excepcio
        }
        scan.nextLine();
    }
    private void casAssociarTransport(){
        String nomLloc = scan.nextLine();
        String nomMT=scan.nextLine();
        LocalTime duradaTrajecte= LocalTime.parse(scan.nextLine());
        Float preu = Float.parseFloat(scan.nextLine());
        MT_Directe transport = new MT_Directe(nomMT, (int) (preu*100),duradaTrajecte);

        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobat = false;
        Lloc LlocActual = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (LlocActual.nom().equals(nomLloc)) trobat = true;
            else LlocActual = llocIt.next();
        }
        LlocActual.associarTransport(transport);
        scan.nextLine();
    }
    private void casMTDirecte(){
        String nomOrigen = scan.nextLine();
        String nomDesti = scan.nextLine();
        String nomMT=scan.nextLine();
        LocalTime duradaTrajecte= LocalTime.parse(scan.nextLine());
        Float preu = Float.parseFloat(scan.nextLine());


        Iterator<Allotjament> it = LlistaAllotjaments.iterator();
        Iterator<Visitable> jt = LlistaVisitables.iterator();
        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobat = false;

        Allotjament allotjamentActual = it.next();
        while (it.hasNext() && !trobat) {
            if (allotjamentActual.nom().equals(nomDesti)) trobat = true;
            else allotjamentActual = it.next();
        }
        trobat = false;
        Visitable visitableActual = jt.next();
        while (jt.hasNext() && !trobat) {
            if (visitableActual.nom().equals(nomDesti)) trobat = true;
            else visitableActual = jt.next();
        }
        String lloc = scan.next();
        trobat = false;
        Lloc LlocActual = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (LlocActual.nom().equals(lloc)) trobat = true;
            else LlocActual = llocIt.next();
        }
        if (jt.hasNext()) {
            LlocActual.associarTransportDirecte(new MT_Directe(nomMT,(int) (preu*100),duradaTrajecte,visitableActual));
        } else if (it.hasNext()) {
            LlocActual.associarTransportDirecte(new MT_Directe(nomMT,(int) (preu*100),duradaTrajecte,allotjamentActual));
        } else {
            //excepcio
        }
        scan.nextLine();
    }
    private void casMTIndirecte(){
        ArrayList<MT_Indirecte> partences = new ArrayList<MT_Indirecte>();
        String nomOrigen = scan.nextLine();
        String nomDesti = scan.nextLine();
        String nomMT = scan.nextLine();
        LocalTime tempsAnada = LocalTime.parse(scan.nextLine());
        LocalTime tempsTornada = LocalTime.parse(scan.nextLine());
        LocalDate data = LocalDate.parse(scan.nextLine());
        LocalTime hora = LocalTime.parse(scan.nextLine());
        LocalTime durada = LocalTime.parse(scan.nextLine());
        Integer preu = (int)(Float.parseFloat(scan.nextLine())/100);
        String entrada = scan.nextLine();
        while(!entrada.equals("*")){
            if(entrada.contains("-")){
                data = LocalDate.parse(entrada);
            }
            else if(entrada.contains(":")){
                hora=LocalTime.parse(entrada);
                durada=LocalTime.parse(scan.next());
            }
            else if(entrada.contains(".")){
                preu =(int)(100*Float.parseFloat(entrada));
                partences.add(new MT_Indirecte(nomMT,data,hora,durada,preu));
            }
            entrada = scan.nextLine();
        }
        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobat = false;

        Lloc llocOrigen = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (llocOrigen.nom().equals(nomOrigen)) trobat = true;
            else llocOrigen = llocIt.next();
        }
        Lloc llocDesti = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (llocDesti.nom().equals(nomDesti)) trobat = true;
            else llocDesti = llocIt.next();
        }
        llocOrigen.associarHub(new Hub(nomMT,tempsAnada,tempsTornada,llocDesti,partences));
    }
    private void casViatge(){
        LocalDate dataInici = LocalDate.parse(scan.nextLine());
        LocalTime horaInici = LocalTime.parse(scan.nextLine());
        Integer nombreDies = Integer.parseInt(scan.nextLine());
        Integer preuMaxim = Integer.parseInt(scan.nextLine());
        String categoria = scan.nextLine();
        ArrayList<Client> clients = new ArrayList<Client>();
        String nomClient = scan.nextLine();
        Iterator<Client> clientIt = LlistaClients.iterator();
        Boolean trobat = new Boolean(false);
        do{
            Client client = clientIt.next();
            while (clientIt.hasNext() && !trobat) {
                if (client.nom().equals(nomClient)) {
                    trobat = true;
                    clients.add(client);
                }
                else client = clientIt.next();
            }
            nomClient=scan.nextLine();
        }while(!nomClient.equals("*"));
        trobat = false;
        String nomVisitable = scan.nextLine();
        ArrayList<Visitable> visitables = new ArrayList<Visitable>();
        Iterator<Visitable> visitableIt = LlistaVisitables.iterator();
        do{
           Visitable visitable = visitableIt.next();
            while (visitableIt.hasNext() && !trobat) {
                if (visitable.nom().equals(nomVisitable)) {
                    trobat = true;
                    visitables.add(visitable);
                }
                else visitable = visitableIt.next();
            }
            nomVisitable=scan.nextLine();
        }while(!nomVisitable.equals("*"));
        String tipusRuta = scan.nextLine();
        scan.nextLine();
        LlistaViatges.add(new Viatge(dataInici.atTime(horaInici),nombreDies,preuMaxim,categoria,clients,visitables,tipusRuta));
    }

    public Mapa llegir() throws ParseException {
        LlistaClients = new ArrayList<Client>();
        LlistaLlocs = new ArrayList<Lloc>();
        LlistaAllotjaments = new ArrayList<Allotjament>();
        LlistaVisitables = new ArrayList<Visitable>();
        LlistaVisites = new ArrayList<Visita>();
        LlistaViatges = new ArrayList<Viatge>();
        scan = new Scanner(System.in);
        Boolean acabar= false;
        String autor = scan.nextLine();
        while (!acabar) {
            String codi_operacio = scan.nextLine();
            switch (codi_operacio) {
                case "client":
                    casClient();
                    break;
                case "lloc":
                    casLloc();
                    break;
                case "allotjament":
                    casAllotjament();
                    break;
                case "lloc visitable":
                    casVisitable();
                    break;
                case "visita": 
                    casVisita();
                    break;
                case "associar lloc": 
                    casAssociarLloc();
                    break;
                case "associar transport": 
                    casAssociarTransport();
                    break;
                case "transport directe": 
                    casMTDirecte();
                    break;
                case "transport indirecte": 
                    casMTIndirecte();
                    break;
                case "viatge":
                    casViatge();
                    break;
                case "*": 
                    acabar = true;
                    break;
                default:
                    break;
            }
        }
        GrupClients gc = new GrupClients(LlistaClients);
        Mapa mapa = new Mapa(gc,LlistaVisitables,LlistaAllotjaments,LlistaLlocs,LlistaViatges);
        return mapa;
    }
}