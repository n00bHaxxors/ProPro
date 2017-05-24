import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
/** @class proactiveTravel
 @brief Classe principal que conté GUI
 @author Lluís Trilla
 */
public class proactiveTravel extends Application implements EventHandler<ActionEvent> {
    public Parent root;
    public Button botoInput;
    public Button botoKML;
    public Button botoOutput;
    public Button botoExecutar;
    public Text textInput;
    public Text textOutput;
    public Text textKML;
    public RadioButton radioBT;
    public RadioButton radioGD;
    public Scene scene;
    public final ToggleGroup grupAlgorisme = new ToggleGroup();
    @Override
    public void start(Stage primaryStage) throws Exception {
            botoInput = new Button();
            botoOutput = new Button();
            botoKML = new Button();
            botoExecutar = new Button();
            textInput = new Text();
            textOutput = new Text();
            textKML = new Text();
            radioBT = new RadioButton();
            radioBT.setToggleGroup(grupAlgorisme);
            radioGD = new RadioButton();
            radioGD.setToggleGroup(grupAlgorisme);

            root = FXMLLoader.load(getClass().getResource("proactiveTravel.fxml"));
            scene = new Scene(root, 400, 300);
            primaryStage.setScene(scene);
            primaryStage.setTitle("ProactiveTravel");
            primaryStage.show();
    }

    /**
     * @brief Demana la sortida per la IO
     * @pre cert
     * @post Crea els arxius de sortida
     */
    private void cridarSortida(){
        File file = new File(textInput.getText());
        if(file.exists() && !file.isDirectory()) {
            IO io = new IO();
            IO.MapaViatge mp = null;
            try {
                mp = io.llegir(textInput.getText());
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (ExcepcioIOCasDesconegut excepcioIOCasDesconegut) {
                excepcioIOCasDesconegut.printStackTrace();
            }
            Iterator<Viatge> it = mp.viatges.iterator();
            while(it.hasNext()){
                Viatge viatge = it.next();
                HashMap<String,Circuit> hashCircuits=null;
                if(radioBT.isSelected()) hashCircuits= Backtracking.CircuitExacte(mp.mapa,viatge);
                else if(radioGD.isSelected()) hashCircuits=Voraç.Circuit_Voraç(mp.mapa,viatge);
                Circuit circuitBarat=hashCircuits.get("ruta barata");
                Circuit circuitCurt=hashCircuits.get("ruta curta");
                Circuit circuitSatisf=hashCircuits.get("ruta satisfactoria");
                try {
                    if (circuitBarat != null) {
                        io.mostrar(circuitBarat, textOutput.getText());
                        io.crearKML(circuitBarat, textKML.getText(), mp.mapa);
                    }
                    if (circuitCurt != null) {
                        io.mostrar(circuitCurt, textOutput.getText());
                        io.crearKML(circuitCurt, textKML.getText(), mp.mapa);
                    }
                    if (circuitSatisf != null) {
                        io.mostrar(circuitSatisf, textOutput.getText());
                        io.crearKML(circuitSatisf, textKML.getText(), mp.mapa);
                    }
                } catch (ExcepcioContingutCasIOErroni excepcioContingutCasIOErroni) {
                    excepcioContingutCasIOErroni.printStackTrace();
                }
            }
        }
    }
    @Override
    /**
     * @brief Metode handle per tractar amb els botons
     * @pre cert
     * @post ---
     */
    public void handle(ActionEvent event) {
        if (event.getSource()== botoInput){
            FileChooser fc = new FileChooser();
            textInput.setText(fc.showOpenDialog(null).getAbsolutePath());
        }
        if (event.getSource()== botoOutput){
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fc.getExtensionFilters().add(extFilter);
            textOutput.setText(fc.showSaveDialog(null).getAbsolutePath());
        }
        if (event.getSource()== botoKML){
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("KML files (*.kml)", "*.kml");
            fc.getExtensionFilters().add(extFilter);
            textKML.setText(fc.showSaveDialog(null).getAbsolutePath());
        }
        if(event.getSource()==botoExecutar){
            cridarSortida();
        }
    }
    public static void main(String[] args){
        Locale.setDefault(Locale.US);
        launch(args);
    }
}

//        __
//    ___( o)> i am the wise duck of code, your code will compile without errors, but only if you say "compile well ducko"
//    \ <_. )
//     `---'