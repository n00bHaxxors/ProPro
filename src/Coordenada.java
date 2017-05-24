/** @file Coordenada.java
 @brief Classe Coordenada
 */
/** @class Coordenada
 @brief Coordenada geogràfica
 @author Lluís Trilla
 */

public class Coordenada {
    /** @invariant -180 < x > 180 i -90 < y > 90
     */
    private Float x, y; //< Coordenades geogràfiques

    /**
     * @brief Crea una coordenada geogràfica a partir de les seves components vertical i horitzontal
     * @pre cert
     * @post Coordenada a la posicio que li hem assignat
     */
    public Coordenada(Float nord, Float est){
        x=est;
        y=nord;
    }
    /**
     * @brief Retorna la interpretació de text de la coordenada
     * @pre cert
     * @post Retorna la string
     */
    public String toString(){
        return (x.toString()+','+y.toString()+",0");
    }
}