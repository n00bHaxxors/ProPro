import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by aula on 5/5/2017.
 */
public class proactiveTravel extends Application implements EventHandler<ActionEvent> {
    public Parent root;
    public Button botoArxiu;
    public Button botoExecutar;
    public Text textPath;
    public RadioButton radioBT;
    public RadioButton radioGD;
    public Scene scene;
    public final ToggleGroup grupAlgorisme = new ToggleGroup();
    @Override
    public void start(Stage primaryStage) throws Exception {
        // DEBUG

        File fileBT = new File("debugBT.txt");
        File fileGD = new File("debugGD.txt");
        if(fileBT.exists() && !fileBT.isDirectory()) {
            IO io = new IO();
            IO.MapaViatge mp = io.llegir("debugBT.txt");
            Iterator<Viatge> it = mp.viatges.iterator();
            while(it.hasNext()){
                Viatge viatge = it.next();
                Backtracking.CircuitExacte(mp.mapa,viatge);
            }
        }
        else if(fileGD.exists() && !fileGD.isDirectory()) {
            IO io = new IO();
            IO.MapaViatge mp = io.llegir("debugGD.txt");
            Iterator<Viatge> it = mp.viatges.iterator();
            while(it.hasNext()){
                Viatge viatge = it.next();
                //AKI VA EL BURÀS
            }
        }
        else {

            //  /DEBUG

            botoArxiu = new Button();
            botoExecutar = new Button();
            textPath = new Text();
            radioBT = new RadioButton();
            radioBT.setToggleGroup(grupAlgorisme);
            radioGD = new RadioButton();
            radioGD.setToggleGroup(grupAlgorisme);

            root = FXMLLoader.load(getClass().getResource("proactiveTravel.fxml"));
            scene = new Scene(root, 750, 500);
            primaryStage.getIcons().add(new Image("/java9.png"));
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource()==botoArxiu){
            FileChooser fc = new FileChooser();
            textPath.setText(fc.showOpenDialog(null).getAbsolutePath());
        }
        if(event.getSource()==botoExecutar){
            if(radioBT.isSelected()){
                File fileBT = new File(textPath.getText());
                if(fileBT.exists() && !fileBT.isDirectory()) {
                    IO io = new IO();
                    IO.MapaViatge mp = null;
                    try {
                        mp = io.llegir(textPath.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Iterator<Viatge> it = mp.viatges.iterator();
                    while(it.hasNext()){
                        Viatge viatge = it.next();
                        Backtracking.CircuitExacte(mp.mapa,viatge);
                    }
                }
            }
            else if(radioGD.isSelected()){
                File fileGD = new File(textPath.getText());
                if(fileGD.exists() && !fileGD.isDirectory()) {
                    IO io = new IO();
                    IO.MapaViatge mp = null;
                    try {
                        mp = io.llegir(textPath.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Iterator<Viatge> it = mp.viatges.iterator();
                    while(it.hasNext()){
                        Viatge viatge = it.next();
                        Voraç.Circuit_Voraç(mp.mapa,viatge);
                    }
                }
            }
            else{
                //throu
            }
        }
    }
    public static void main(String[] args){
        Locale.setDefault(Locale.US);
        launch(args);
    }
}