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
    /** @brief Consulta la hora en que s'ha fet l'activitat
     @pre cert
     @post Retorna l'hora en que sha fet l'activitat*/
    public LocalTime horaActivitat(){ return hora; }
    /** @brief Consulta el dia en que s'ha fet una activitat
     @pre cert
     @post Retorna el dia en que sha fet l'activitat*/   
    public MonthDay diaActivitat() { return dia; }
}
