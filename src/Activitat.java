/** @file Activitat.java
    @brief Classe Activitat
*/
import java.time.*;

/** @class Activitat
    @brief Classe que representa una Activitat
    @author Ismael El Habri
*/
public class Activitat {
    private MonthDay dia;
    private LocalTime hora;
    
    public Activitat(MonthDay d, LocalTime h){
        dia=d;hora=h;
    }
    
    public LocalTime horaActivitat(){ return hora; }
    
    public MonthDay diaActivitat() { return dia; }
}
