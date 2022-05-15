package bonken.gui;

import bonken.game.*;
import bonken.utils.Action;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TrickPane extends Pane {

    boolean trickEndAlreadyDrawn = false;

    private double cardWidth = 132;
    private double cardHeight = 180;


    protected Timer timer;

    protected StatusPane statusPane;

    private static Border testBorder = new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

    protected Pane[] cardPanes;
    protected Game game;

    Callable showBlock; Callable hideBlock;

    public void setGame(Game game) {
        this.game = game;
        statusPane.setGame(game);
    }

    public TrickPane(Position bottomPlayer, Callable showBlock, Callable hideBlock) {
        super();

        this.showBlock = showBlock;
        this.hideBlock = hideBlock;

        timer = new Timer();
        statusPane = new StatusPane();
        this.bottomPlayer = bottomPlayer;
        setupCardPanes();
        this.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //this.setBackground(new Background(new BackgroundFill(Color.MAGENTA, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    protected Position bottomPlayer;

    private void setupCardPanes() {
        cardPanes = new Pane[4];

        Position currPos = bottomPlayer;


        for (int i = 0; i < 4; i++) {
            Pane p = new Pane();
            cardPanes[i] = p;
            p.setMinHeight(cardHeight);
            p.setMinWidth(cardWidth);
            p.setBorder(testBorder);
            this.getChildren().add(p);
        }

        this.getChildren().add(statusPane);
        statusPane.setTranslateX(0);
        statusPane.setTranslateX(0);


    }

    protected void adjustPanePositions() {
        double h = (this.getHeight() - cardHeight) / 2;
        double w = (this.getWidth() - cardWidth) / 2;
        double alignment = (this.getWidth() / 4);

        Position currPos = bottomPlayer;
        for (int i = 0; i < 4; i++) {
            Pane p = cardPanes[i];
        if (currPos == bottomPlayer) {
            p.setTranslateY(this.getHeight() - cardHeight);
            p.setTranslateX(w);
        }
        else if (currPos == bottomPlayer.next()) {
            p.setTranslateY(h);
            p.setTranslateX(alignment);
        }
        else if(currPos == (bottomPlayer.next()).next()) {
            p.setTranslateY(0);
            p.setTranslateX(w);
        }
        else if (currPos == ((bottomPlayer.next()).next()).next()) {
            p.setTranslateY(h);
            p.setTranslateX(this.getWidth() - cardWidth - alignment);
        }

        currPos = currPos.next();
        }
    }

    protected boolean showingTrickEnd = false;
    protected boolean blocking = false;


    public void packUpTrick() {
        if(showingTrickEnd == false) return;
        showingTrickEnd = false;
    }

    protected void clear() {
        for (Pane p: cardPanes) {
            p.getChildren().clear();
        }
    }




    public void killTimer(){
        if (timer != null) {
            timer.cancel();
        }
    }
}
