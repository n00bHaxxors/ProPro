/** @file IO.java
 @brief Classe IO
 */
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.regex.Pattern;

/** @class IO
 @brief Classe que manipula la entrada i sortida del programa, conté el Main() de moment
 @author Lluís Trilla
 */

class IO {
    private static final String SEPARADOR = "*";

    private void llegir() throws ParseException {
        ArrayList<Client> LlistaClients = new ArrayList<Client>();
        ArrayList<Lloc> LlistaLlocs = new ArrayList<Lloc>();
        ArrayList<Allotjament> LlistaAllotjaments = new ArrayList<Allotjament>();
        ArrayList<Visitable> LlistaVisitables = new ArrayList<Visitable>();
        ArrayList<Visita> LlistaVisites = new ArrayList<Visita>();



        Scanner scan = new Scanner(System.in);
        Boolean acabar= false;
        String autor = scan.nextLine();
        while (!acabar) {
            String codi_operacio = scan.nextLine();
            switch (codi_operacio) {
                case "client": {
                    ArrayList<String> preferencies = new ArrayList<String>();
                    String nom_client, pref;
                    nom_client = scan.next();
                    do {
                        pref = scan.next();
                        preferencies.add(pref);
                    } while (pref != SEPARADOR);

					LlistaClients.add(new Client(nom_client, preferencies));
                    break;
                }
                case "lloc": {
                    String nom_lloc = scan.nextLine();
                    scan.useDelimiter(",");
                    Coordenada cord_lloc = new Coordenada(scan.nextFloat(), scan.nextFloat());
                    scan.useDelimiter(Pattern.compile("\\p{javaWhitespace}+"));
                    String IDZonaHoraria = scan.nextLine();
                    scan.nextLine(); //llegir separador
					LlistaLlocs.add(new Lloc(nom_lloc, cord_lloc, TimeZone.getTimeZone(IDZonaHoraria)));
                    break;
                }

                case "allotjament": {
                    String nomAllotjament = scan.nextLine();
                    scan.useDelimiter(",");
                    Coordenada coordAllotjament = new Coordenada(scan.nextFloat(), scan.nextFloat());
                    scan.useDelimiter(Pattern.compile("\\p{javaWhitespace}+"));
                    String zonaHoraria = scan.nextLine();
                    String categoria = scan.nextLine();
                    Float preuHabDoble = scan.nextFloat();
                    String caracteristica = scan.next();
                    ArrayList<String> llistaCaracteristiques = new ArrayList<String>();
                    do {
                        llistaCaracteristiques.add(caracteristica);
                        caracteristica = scan.next();
                    } while (caracteristica != SEPARADOR);
                    LlistaAllotjaments.add(new Allotjament(nomAllotjament, preuHabDoble, coordAllotjament, llistaCaracteristiques, TimeZone.getTimeZone(zonaHoraria), categoria));
                    break;
                }
                case "lloc visitable":{
                    String nomVisitable = scan.next();scan.useDelimiter(",");
                    Coordenada coordVisitable = new Coordenada(scan.nextFloat(), scan.nextFloat());
                    scan.useDelimiter(Pattern.compile("\\p{javaWhitespace}+"));
                    String zonaHoraria = scan.next();
                    String tempsVisita = scan.next();
                    Float preu = scan.nextFloat();
                    String caracteristica = scan.next();
                    ArrayList<String> llistaCaracteristiques = new ArrayList<String>();
                    do {
                        llistaCaracteristiques.add(caracteristica);
                        caracteristica = scan.next();
                    } while (caracteristica != SEPARADOR);
                    String horari = scan.next();
                    ArrayList<Visitable.BlocHorari> llistaHoraris = new ArrayList<Visitable.BlocHorari>();
                    do {
                        LocalDate diaInici, diaFi;
                        LocalTime horaInici,horaFi;
                        final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM dd");
                        String partsHorari[] = horari.split("-|:|\\s+");
                        diaInici = LocalDate.parse(partsHorari[0] + " " + partsHorari[1],DATE_FORMAT);
                        diaFi = LocalDate.parse(partsHorari[2] + " " + partsHorari[3],DATE_FORMAT);
                        horaInici = LocalTime.parse(partsHorari[4]+':'+partsHorari[5]);
                        horaFi = LocalTime.parse(partsHorari[6]+':'+partsHorari[7]);
                        llistaHoraris.add(new Visitable.BlocHorari(diaInici,horaInici,diaFi,horaFi));
                        horari = scan.next();
                    } while (horari != SEPARADOR);
                    String excepcio = scan.next();
                    ArrayList<Visitable.ExcepcioHorari> llistaExcepcions = new ArrayList<Visitable.ExcepcioHorari>();
                    do {
                        LocalDate dia;
                        LocalTime horaInici,horaFi;
                        final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM dd");
                        String partsHorari[] = horari.split("-|:|\\s+");
                        dia = LocalDate.parse(partsHorari[0] + " " + partsHorari[1],DATE_FORMAT);
                        horaInici = LocalTime.parse(partsHorari[2]+':'+partsHorari[3]);
                        horaFi = LocalTime.parse(partsHorari[4]+':'+partsHorari[5]);
                        llistaExcepcions.add(new Visitable.ExcepcioHorari(dia, horaInici, horaFi));
                        excepcio = scan.next();
                    } while (excepcio != SEPARADOR);
                    Visitable v = new Visitable(nomVisitable, preu, coordVisitable, llistaCaracteristiques, TimeZone.getTimeZone(zonaHoraria), LocalTime.parse(tempsVisita), llistaExcepcions, llistaHoraris);
                    break;
                    }
                case "visita": {
                    String nom_client = scan.nextLine();
                    String nom_lloc = scan.nextLine();
                    LocalDate data = LocalDate.parse(scan.nextLine()); //data de la visita més recent
                    scan.next(); //llegir separador
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
                    break;
                }
                case "associar lloc": {
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
                    String lloc = scan.nextLine();
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
                    break;
                }
                case "associar transport": {
                    String nomLloc = scan.nextLine();
                    String nomMT=scan.nextLine();
                    LocalTime duradaTrajecte= LocalTime.parse(scan.nextLine());
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
                    scan.nextLine();
                    break;
                }
                case "transport directe": {
                    String nomOrigen = scan.nextLine();
                    String nomDesti = scan.nextLine();
                    String nomMT=scan.nextLine();
                    LocalTime duradaTrajecte= LocalTime.parse(scan.nextLine());
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
                    String lloc = scan.nextLine();
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
                    scan.nextLine();
                    break;
                }
                case "transport indirecte": {
                    ArrayList<MT_Indirecte.Partença> partences = new ArrayList<MT_Indirecte.Partença>();
                    String nomOrigen = scan.nextLine();
                    String nomDesti = scan.nextLine();
                    String nomMT = scan.nextLine();
                    LocalTime tempsAnada = LocalTime.parse(scan.nextLine());
                    LocalTime tempsTornada = LocalTime.parse(scan.nextLine());
                    LocalDate data = LocalDate.parse(scan.nextLine());
                    LocalTime hora = LocalTime.parse(scan.nextLine());
                    LocalTime durada = LocalTime.parse(scan.nextLine());
                    Integer preu = (int)(scan.nextFloat()/100);
                    String entrada = scan.nextLine();
                    MT_Indirecte.Partença p;
                    while(entrada != "*"){
                        if(entrada.contains("-")){
                            data = LocalDate.parse(entrada);
                        }
                        else if(entrada.contains(":")){
                            hora=LocalTime.parse(entrada);
                            durada=LocalTime.parse(scan.nextLine());
                        }
                        else if(entrada.contains(".")){
                            preu =(int)(100*Float.parseFloat(entrada));
                            partences.add(new MT_Indirecte.Partença(data.atTime(hora),durada,preu));
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
            }
        }
    }
}