/** @file EstadaHotel.java
    @brief Classe EstadaHotel
*/
import java.time.*;

/** @class EstadaHotel
    @brief Classe que representa una Estada en un Allotjament
    @author Ismael El Habri
*/
public class EstadaHotel extends Activitat {
    private Allotjament hotel;
    
    /** @brief Constructor de una Estadia a un Hotel
     @pre cert
     @post Crea una estadia amb les dades donades*/
    public EstadaHotel(Allotjament h, MonthDay dia, LocalTime hora){
        super(dia, hora);
        hotel = h;
    }
    /** @brief Consulta el nom del Allotjament
     @pre cert
     @post Retorna el nom del Allotjament*/
    public String nomAllotjament(){ return hotel.nom(); }
}
