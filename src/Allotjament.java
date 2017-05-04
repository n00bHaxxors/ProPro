/** @file Allotjament.java
    @brief Classe Allotjament
*/
import java.util.ArrayList;
import java.util.TimeZone;
/** @class Allotjament
    @brief Allotjament (subtipus de punt d'interès)
    @author Ismael El Habri
*/

public class Allotjament extends PuntInteres {
    private String categoria;
    
    /** @brief Crea un punt d'allotjament amb els parametres
	@pre cert
	@post Allotjament amb nom n, preu p, coordenades pos, caracteresitques Carac zona z i categoria cat creat */
    public Allotjament(String nom, float preu, Coordenada pos, ArrayList<String> Caracteristiques, TimeZone zona, String categoriaAllotjament){
        super(nom, preu, pos, Caracteristiques, zona);
        categoria = categoriaAllotjament;
    }
    
     /** @brief Consulta la categoria de l'allotjament
	@pre cert
	@post retorna la categoria */
    public String categoria() {
        return categoria;
    }
}
