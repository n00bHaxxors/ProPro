/** @file EstadaHotel.java
    @brief Classe EstadaHotel
*/
import java.time.*;
import java.util.Iterator;

/** @class EstadaHotel
    @brief Classe que representa una Estada en un Allotjament
    @author Ismael El Habri
*/
public class EstadaHotel extends Activitat {
    private Allotjament hotel;
    
    /** @brief Constructor de una Estadia a un Hotel
     @pre cert
     @post Crea una estadia amb les dades donades*/
    public EstadaHotel(Allotjament h, LocalDate dia, LocalTime hora){
        super(dia, hora, h.preu());
        hotel = h;
    }
    
    @Override
    public boolean Acceptable (Circuit c, Viatge v){
        return true;
    }
    
    /** @brief Consulta la duracio de la estada al hotel
     @pre cert
     @post Retorna la duració de la estada*/
    @Override
    public LocalTime Duracio(){
        return null; // aixo probablament merexi una redifinició de la classe (a inicialitzar Candidat s'haurà de tenir en compte
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
    public PuntInteres UbicacioActual(){ return hotel; }
    
    /** @brief Consulta el nom del Allotjament
     @pre cert
     @post Retorna el nom del Allotjament*/
    @Override
    public String nomAct(){ return hotel.nom(); }
}
