/** @file IO.java
 @brief Classe IO
 */

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

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

    private void casClient(){
        ArrayList<String> preferencies = new ArrayList<String>();
        String nom_client, pref;
        nom_client = scan.nextLine();
        do {
            pref = scan.nextLine();
            preferencies.add(pref);
        } while (pref != SEPARADOR);
        LlistaClients.add(new Client(nom_client, preferencies));
    }
    private void casLloc(){
        String nom_lloc = scan.nextLine();
        scan.useDelimiter(",");
        Coordenada cord_lloc = new Coordenada(scan.nextFloat(), scan.nextFloat());
        scan.useDelimiter(Pattern.compile("\\p{javaWhitespace}+"));
        String IDZonaHoraria = scan.nextLine();
        scan.nextLine(); //llegir separador
        LlistaLlocs.add(new Lloc(nom_lloc, cord_lloc, TimeZone.getTimeZone(IDZonaHoraria)));
    }
    private void casAllotjament(){
        String nomAllotjament = scan.nextLine();
        scan.useDelimiter(",");
        Coordenada coordAllotjament = new Coordenada(scan.nextFloat(), scan.nextFloat());
        scan.useDelimiter(Pattern.compile("\\p{javaWhitespace}+"));
        String zonaHoraria = scan.nextLine();
        String categoria = scan.nextLine();
        Float preuHabDoble = scan.nextFloat();
        String caracteristica = scan.nextLine();
        ArrayList<String> llistaCaracteristiques = new ArrayList<String>();
        do {
            llistaCaracteristiques.add(caracteristica);
            caracteristica = scan.nextLine();
        } while (caracteristica != SEPARADOR);
        LlistaAllotjaments.add(new Allotjament(nomAllotjament, (int)(100*preuHabDoble), coordAllotjament, llistaCaracteristiques, TimeZone.getTimeZone(zonaHoraria), categoria));
    }
    private void casVisitable() throws ParseException { //DONE
        String nomVisitable = scan.nextLine();
        String[] parts = scan.nextLine().split(",");
        Coordenada coordVisitable = new Coordenada(Float.parseFloat(parts[0]),Float.parseFloat(parts[1]));
        String zonaHoraria = scan.nextLine();
        String tempsVisita = scan.nextLine();
        Float preu = scan.nextFloat();
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
        do {
            MonthDay dia;
            LocalTime horaInici,horaFi;
            final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM d");
            String partsHorari[] = horari.split("-|:|\\s+");
            dia = MonthDay.parse(partsHorari[0] + " " + partsHorari[1],DATE_FORMAT);
            horaInici = LocalTime.parse(partsHorari[3]+':'+partsHorari[4]);
            horaFi = LocalTime.parse(partsHorari[5]+':'+partsHorari[6]);
            llistaExcepcions.add(new Visitable.ExcepcioHorari(dia, horaInici, horaFi));
            excepcio = scan.nextLine();
        } while (!excepcio.equals(SEPARADOR));
        Visitable v = new Visitable(nomVisitable, (int)(100*preu), coordVisitable, llistaCaracteristiques, TimeZone.getTimeZone(zonaHoraria), LocalTime.parse(tempsVisita), llistaExcepcions, llistaHoraris);
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
            if(clientActual.nom()==nom_lloc) trobat = true;
            else clientActual = it.next();
        }
        Iterator<Visitable> jt = LlistaVisitables.iterator();
        Visitable visitableActual = jt.next();
        trobat = false;
        while(jt.hasNext() && !trobat){
            if(visitableActual.nom()==nom_lloc) trobat = true;
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
            if (allotjamentActual.nom() == nomAllotjVisitable) trobat = true;
            else allotjamentActual = it.next();
        }
        trobat = false;
        Visitable visitableActual = jt.next();
        while (jt.hasNext() && !trobat) {
            if (visitableActual.nom() == nomAllotjVisitable) trobat = true;
            else visitableActual = jt.next();
        }
        String lloc = scan.next();
        trobat = false;
        Lloc LlocActual = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (LlocActual.nom() == lloc) trobat = true;
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
        String nomLloc = scan.next();
        String nomMT=scan.next();
        LocalTime duradaTrajecte= LocalTime.parse(scan.next());
        Float preu = scan.nextFloat();
        MT_Directe transport = new MT_Directe(nomMT, (int) (preu*100),duradaTrajecte);

        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobat = false;
        Lloc LlocActual = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (LlocActual.nom() == nomLloc) trobat = true;
            else LlocActual = llocIt.next();
        }
        LlocActual.associarTransport(transport);
        scan.next();
    }
    private void casMTDirecte(){
        String nomOrigen = scan.next();
        String nomDesti = scan.next();
        String nomMT=scan.next();
        LocalTime duradaTrajecte= LocalTime.parse(scan.next());
        Float preu = scan.nextFloat();


        Iterator<Allotjament> it = LlistaAllotjaments.iterator();
        Iterator<Visitable> jt = LlistaVisitables.iterator();
        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobat = false;

        Allotjament allotjamentActual = it.next();
        while (it.hasNext() && !trobat) {
            if (allotjamentActual.nom() == nomDesti) trobat = true;
            else allotjamentActual = it.next();
        }
        trobat = false;
        Visitable visitableActual = jt.next();
        while (jt.hasNext() && !trobat) {
            if (visitableActual.nom() == nomDesti) trobat = true;
            else visitableActual = jt.next();
        }
        String lloc = scan.next();
        trobat = false;
        Lloc LlocActual = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (LlocActual.nom() == lloc) trobat = true;
            else LlocActual = llocIt.next();
        }
        if (jt.hasNext()) {
            LlocActual.associarTransportDirecte(new MT_Directe(nomMT,(int) (preu*100),duradaTrajecte,visitableActual));
        } else if (it.hasNext()) {
            LlocActual.associarTransportDirecte(new MT_Directe(nomMT,(int) (preu*100),duradaTrajecte,allotjamentActual));
        } else {
            //excepcio
        }
        scan.next();
    }
    private void casMTIndirecte(){
        ArrayList<MT_Indirecte.Partença> partences = new ArrayList<MT_Indirecte.Partença>();
        String nomOrigen = scan.next();
        String nomDesti = scan.next();
        String nomMT = scan.next();
        LocalTime tempsAnada = LocalTime.parse(scan.next());
        LocalTime tempsTornada = LocalTime.parse(scan.next());
        MonthDay data = MonthDay.parse(scan.next());
        LocalTime hora = LocalTime.parse(scan.next());
        LocalTime durada = LocalTime.parse(scan.next());
        Integer preu = (int)(scan.nextFloat()/100);
        String entrada = scan.next();
        MT_Indirecte.Partença p;
        while(entrada != "*"){
            if(entrada.contains("-")){
                data = MonthDay.parse(entrada);
            }
            else if(entrada.contains(":")){
                hora=LocalTime.parse(entrada);
                durada=LocalTime.parse(scan.next());
            }
            else if(entrada.contains(".")){
                preu =(int)(100*Float.parseFloat(entrada));
                partences.add(new MT_Indirecte.Partença(data,hora,durada,preu));
            }
        }

        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobat = false;

        Lloc LlocActual = llocIt.next();
        while (llocIt.hasNext() && !trobat) {
            if (LlocActual.nom() == nomOrigen) trobat = true;
            else LlocActual = llocIt.next();
        }
        MT_Indirecte mt = new MT_Indirecte(nomMT,tempsAnada,tempsTornada,LlocActual,partences);
    }

    public void llegir() throws ParseException {
        LlistaClients = new ArrayList<Client>();
        LlistaLlocs = new ArrayList<Lloc>();
        LlistaAllotjaments = new ArrayList<Allotjament>();
        LlistaVisitables = new ArrayList<Visitable>();
        LlistaVisites = new ArrayList<Visita>();
        scan = new Scanner(System.in);
        Boolean acabar= false;
        String autor = scan.nextLine();
        while (!acabar) {
            String codi_operacio = scan.nextLine();
            switch (codi_operacio) {
                case "client": {
                    casClient();
                    break;
                }
                case "lloc": {
                    casLloc();
                    break;
                }

                case "allotjament": {
                    casAllotjament();
                    break;
                }
                case "lloc visitable":{
                    casVisitable();
                    break;
                    }
                case "visita": {
                    casVisita();
                    break;
                }
                case "associar lloc": {
                    casAssociarLloc();
                    break;
                }
                case "associar transport": {
                    casAssociarTransport();
                    break;
                }
                case "transport directe": {
                    casMTDirecte();
                    break;
                }
                case "transport indirecte": {
                    casMTIndirecte();
                    break;
                }
                case "*": {
                    acabar = true;
                    break;
                }
            }
        }
    }
}