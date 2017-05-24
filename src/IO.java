/** @file IO.java
 @brief Classe IO
 */

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** @class IO
 @brief Classe que manipula la entrada i sortida del programa
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

    /**
     * @brief Tracta el cas "client" de IO
     * @pre cert
     * @post Emplena les llistes amb les dades llegides
     */
    private void casClient() throws IOException {
        ArrayList<String> preferencies = new ArrayList<String>();
        String nom_client, pref;
        nom_client = scan.readLine();
        pref = scan.readLine();
        do {
           preferencies.add(pref);
            pref = scan.readLine();

        } while (!pref.equals("*"));
        LlistaClients.add(new Client(nom_client, preferencies));
    }
    /**
     * @brief Tracta el cas "lloc" de IO
     * @pre cert
     * @post Emplena les llistes amb les dades llegides
     */
    private void casLloc() throws IOException {
        String nom_lloc = scan.readLine();
        String [] stringCoord = scan.readLine().split(",");
        Coordenada cord_lloc = new Coordenada(Float.parseFloat(stringCoord[0]),Float.parseFloat(stringCoord[1]));
        String IDZonaHoraria = scan.readLine();
        scan.readLine(); //llegir separador
        LlistaLlocs.add(new Lloc(nom_lloc, cord_lloc, TimeZone.getTimeZone(IDZonaHoraria)));
    }
    /**
     * @brief Tracta el cas "allotjament" de IO
     * @pre cert
     * @post Emplena les llistes amb les dades llegides
     */
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
    /**
     * @brief Tracta el cas "lloc visitable" de IO
     * @pre cert
     * @post Emplena les llistes amb les dades llegides
     */
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
    /**
     * @brief Tracta el cas "visita" de IO
     * @pre cert
     * @post Emplena les llistes amb les dades llegides
     */
    private void casVisita() throws IOException {
        String nom_client = scan.readLine();
        String nom_lloc = scan.readLine();
        LocalDate data = LocalDate.parse(scan.readLine()); //data de la visita més recent
        scan.readLine(); //llegir separador
        Iterator<Client> it = LlistaClients.iterator();
        Client clientActual = null; //--sipwarriper: el netbeans mha obligat a inicialitzar-la a algo:/
        Boolean trobat = false;
        while(it.hasNext() && !trobat){
            clientActual= it.next();
            if(clientActual.nom().equals(nom_client)) trobat = true;
        }
        Iterator<Visitable> jt = LlistaVisitables.iterator();
        Visitable visitableActual = null;
        trobat = false;
        while(jt.hasNext() && !trobat){
            visitableActual = jt.next();
            if(visitableActual.nom().equals(nom_lloc)) trobat = true;
        }
        if (clientActual != null && visitableActual!= null) clientActual.afegirVisita(visitableActual,data); //--sipwarriper: probablament aqui s'hauria de fer diferenciació de booleans per tirar excepcio si no troba el client i/o el visitable
    }
    /**
     * @brief Tracta el cas "associar lloc" de IO
     * @pre cert
     * @post Emplena les llistes amb les dades llegides
     */
    private void casAssociarLloc() throws IOException {
        String nomAllotjVisitable = scan.readLine();
        Iterator<Allotjament> it = LlistaAllotjaments.iterator();
        Iterator<Visitable> jt = LlistaVisitables.iterator();
        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobatV = false;
        boolean trobatA = false;

        Allotjament allotjamentActual = null;
        while (it.hasNext() && !trobatA) {
            allotjamentActual = it.next();
            if (allotjamentActual.nom().equals(nomAllotjVisitable)) trobatA = true;
            
        }
        Visitable visitableActual = null;
        while (jt.hasNext() && !trobatV) {
            visitableActual = jt.next();
            if (visitableActual.nom().equals(nomAllotjVisitable)) trobatV = true;
        }
        String lloc = scan.readLine();
        boolean trobat = false;
        Lloc LlocActual = null;
        while (llocIt.hasNext() && !trobat) {
            LlocActual = llocIt.next();
            if (LlocActual.nom().equals(lloc)) trobat = true;
        }
        if (trobatV) {
            visitableActual.assignarLlocPrincipal(LlocActual.nom());
            LlocActual.associarPuntInteres(visitableActual);
        } else if (trobatA) {
            allotjamentActual.assignarLlocPrincipal(LlocActual.nom());
            LlocActual.associarPuntInteres(allotjamentActual);
        } else {
            //excepcio //--sipwarriper: en els ifs anteriors no cladria mmirar si hem trobat el llocActual?
        }
        scan.readLine();
    }
    /**
     * @brief Tracta el cas "associar transport" de IO
     * @pre cert
     * @post Emplena les llistes amb les dades llegides
     */
    private void casAssociarTransport() throws IOException {
        String nomLloc = scan.readLine();
        String nomMT=scan.readLine();
        LocalTime duradaTrajecte= LocalTime.parse(scan.readLine());
        Float preu = Float.parseFloat(scan.readLine());
        MT_Directe transport = new MT_Directe(nomMT, (int) (preu*100),duradaTrajecte);

        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        boolean trobat = false;
        Lloc LlocActual = null;
        while (llocIt.hasNext() && !trobat) {
            LlocActual = llocIt.next();
            if (LlocActual.nom().equals(nomLloc)) trobat = true;
        }
        LlocActual.associarTransport(transport);
        scan.readLine();
    }
    /**
     * @brief Tracta el cas "transport directe" de IO
     * @pre cert
     * @post Emplena les llistes amb les dades llegides
     */
    private void casMTDirecte() throws IOException {
        String nomOrigen = scan.readLine();
        String nomDesti = scan.readLine();
        String nomMT=scan.readLine();
        LocalTime duradaTrajecte= LocalTime.parse(scan.readLine());
        Float preu = Float.parseFloat(scan.readLine());


        Iterator<Allotjament> it = LlistaAllotjaments.iterator();
        Iterator<Visitable> jt = LlistaVisitables.iterator();
        Iterator<Lloc> llocIt = LlistaLlocs.iterator();
        PuntInteres origen = null;
        boolean origenTrobat=false;
        while (it.hasNext() && !origenTrobat) {
            origen=it.next();
            if (origen.nom().equals(nomOrigen)) origenTrobat = true;
        }if(!origenTrobat) {
            while (jt.hasNext() && !origenTrobat) {
                origen = jt.next();
                if (origen.nom().equals(nomDesti)) origenTrobat = true;
            }
        }
        it = LlistaAllotjaments.iterator();
        jt = LlistaVisitables.iterator();
        PuntInteres desti=null;
        boolean destiTrobat=false;
        while (it.hasNext() && !destiTrobat) {
            desti = it.next();
            if (desti.nom().equals(nomDesti)) destiTrobat = true;
        }if(!destiTrobat) {
            while (jt.hasNext()&& !destiTrobat) {
                desti = jt.next();
                if (desti.nom().equals(nomDesti)) destiTrobat = true;
            }
        }
        origen.afegirTransportDirecte(new MT_Directe(nomMT,(int) (preu*100),duradaTrajecte,desti));
        scan.readLine();
    }
    /**
     * @brief Tracta el cas "transport indirecte" de IO
     * @pre cert
     * @post Emplena les llistes amb les dades llegides
     */
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

        Lloc llocOrigen = null;
        while (llocIt.hasNext() && !trobat) {
            llocOrigen = llocIt.next();
            if (llocOrigen.nom().equals(nomOrigen)) trobat = true;
        }
        trobat = false;
        llocIt = LlistaLlocs.iterator();
        Lloc llocDesti = null;
        while (llocDesti!=null && !trobat) {
            llocDesti = llocIt.next();
            if (llocDesti.nom().equals(nomDesti)) trobat = true;
        }
        llocOrigen.associarHub(new Hub(nomMT,tempsAnada,tempsTornada,llocDesti,partences));
    }
    /**
     * @brief Tracta el cas "viatge" de IO
     * @pre cert
     * @post Emplena les llistes amb les dades llegides
     */
    private void casViatge() throws IOException {
        LocalDate dataInici = LocalDate.parse(scan.readLine());
        LocalTime horaInici = LocalTime.parse(scan.readLine());
        Integer nombreDies = Integer.parseInt(scan.readLine());
        Integer preuMaxim = 100*Integer.parseInt(scan.readLine());
        String categoria = scan.readLine();
        ArrayList<Client> clients = new ArrayList<Client>();
        String nomClient = scan.readLine();
        Iterator<Client> clientIt = LlistaClients.iterator();
        Boolean trobat = new Boolean(false);
        do{
            Client client = null;
            while (clientIt.hasNext() && !trobat) {
                client = clientIt.next();
                if (client.nom().equals(nomClient)) {
                    trobat = true;
                    clients.add(client);
                }
            }
            trobat = false;
            clientIt = LlistaClients.iterator();
            nomClient=scan.readLine();
        }while(!nomClient.equals("*"));
        trobat = false;
        String nomVisitable = scan.readLine();
        ArrayList<Visitable> visitables = new ArrayList<Visitable>();
        Iterator<Visitable> visitableIt = LlistaVisitables.iterator();
        Localitzacio origen=null;
        Localitzacio  desti=null;
        Visitable visitableActual = null;
        Localitzacio temp;
        while(!nomVisitable.equals("*")) {
            while (visitableIt.hasNext() && !trobat) {
                visitableActual = visitableIt.next();
                if (visitableActual.nom().equals(nomVisitable)) {
                    nomVisitable = scan.readLine();
                    if(nomVisitable.equals("*")){desti=visitableActual;}
                    else {
                        if (origen == null) origen = visitableActual;
                        else visitables.add(visitableActual);
                    }
                    trobat = true;
                }
            }
            if(!trobat){
                Iterator<Lloc> LlocsIt= LlistaLlocs.iterator();
                while (LlocsIt.hasNext() && !trobat) {
                    temp=LlocsIt.next();
                    if (temp.nom().equals(nomVisitable)) {
                        if(origen==null)origen = temp;
                        else desti=temp;
                        trobat = true;
                        nomVisitable = scan.readLine();
                    }
                }
            }
            trobat = false;
            visitableIt=LlistaVisitables.iterator();
            visitableActual = visitableIt.next();
        }
        ArrayList<String> tipusRuta = new ArrayList<String>();
        String nomRuta = scan.readLine();
        while(!nomRuta.equals("*")){
            tipusRuta.add(nomRuta);
            nomRuta=scan.readLine();
        }
        LlistaViatges.add(new Viatge(dataInici.atTime(horaInici),nombreDies,preuMaxim,categoria,new GrupClients(clients),origen,desti,visitables,tipusRuta));
    }
    /**
     * @brief Retorna un MapaViatge creat a partir de les dades entrades
     * @pre f és el path de l'arxiu d'entrada
     * @post Retorna el MapaViatge
     */
    public MapaViatge llegir(String f) throws ParseException, ExcepcioIOCasDesconegut{
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
            String codi_operacio = scan.readLine();
            while (codi_operacio!=null) {
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
                    default:
                        throw new ExcepcioIOCasDesconegut();
                }
                codi_operacio = scan.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        GrupClients gc = new GrupClients(LlistaClients);
        Mapa mapa = new Mapa(gc,LlistaVisitables,LlistaAllotjaments,LlistaLlocs,LlistaViatges);
        return new MapaViatge(mapa,LlistaViatges);
    }
    /**
     * @brief Crea un arxiu amb el resultat del circuit
     * @pre Es tenen permisos d'escriptura al arxiu especificat
     * @post Crea l'arxiu de sortida i l'emplena
     */
    public void mostrar(Circuit c, String fitxerSortida) throws ExcepcioContingutCasIOErroni {
        try{
            PrintWriter writer = new PrintWriter(fitxerSortida, "UTF-8");
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            writer.println("Preu: " + df.format(c.preu_persona()/100));
            writer.println("Satisfaccio " + df.format(c.grau_satisfaccio()));
            Iterator<Activitat> it = c.Activitats();
            Activitat activitatActual;
            LocalTime horaAnterior = null;
            LocalDate ultimDia = null;
            while(it.hasNext()){
                activitatActual=it.next();
                if(ultimDia==null || !ultimDia.equals(activitatActual.diaActivitat())){
                    writer.println(activitatActual.diaActivitat().toString());
                }
                ultimDia=activitatActual.diaActivitat();
                if(horaAnterior !=null){
                    if(!horaAnterior.equals(activitatActual.horaActivitat())){
                        if((horaAnterior.getHour()<12 || (horaAnterior.getHour()==12&&horaAnterior.getMinute()==0)) &&
                                (activitatActual.horaActivitat().getHour()>14 || activitatActual.horaActivitat().getHour()==14 && activitatActual.horaActivitat().getMinute()>=0 )){
                            if(horaAnterior.getHour()<12){
                                writer.println(horaAnterior.toString() + "-12:00 Temps lliure");
                            }
                            writer.println("12:00-14:00 Dinar");
                            if(activitatActual.horaActivitat().getHour()>14 || (activitatActual.horaActivitat().getHour()==14 && activitatActual.horaActivitat().getMinute()>0)){
                                writer.println("14:00-"+activitatActual.horaActivitat().toString()+" Temps lliure");
                            }

                        }
                        else{//TEMPS LLIURE
                            writer.println(horaAnterior.toString() + '-' + activitatActual.horaActivitat() + " Temps lliure");
                        }
                    }
                }
                horaAnterior=activitatActual.horaActivitat().plusHours(activitatActual.Duracio().getHour()).plusMinutes(activitatActual.Duracio().getMinute());
                writer.println(activitatActual.toString());
            }
            writer.close();
        } catch (IOException e) {
            throw new ExcepcioContingutCasIOErroni();
        }
    }
    /**
     * @brief Crea un arxiu amb les dades KML del circuit
     * @pre Es tenen permisos d'escriptura al arxiu especificat
     * @post Crea l'arxiu KML i l'emplena
     */
    public void crearKML(Circuit c, String fitxerKML, Mapa m){
        try{
            PrintWriter writer = new PrintWriter(fitxerKML, "UTF-8");
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
                    "  <Document>\n" +
                    "    <name>Ruta</name>\n" +
                    "    <description>Ruta que han recorregut els clients</description>\n" +
                    "    <Style id=\"yellowLineGreenPoly\">\n" +
                    "      <LineStyle>\n" +
                    "        <color>7f00ffff</color>\n" +
                    "        <width>4</width>\n" +
                    "      </LineStyle>\n" +
                    "      <PolyStyle>\n" +
                    "        <color>7f00ff00</color>\n" +
                    "      </PolyStyle>\n" +
                    "    </Style>");
            Iterator<Activitat> it1 = c.Activitats();
            Activitat activitatActual1;
            Localitzacio localitzacioAnterior1 = null;
            while(it1.hasNext()) {
                activitatActual1 = it1.next();
                String s = activitatActual1.UbicacioActual();
                Localitzacio l = m.lloc(m.puntInteres(s).nomLloc());
                if(l!=null && (localitzacioAnterior1==null || !localitzacioAnterior1.nom().equals(l.nom()))){
                    writer.print("<Placemark>\n" +
                            "    <name>"+l.nom() +"</name>\n" +
                            "    <Point>\n" +
                            "      <coordinates>"+ l.coordenada().toString()+"</coordinates>\n" +
                            "    </Point>\n" +
                            "  </Placemark>");
                }
                localitzacioAnterior1=l;
            }
                    writer.print("<Placemark>\n" +
                    "      <name>Recorregut</name>\n" +
                    "      <description>Recorregut dels clients</description>\n" +
                    "      <styleUrl>#yellowLineGreenPoly</styleUrl>\n" +
                    "      <LineString>\n" +
                    "        <extrude>1</extrude>\n" +
                    "        <tessellate>1</tessellate>\n" +
                    "        <altitudeMode>absolute</altitudeMode>\n" +
                    "        <coordinates> ");
            Iterator<Activitat> it2 = c.Activitats();
            Activitat activitatActual2;
            Localitzacio localitzacioAnterior2 = null;
            while(it2.hasNext()) {
                activitatActual2 = it2.next();
                String s = activitatActual2.UbicacioActual();
                Localitzacio l = m.lloc(m.puntInteres(s).nomLloc());
                if(l!=null && (localitzacioAnterior1==null || !localitzacioAnterior1.nom().equals(l.nom())))writer.println(l.coordenada().toString());
                localitzacioAnterior2=l;
            }
            writer.print("</coordinates>\n" +
                    "      </LineString>\n" +
                    "    </Placemark>\n" +
                    "  </Document>\n" +
                    "</kml>");
            writer.close();
        } catch (IOException e) {

        }
    }
}