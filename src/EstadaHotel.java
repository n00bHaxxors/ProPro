/** @file EstadaHotel.java
    @brief Classe EstadaHotel
*/
import java.time.*;
import java.util.Iterator;
import java.util.Set;

/** @class EstadaHotel
    @brief Classe que representa una Estada en un Allotjament
    @author Ismael El Habri
*/
public class EstadaHotel extends Activitat {
    /** @invariant hotel!=null
     */
    private Allotjament hotel; //<Allotjament on fem l'estada al hotel
    private LocalTime duracio; //< Atribut que ens diu el temps que passem al hotel
    
    /** @brief Constructor de una Estadia a un Hotel
     @pre parametres != null
     @post Crea una estadia amb les dades donades*/
    public EstadaHotel(Allotjament h, LocalDate dia, LocalTime hora, LocalTime d){
        super(dia, hora, h.preu());
        hotel = h; duracio = d;
    }
    
    /** @brief Consulta si l'Activitat és acceptable
     @pre circuit i viatge no buits
     @post retorna cert si és acceptable i fals en c.c.*/
    @Override
    public boolean Acceptable (Circuit c, Viatge v,Set<Visitable> obl){
        boolean resultat = hotel.categoria().equals(v.categoria());
        return resultat;
    }
    
    /** @brief Consulta la duracio de la estada al hotel
     @pre cert
     @post Retorna la duració de la estada*/
    @Override
    public LocalTime Duracio(){
        return duracio; // aixo probablament merexi una redifinició de la classe (a inicialitzar Candidat s'haurà de tenir en compte
    }
    
    /** @brief Calcula la Satisfaccio que afageix l'activitat
     @pre cert
     @post Retorna la satisfaccio calculada*/
    @Override
    public int Satisfaccio(GrupClients g){
        return hotel.grauSatisfaccio(g);
    }
    
    /** @brief consulta la ubicacio on s'ha acabat la activitat
     @pre cert
     @post Retorna el PuntInteres on s'ha acabat la activitat*/
    @Override
    public String UbicacioActual(){ return hotel.nom(); }
    
    /** @brief Consulta el nom del Allotjament
     @pre cert
     @post Retorna el nom del Allotjament*/
    @Override
    public String nomAct(){ return hotel.nom(); }
        
    /** @brief Passa l'estada al hotel a format String
     @pre cert
     @post Retorna una String amb l'estada al hotel*/
    @Override
    public String toString(){
        LocalTime horaFinal = horaActivitat().plusHours(Duracio().getHour()).plusMinutes(Duracio().getMinute());
        String s = horaActivitat().toString() + " - " + horaFinal.toString() + " " + nomAct();
        return s;
    }
}
