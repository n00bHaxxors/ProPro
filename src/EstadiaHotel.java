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
    
    public EstadiaHotel(Allotjament h, MonthDay dia, LocalTime hora){
        super(dia, hora);
        hotel = h;
    }
    
    public String nomAllotjament(){ return hotel.nom(); }
}
