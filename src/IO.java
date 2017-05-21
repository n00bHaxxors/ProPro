/** @file IO.java
 @brief Classe IO
 */

import java.io.*;
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
    public static class MapaViatge{
        public ArrayList<Viatge> viatges;
        public Mapa mapa;
        public MapaViatge(Mapa m, ArrayList<Viatge> v){
            mapa = m;
            viatges = v;
        }
    }
    private static final String SEPARADOR = "*";
    private BufferedReader scan;
    ArrayList<Client> LlistaClients;
    ArrayList<Lloc> LlistaLlocs;
    ArrayList<Allotjament> LlistaAllotjaments;
    ArrayList<Visitable> LlistaVisitables;
    ArrayList<Visita> LlistaVisites;
    ArrayList<Viatge> LlistaViatges;

    private void casClient() throws IOException {
        ArrayList<String> preferencies = new ArrayList<String>();
        String nom_client, pref;
        nom_client = scan.readLine();
        pref = scan.readLine();
        do {
           preferencies.add(pref);
            pref = scan.readLine();

        } while (!pref.equals(SEPARADOR));
        LlistaClients.add(new Client(nom_client, preferencies));
    }
    private void casLloc() throws IOException {
        String nom_lloc = scan.readLine();
        String [] stringCoord = scan.readLine().split(",");
        Coordenada cord_lloc = new Coordenada(Float.parseFloat(stringCoord[0]),Float.parseFloat(stringCoord[1]));
        String IDZonaHoraria = scan.readLine();
        scan.readLine(); //llegir separador
        LlistaLlocs.add(new Lloc(nom_lloc, cord_lloc, TimeZone.getTimeZone(IDZonaHoraria)));
    }
    private void casAllotjament() throws IOException {
        String nomAllotjament = scan.readLine();
        String [] stringCoord = scan.readLine().split(",");
        Coordenada coordAllotjament = new Coordenada(Float.parseFloat(stringCoord[0]),Float.parseFloat(stringCoord[1]));
        String zonaHoraria = scan.readLine();
        String categoria = scan.readLine();
        Float preuHabDoble = Float.parseFloat(scan.readLine());
        String caracteristica = scan.readLine();
        ArrayList<String> llistaCaracteristiques = new ArrayList<String>();
        do {
            llistaCaracteristiques.add(caracteristica);
            caracteristica = scan.readLine();
        } while (!caracteristica.equals(SEPARADOR));
        LlistaAllotjaments.add(new Allotjament(nomAllotjament, (int)(100*preuHabDoble), coordAllotjament, llistaCaracteristiques, TimeZone.getTimeZone(zonaHoraria), categoria));
    }
    private void casVisitable() throws ParseException, IOException {
        String nomVisitable = scan.readLine();
        String[] parts = scan.readLine().split(",");
        Coordenada coordVisitable = new Coordenada(Float.parseFloat(parts[0]),Float.parseFloat(parts[1]));
        String zonaHoraria = scan.readLine();
        String tempsVisita = scan.readLine();
        Float preu = Float.parseFloat(scan.readLine());
        String caracteristica = scan.readLine();
        ArrayList<String> llistaCaracteristiques = new ArrayList<String>();
        do {
            llistaCaracteristiques.add(caracteristica);
            caracteristica = scan.readLine();
        } while (!caracteristica.equals(SEPARADOR));
        String horari = scan.readLine();
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
            horari = scan.readLine();
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
                excepcio = scan.readLine();
            } while (!excepcio.equals(SEPARADOR));
        }
        LlistaVisitables.add(new Visitable(nomVisitable, (int)(100*preu), coordVisitable, llistaCaracteristiques, TimeZone.getTimeZone(zonaHoraria), LocalTime.parse(tempsVisita), llistaExcepcions, llistaHoraris));
    }
    private void casVisita() throws IOException {
        String nom_client = scan.readLine();
        String nom_lloc = scan.readLine();
        LocalDate data = LocalDate.parse(scan.readLine()); //data de la visita més recent
        scan.readLine(); //llegir separador
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
    private void casAssociarLloc() throws IOException {
        String nomAllotjVisitable = scan.readLine();
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
        String lloc = scan.readLine();
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
        scan.readLine();
    }
    private void casAssociarTransport() throws IOException {
        String nomLloc = scan.readLine();
        String nomMT=scan.readLine();
        LocalTime duradaTrajecte= LocalTime.parse(scan.readLine());
        Float preu = Float.parseFloat(scan.readLine());
        MT_Directe transport = new MT_Directe(nomMT, (int) (preu*100),duradaTrajecte);

        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobat = false;
        Lloc LlocActual = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (LlocActual.nom().equals(nomLloc)) trobat = true;
            else LlocActual = llocIt.next();
        }
        LlocActual.associarTransport(transport);
        scan.readLine();
    }
    private void casMTDirecte() throws IOException {
        String nomOrigen = scan.readLine();
        String nomDesti = scan.readLine();
        String nomMT=scan.readLine();
        LocalTime duradaTrajecte= LocalTime.parse(scan.readLine());
        Float preu = Float.parseFloat(scan.readLine());


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
        trobat = false;
        Lloc LlocActual = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (LlocActual.nom().equals(nomOrigen)) trobat = true;
            else LlocActual = llocIt.next();
        }
        if (jt.hasNext()) {
            LlocActual.associarTransportDirecte(new MT_Directe(nomMT,(int) (preu*100),duradaTrajecte,visitableActual));
        } else if (it.hasNext()) {
            LlocActual.associarTransportDirecte(new MT_Directe(nomMT,(int) (preu*100),duradaTrajecte,allotjamentActual));
        } else {
            //excepcio
        }
        scan.readLine();
    }
    private void casMTIndirecte() throws IOException {
        ArrayList<MT_Indirecte> partences = new ArrayList<MT_Indirecte>();
        String nomOrigen = scan.readLine();
        String nomDesti = scan.readLine();
        String nomMT = scan.readLine();
        LocalTime tempsAnada = LocalTime.parse(scan.readLine());
        LocalTime tempsTornada = LocalTime.parse(scan.readLine());
        LocalDate data = LocalDate.parse(scan.readLine());
        LocalTime hora = LocalTime.parse(scan.readLine());
        LocalTime durada = LocalTime.parse(scan.readLine());
        Integer preu = (int)(Float.parseFloat(scan.readLine())/100);
        String entrada = scan.readLine();
        while(!entrada.equals("*")){
            if(entrada.contains("-")){
                data = LocalDate.parse(entrada);
            }
            else if(entrada.contains(":")){
                hora=LocalTime.parse(entrada);
                durada=LocalTime.parse(scan.readLine());
            }
            else if(entrada.contains(".")){
                preu =(int)(100*Float.parseFloat(entrada));
                partences.add(new MT_Indirecte(nomMT,data,hora,durada,preu));
            }
            entrada = scan.readLine();
        }
        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobat = false;

        Lloc llocOrigen = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (llocOrigen.nom().equals(nomOrigen)) trobat = true;
            else llocOrigen = llocIt.next();
        }
        trobat = false;
        llocIt = LlistaLlocs.iterator();
        Lloc llocDesti = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (llocDesti.nom().equals(nomDesti)) trobat = true;
            else llocDesti = llocIt.next();
        }
        llocOrigen.associarHub(new Hub(nomMT,tempsAnada,tempsTornada,llocDesti,partences));
    }
    private void casViatge() throws IOException {
        LocalDate dataInici = LocalDate.parse(scan.readLine());
        LocalTime horaInici = LocalTime.parse(scan.readLine());
        Integer nombreDies = Integer.parseInt(scan.readLine());
        Integer preuMaxim = Integer.parseInt(scan.readLine());
        String categoria = scan.readLine();
        ArrayList<Client> clients = new ArrayList<Client>();
        String nomClient = scan.readLine();
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
            nomClient=scan.readLine();
        }while(!nomClient.equals("*"));
        trobat = false;
        String nomVisitable = scan.readLine();
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
            nomVisitable=scan.readLine();
        }while(!nomVisitable.equals("*"));
        ArrayList<String> tipusRuta = new ArrayList<String>();
        String temp = scan.readLine();
        while(!temp.equals("*")){
            tipusRuta.add(temp);
            temp=scan.readLine();
        }
        LlistaViatges.add(new Viatge(dataInici.atTime(horaInici),nombreDies,preuMaxim,categoria,new GrupClients(clients),visitables,tipusRuta));
    }

    public MapaViatge llegir(String f) throws ParseException {
        LlistaClients = new ArrayList<Client>();
        LlistaLlocs = new ArrayList<Lloc>();
        LlistaAllotjaments = new ArrayList<Allotjament>();
        LlistaVisitables = new ArrayList<Visitable>();
        LlistaVisites = new ArrayList<Visita>();
        LlistaViatges = new ArrayList<Viatge>();
        try {
            scan = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Boolean acabar= false;
        try {
            String autor = scan.readLine();
            while (!acabar) {
                String codi_operacio = scan.readLine();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        GrupClients gc = new GrupClients(LlistaClients);
        Mapa mapa = new Mapa(gc,LlistaVisitables,LlistaAllotjaments,LlistaLlocs,LlistaViatges);
        return new MapaViatge(mapa,LlistaViatges);
    }
}