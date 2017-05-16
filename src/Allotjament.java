/** @file Allotjament.java
    @brief Classe Allotjament
*/
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public Allotjament(String nom, int preu, Coordenada pos, ArrayList<String> Caracteristiques, TimeZone zona, String categoriaAllotjament){
        super(nom, preu, pos, Caracteristiques, zona);
        categoria = categoriaAllotjament;
    }
    
     /** @brief Consulta la categoria de l'allotjament
	@pre cert
	@post retorna la categoria */
    public String categoria() {
        return categoria;
    }
    
    /** @brief Crea l'activitat corresponent al Allotjament
	@pre cert
	@post retorna l'activitat creada*/
    @Override
    public Activitat ActivitatCorresponent(LocalDateTime ara){
        return new EstadaHotel(this, ara.toLocalDate(), ara.toLocalTime());
    }
    
    /** @brief Consulta si l'Allotjament estarà obert avui
	@pre this no es lloc de pas
	@post retorna cert si estarà avui i fals en c.c.*/
    @Override
    public boolean obreAvui(LocalDateTime ara){
        return ara.toLocalTime().isBefore(LocalTime.of(0, 0));
    }
    
    @Override
    public LocalDateTime ProximaObertura(LocalDateTime ara){
        if(ara.toLocalTime().isBefore(LocalTime.of(0, 0))) return ara;
        else return ara.toLocalDate().atTime(LocalTime.of(4, 0));
    }

}
