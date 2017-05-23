/** @file PuntInteres.java
    @brief Classe PuntInteres
*/
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TimeZone;

/** @class PuntInteres
    @brief Punt de Interès en el mapa
    @author Ismael El Habri
*/

public abstract class PuntInteres extends Localitzacio implements Comparable<PuntInteres>{
    //Atributs
    private String nom;
    private int preu;
    private Coordenada ubicacio;
    private HashSet<String> caracteristiques;
    private TimeZone zona;
    private ArrayList<MT_Directe> transportsDirectes;
    private String LlocPrincipal;
    
    //Mètodes
    
    /** @brief Crea un punt d'interès amb els parametres
	@pre cert
	@post PuntInteres amb nom n, preu p, coordenades pos, caracteresitques Carac i zona z creat */
    PuntInteres(String nomPunt, int preuVisita, Coordenada pos, ArrayList<String> CaracteristiquesPunt, TimeZone zonaHoraria){
        nom=nomPunt; preu=preuVisita; ubicacio=pos;  zona=zonaHoraria;
        caracteristiques=new HashSet(CaracteristiquesPunt);
        transportsDirectes= new ArrayList();
    }
    
    /** @brief Consulta el nom del punt d'interès
	@pre cert
	@post retorna el nom */
    @Override
    public String nom(){  return nom; }
    
    /** @brief Consulta el preu del punt d'interès
	@pre cert
	@post retorna el preu */
    public int preu(){ return preu; }
    
    /** @brief Consulta la posició del punt d'interès
	@pre cert
	@post retorna les coordenades del punt d'interès */
    @Override
    public Coordenada coordenada(){ return ubicacio; }
    
    /** @brief Consulta la zona horària del punt d'interes
     @pre cert
     @post Retorna la zona horària del punt d'interes
     */
    @Override
    public TimeZone zona(){ return zona; }
    
    /** @brief Pregunta si el punt d'Interès té una característica en concret
	@pre cert
	@post retorna cert si el punt d'interès té la característica i fals en c.c.*/
    public boolean conteCaracteristica(String car){ return caracteristiques.contains(car); }
    
    /** @brief Afageix un transportDirecte
	@pre cert
	@post transport afegit al conjunt de tranpsorts Directes*/
    public void afegirTransportDirecte(MT_Directe transport){ transportsDirectes.add(transport); }
    
     /** @brief Consulta els transports Directes
	@pre cert
	@post retorna un iterador als transports directes que hi ha*/
    public Iterator<MT_Directe> TransportsDirectes(){
        return transportsDirectes.iterator();
    }
    
    /** @brief Assigna un Lloc Principal al Punt d'Interes 
	@pre cert
	@post Lloc Principal es el nou lloc Principal del punt d'Interes*/
    public void assignarLlocPrincipal(String nom){ LlocPrincipal = nom; }
    
    /** @brief Consulta el nom del lloc Principal al que esta associat
	@pre cert
	@post retorna el nom del Lloc Principal*/
    public String nomLloc(){ return LlocPrincipal; }
    
    /** @brief Crea l'activitat corresponent al punt d'interés
	@pre cert
	@post retorna l'activitat creada*/
    public abstract Activitat ActivitatCorresponent(LocalDateTime ara);
    
    /** @brief Consulta si el punt d'interès estarà obert avui
	@pre this no es lloc de pas
	@post retorna cert si estarà avui i fals en c.c.*/
    public abstract boolean obreAvui(LocalDateTime ara);

    public abstract LocalDateTime ProximaObertura(LocalDateTime ara);
    
    /** @brief Consulta si el Punt d'Interes es lloc de pas
	@pre cert
	@post retorna cert si es lloc de pas, i fals en c.c.*/
    public abstract boolean esLlocPas();
    
    /** @brief Calcula la Satisfaccio que genera a un Grup de Clients aquest Punt d'Interes
     @pre cert
     @post Retorna la satisfaccio calculada*/
    public int grauSatisfaccio(GrupClients g){
        Iterator<Client> itr = g.iteradorClients();
        int resultat = 0;
        while(itr.hasNext()){
            Client c = itr.next();
            Iterator<String> itr2= c.IteradorPreferencies();
            while (itr2.hasNext()){
                String preferencia = itr2.next();
                if (conteCaracteristica(preferencia)) resultat++;
            }
        }
        return resultat;
    }
    
    @Override
    public int compareTo(PuntInteres o){
        return nom.compareTo(o.nom);
    }
    

}
