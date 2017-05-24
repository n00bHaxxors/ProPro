/** @file Excepcions.java
 @brief Classes d'excepcions
 */
class ExcepcioIOCasDesconegut extends Exception{
    ExcepcioIOCasDesconegut(){
        super("Cas de IO desconegut");
    }
    ExcepcioIOCasDesconegut(String s){
        super(s);
    }
}
class ExcepcioContingutCasIOErroni extends Exception{
    ExcepcioContingutCasIOErroni(){
        super("Dades errònies a un cas de IO");
    }
    ExcepcioContingutCasIOErroni(String s){
        super(s);
    }
}class ExcepcioEscripturaKML extends Exception{
    ExcepcioEscripturaKML(){
        super("Error al intentar escriure l'arxiu KML");
    }
    ExcepcioEscripturaKML(String s){
        super(s);
    }
}
class ExcepcioRadioButtons extends Exception{
    ExcepcioRadioButtons(){
        super("No s'ha seleccionat ni BT ni Greedy, hauria de ser impossible");
    }
    ExcepcioRadioButtons(String s){
        super(s);
    }
}