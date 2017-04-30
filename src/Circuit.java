/**
 @file Allotjament.java
    @brief Classe Circuit
*/
/** @class Circuit
    @brief Conté un possible recorregut que faràn els clients
    @author Marc Cané Salamià
*/

import java.time.*;
import java.util.ArrayList;

public class Circuit {
    private int preu_per_persona;
    private Float grau_satisfacio;
    private LocalTime sumatori_temps;
    private ArrayList<Lloc> llocs;

    /** @brief Constructor circuit amb paràmetres
     @pre cert
     @post Circuit amb preu,grau,temps i llocs creat*/
    public Circuit(int preu, Float grau, LocalTime t, ArrayList<Lloc> l){
        preu_per_persona=preu; grau_satisfacio=grau; sumatori_temps=t; llocs=l;
    }

    /** @brief Consulta el temps total
     @pre cert
     @post Retorna el temps total que durarà el circuit*/
    public int temps_total() {
        return sumatori_temps;
    }

    /** @brief Consulta el grau de satisfacció (mitja?) que tindràn els clients al fer el circuit
     @pre cert
     @post Retorna el grau de satisfaccio mitjà*/
    public Float grau_satisfacio(Grupclients c){ return grau_satisfacio; } //grupclients o client?

    /** @brief Consulta el preu per persona
     @pre cert
     @post Retorna el preu per persona*/
    public int preu_persona(){
        return preu_per_persona;
    }

}

/** @brief
 @pre cert
 @post */