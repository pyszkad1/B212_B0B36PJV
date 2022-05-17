package bonken.gui;

import bonken.game.Game;
import bonken.game.Position;
import bonken.utils.Callable;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Timer;

/**
 * Abstract class for showing trick.
 */
public abstract class TrickPane extends Pane {

    boolean trickEndAlreadyDrawn = false;

    protected double cardWidth = 132;
    protected double cardHeight = 180;


    protected Timer timer;

    protected static Border otherPlayerBorder = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    protected static Border playerBorder = new Border(new BorderStroke(Color.GOLDENROD, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

    protected Pane[] cardPanes;
    protected Game game;

    Callable showBlock;
    Callable hideBlock;

    /**
     *
     * @param bottomPlayer position of the player, default is North
     * @param showBlock called after playing a card
     * @param hideBlock called when the player is supposed to play
     */
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
            p.setBorder(otherPlayerBorder);
        if (currPos == bottomPlayer) {
            p.setTranslateY(this.getHeight() - cardHeight - 20);
            p.setTranslateX(w);
            p.setBorder(playerBorder);
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

    /**
     * Used when ending trick.
     */
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
