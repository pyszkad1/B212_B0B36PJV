package bonken.gui;

import bonken.game.Game;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ScoreboardView extends VBox {
    Game game;

    Label label0;
    Label label1;
    Label label2;
    Label label3;

    HBox player0;
    HBox player1;
    HBox player2;
    HBox player3;

    public void setGame(Game game) {
        this.game = game;
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        player0 = new HBox(new Label(game.getPlayers()[0].getUsername() + ":"), label0);
        player1= new HBox(new Label(game.getPlayers()[1].getUsername() + ":"), label1);
        player2= new HBox(new Label(game.getPlayers()[2].getUsername() + ":"), label2);
        player3= new HBox(new Label(game.getPlayers()[3].getUsername() + ":"), label3);
        player0.setSpacing(10);
        player1.setSpacing(10);
        player2.setSpacing(10);
        player3.setSpacing(10);
        //TODO alignment

        player1.setPadding(new Insets(0, 20, 0,0));
        player2.setPadding(new Insets(0, 20, 0,0));
        player3.setPadding(new Insets(0, 20, 0,0));



        this.getChildren().addAll(player0, player1, player2, player3);
    }

    public ScoreboardView() {

        this.setVisible(false);



    }

    public void show() {


        label0.setText(String.valueOf( game.getPlayers()[0].getScore()));
        label1.setText(String.valueOf( game.getPlayers()[1].getScore()));
        label2.setText(String.valueOf( game.getPlayers()[2].getScore()));
        label3.setText(String.valueOf( game.getPlayers()[3].getScore()));

        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }

}
