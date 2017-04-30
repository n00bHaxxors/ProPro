/** @file PuntInteres.java
    @brief Classe PuntInteres
*/
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TimeZone;

/** @class PuntInteres
    @brief Punt de Interès en el mapa
    @author Ismael El Habri
*/

public class PuntInteres {
    //Atributs
    private String nom;
    private float preu;
    private Coordenada ubicacio;
    private HashSet<String> caracteristiques;
    private TimeZone zona;
    
    //Mètodes
    
    /** @brief Crea un punt d'interès amb els parametres
	@pre cert
	@post PuntInteres amb nom n, preu p, coordenades pos, caracteresitques Carac i zona z creat */
    PuntInteres(String nomPunt, float preuVisita, Coordenada pos, ArrayList<String> CaracteristiquesPunt, TimeZone zonaHoraria){
        nom=nomPunt; preu=preuVisita; ubicacio=pos;  zona=zonaHoraria;
        caracteristiques=new HashSet(CaracteristiquesPunt);
    }
    
    /** @brief Consulta el nom del punt d'interès
	@pre cert
	@post retorna el nom */
    public String nom(){  return nom; }
    
    /** @brief Consulta el preu del punt d'interès
	@pre cert
	@post retorna el preu */
    public float preu(){ return preu; }
    
    /** @brief Consulta la posició del punt d'interès
	@pre cert
	@post retorna les coordenades del punt d'interès */
    public Coordenada posicio(){ return ubicacio; }
    
    /** @brief Pregunta si el punt d'Interès té una característica en concret
	@pre cert
	@post retorna cert si el punt d'interès té la característica i fals en c.c.*/
    public boolean conteCaracteristica(String car){ return caracteristiques.contains(car); }
    
}
