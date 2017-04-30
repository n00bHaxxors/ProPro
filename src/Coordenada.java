/** @file Coordenada.java
 @brief Classe Coordenada
 */
/** @class Coordenada
 @brief Coordenada geogràfica
 @author Lluís Trilla
 */

public class Coordenada {
    private Double x, y;

    /**
     * @brief Crea una coordenada geogràfica a partir de les seves components vertical i horitzontal
     * @pre cert
     * @post Coordenada a la posicio que li hem assignat
     */
    public Coordenada(Double nord, Double est){
        x=est;
        y=nord;
    }
}