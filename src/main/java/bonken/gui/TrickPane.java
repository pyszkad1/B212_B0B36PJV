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

    protected double cardWidth = 132;
    protected double cardHeight = 180;


    protected Timer timer;

    protected static Border testBorder = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    protected static Border testBorder2 = new Border(new BorderStroke(Color.GOLDENROD, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

    protected Pane[] cardPanes;
    protected Game game;

    Callable showBlock;
    Callable hideBlock;

    public TrickPane(Position bottomPlayer, Callable showBlock, Callable hideBlock) {
        super();

        this.showBlock = showBlock;
        this.hideBlock = hideBlock;

        timer = new Timer();
        this.bottomPlayer = bottomPlayer;
    }

    protected Position bottomPlayer;

    protected void adjustPanePositions() {
        double h = (this.getHeight() - cardHeight) / 2;
        double w = (this.getWidth() - cardWidth) / 2;
        double alignment = (this.getWidth() / 4);

        Position currPos = bottomPlayer;
        for (int i = 0; i < 4; i++) {
            Pane p = cardPanes[i];
            p.setBorder(testBorder);
        if (currPos == bottomPlayer) {
            p.setTranslateY(this.getHeight() - cardHeight - 20);
            p.setTranslateX(w);
            p.setBorder(testBorder2);
        }
        else if (currPos == bottomPlayer.next()) {
            p.setTranslateY(h);
            p.setTranslateX(alignment);
        }
        else if(currPos == (bottomPlayer.next()).next()) {
            p.setTranslateY(20);
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
