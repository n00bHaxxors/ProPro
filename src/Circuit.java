/**
 @file Allotjament.java
    @brief Classe Circuit
*/
/** @class Circuit
    @brief Conté un possible recorregut que faràn els clients
    @author Marc Cané Salamià
*/

public class Circuit {
    private int sumatori_temps, preu_per_persona;
    private Float grau_satisfacio;
    private ArrayList<Lloc> llocs;

    /** @brief Constructor circuit donat un arraylist de llocs
     @pre cert
     @post Circuit creat*/
    public Circuit(ArrayList<Lloc> l){}

    /** @brief Consulta el temps total
     @pre cert
     @post Retorna el temps total que durarà el circuit*/
    public int temps_total() {
        return sumatori_temps;
    }

    /** @brief Consulta el grau de satisfacció (mitja?) que tindràn els clients al fer el circuit
     @pre cert
     @post Retorna el grau de satisfaccio mitjà*/
    public Float grau_satisfacio(Client/Grupclients c){
        return grau_satisfacio; //!wtf
    }

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