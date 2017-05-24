/** @file Activitat.java
    @brief Classe Activitat
*/
import java.time.*;
import java.util.Set;

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

    protected Activitat() {
    }

    /** @brief Consulta la hora en que s'ha fet l'activitat
     @pre cert
     @post Retorna l'hora en que sha fet l'activitat*/
    
    public LocalTime horaActivitat(){ return hora; }
    
    /** @brief Consulta el dia en que s'ha fet una activitat
     @pre cert
     @post Retorna el dia en que sha fet l'activitat*/   
    public LocalDate diaActivitat() { return dia; }
    
    /** @brief Consulta el preu de una Activitat
     @pre cert
     @post Retorna el preu de l'activitat*/
    public int preuAct(){ return preu;}
   
    /** @brief Consulta si l'Activitat és acceptable
     @pre circuit i viatge no buits
     @post retorna cert si és acceptable i fals en c.c.*/
    public abstract boolean Acceptable(Circuit c, Viatge v, Set<Visitable> obl);
    
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
     @post Retorna el nom de la Localització on s'ha acabat la activitat*/
    public abstract String UbicacioActual();
    
    /** @brief consulta el nom de l'activitat
     @pre cert
     @post Retorna el nom de l'activitat*/
    public abstract String nomAct();
    
    /** @brief Passa la activitat a format String
     @pre cert
     @post Retorna una String amb l'activitat*/
    @Override
    public abstract String toString();

    /** @brief Fa la comparació corresponent en funcio del tipus de variable a optimitzar
     @pre tipus=b,c,s
     @post Retorna cert si l'activitat actual és millor que la millor activitat trobada fins el moment*/
    public Boolean comparar(Activitat millor, GrupClients gc, char tipus){
        Boolean res=true;
        if(millor!=null){
            switch(tipus){
                case 'b':
                    res=preu<millor.preu;
                    break;
                case 's':
                    res=Satisfaccio(gc)>millor.Satisfaccio(gc);
                    break;
                case 'c':
                    res=Duracio().isBefore(millor.Duracio());
                    break;
            }
        }
        return res;
    }
    
}
