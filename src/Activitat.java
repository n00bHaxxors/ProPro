/** @file Activitat.java
    @brief Classe Activitat
*/
import java.time.*;

/** @class Activitat
    @brief Classe que representa una Activitat
    @author Ismael El Habri
*/
public abstract class Activitat {
    private LocalDate dia;
    private LocalTime hora;
    private int preu;
    
    
    public Activitat(LocalDate d, LocalTime h, int p){
        dia=d;hora=h;preu=p;
    }
    /** @brief Consulta la hora en que s'ha fet l'activitat
     @pre cert
     @post Retorna l'hora en que sha fet l'activitat*/
    public LocalTime horaActivitat(){ return hora; }
    /** @brief Consulta el dia en que s'ha fet una activitat
     @pre cert
     @post Retorna el dia en que sha fet l'activitat*/   
    public LocalDate diaActivitat() { return dia; }
   
    public abstract boolean Acceptable(Circuit c);
    
    /** @brief Consulta la duracio de l'activitat
     @pre cert
     @post Retorna la duració de l'activitat*/
    public abstract LocalTime Duracio();
    
    /** @brief Calcula la Satisfaccio que afageix l'activitat
     @pre cert
     @post Retorna la satisfaccio calculada*/
    public abstract int Satisfaccio(GrupClients g);
    
    /** @brief consulta la ubicacio on s'ha acabat la activitat
     @pre cert
     @post Retorna el PuntInteres on s'ha acabat la activitat*/
    public abstract PuntInteres UbicacioActual();
    
    /** @brief consulta el nom de l'activitat
     @pre cert
     @post Retorna el nom de l'activitat*/
    public abstract String nomAct();
    
    
}
