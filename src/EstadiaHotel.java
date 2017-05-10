/** @file EstadiaHotel.java
    @brief Classe EstadiaHotel
*/
import java.time.*;

/** @class EstadiaHotel
    @brief Classe que representa una Estadia en un Allotjament
    @author Ismael El Habri
*/
public class EstadiaHotel extends Activitat {
    private Allotjament hotel;
    
    /** @brief Constructor de una Estadia a un Hotel
     @pre cert
     @post Crea una estadia amb les dades donades*/
    public EstadiaHotel(Allotjament h, MonthDay dia, LocalTime hora){
        super(dia, hora);
        hotel = h;
    }
    /** @brief Consulta el nom del Allotjament
     @pre cert
     @post Retorna el nom del Allotjament*/
    public String nomAllotjament(){ return hotel.nom(); }
}
