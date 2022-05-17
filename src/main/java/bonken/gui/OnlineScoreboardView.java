package bonken.gui;

import bonken.game.Game;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * CLass for showing scoreboard in net game.
 */
public class OnlineScoreboardView extends VBox {
    Label username0;
    Label username1;
    Label username2;
    Label username3;

    Label score0;
    Label score1;
    Label score2;
    Label score3;

    HBox player0;
    HBox player1;
    HBox player2;
    HBox player3;

    public OnlineScoreboardView() {
        super();

        username0 = new Label();
        username1 = new Label();
        username2 = new Label();
        username3 = new Label();

        score0 = new Label();
        score1 = new Label();
        score2 = new Label();
        score3 = new Label();

        player0 = new HBox(username0, score0);
        player1= new HBox(username1, score1);
        player2= new HBox(username2, score2);
        player3= new HBox(username3, score3);

        player0.setSpacing(10);
        player1.setSpacing(10);
        player2.setSpacing(10);
        player3.setSpacing(10);

        player1.setPadding(new Insets(0, 20, 0,0));
        player2.setPadding(new Insets(0, 20, 0,0));
        player3.setPadding(new Insets(0, 20, 0,0));

        this.getChildren().addAll(player0, player1, player2, player3);
        this.getStyleClass().add("score");
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setSpacing(20);

        this.setVisible(false);
    }

    public void show() {
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }

    /**
     * Updates scoreboard upon receiving info from server.
     * @param players
     * @param score
     */
    public void update(String[] players, String[] score) {
        username0.setText(players[0] + ": ");
        username1.setText(players[1] + ": ");
        username2.setText(players[2] + ": ");
        username3.setText(players[3] + ": ");

        score0.setText(score[0]);
        score1.setText(score[1]);
        score2.setText(score[2]);
        score3.setText(score[3]);
    }
}
