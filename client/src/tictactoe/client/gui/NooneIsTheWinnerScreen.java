package tictactoe.client.gui;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tictactoe.client.App;

/**
 *
 * @author KeR
 */
public class NooneIsTheWinnerScreen extends StackPane {
    private final App app;
    
    public NooneIsTheWinnerScreen(App app) {
        this.app = app;
        Region rec = new Region();
        rec.setPrefSize(498, 460);
        rec.setId("recSignin");

        Region over = new Region();
        over.setId("noOneIsTheWinner");
        over.setPrefSize(130, 130);
       

        DropShadow e = new DropShadow();
        e.setOffsetX(0.0f);
        e.setOffsetY(4.0f);
        e.setBlurType(BlurType.GAUSSIAN);
        e.setColor(Color.BLACK);

        Button noOneWine = new Button("No one Is The Winner");
        noOneWine.setId("lose");
        noOneWine.setEffect(e);
        ToggleButton back = new ToggleButton("Back");
        back.setOnAction((event) -> {
            app.setScreen("main");
        });
        back.setPrefSize(180, 20);
        back.setId("back");
        ToggleButton playAgain = new ToggleButton("Play Again");
        playAgain.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                app.setScreen("levels");
            }
        });
        playAgain.setPrefSize(180, 20);
        playAgain.setId("playAgain");
        HBox buttonBox = new HBox(50,back,playAgain);
        

        VBox vbox = new VBox(30, over, noOneWine,buttonBox);
                vbox.setId("vbox");


        getChildren().addAll(rec, vbox);
        setId("stackGameResultScreen");
    }
}
