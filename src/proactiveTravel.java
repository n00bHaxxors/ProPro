import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by aula on 5/5/2017.
 */
public class proactiveTravel extends Application implements EventHandler<ActionEvent> {
    private Button btn;
    private boolean visibilitat = false;
    private Text text;
    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception {
        text = new Text("KEKASSO DEL GUENO");
        text.setVisible(false);
        btn = new Button();
        btn.setText("KEK");
        btn.setTranslateY(20);
        btn.setOnAction(this);
        IO io = new IO();
        IO.MapaViatge mp = io.llegir();
        Iterator<Viatge> it = mp.viatges.iterator();
        while(it.hasNext()){
            Viatge viatge = it.next();
            Backtracking.CircuitExacte(mp.mapa,viatge);
        }
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        root.getChildren().add(text);

        Scene scene = new Scene(root,300,250);
        primaryStage.setTitle("LEL");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource()== btn){
            IO test = new IO();
            try {
                test.llegir();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        Locale.setDefault(Locale.US);
        launch(args);
    }
}
