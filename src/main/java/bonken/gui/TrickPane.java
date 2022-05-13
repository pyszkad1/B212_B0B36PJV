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

public class TrickPane extends Pane {

    boolean trickEndAlreadyDrawn = false;

    private double cardWidth = 132;
    private double cardHeight = 180;


    private Timer timer ;

    private StatusPane statusPane;

    private static Border testBorder = new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

    private Pane[] cardPanes;
    private Game game;

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

        setupCardPanes(bottomPlayer);
        this.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //this.setBackground(new Background(new BackgroundFill(Color.MAGENTA, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void setupCardPanes(Position bottomPlayer) {
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

    private void adjustPanePositions() {
        double h = (this.getHeight() - cardHeight) / 2;
        double w = (this.getWidth() - cardWidth) / 2;
        double alignment = (this.getWidth() / 4);

        Position currPos = Position.North;
        for (int i = 0; i < 4; i++) {
            Pane p = cardPanes[i];
        switch (currPos) {
            case North:
                p.setTranslateY(this.getHeight() - cardHeight);
                p.setTranslateX( w );
                break;
            case East:
                p.setTranslateY(h);
                p.setTranslateX(alignment);
                break;
            case South:
                p.setTranslateY(0);
                p.setTranslateX( w);
                break;
            case West:
                p.setTranslateY( h);
                p.setTranslateX(this.getWidth()  - cardWidth - alignment);
                break;
        }
        currPos = currPos.next();}
    }

    private boolean showingTrickEnd = false;
    private boolean blocking = false;

    public void update() {
        if (blocking){
            hideBlock.call();
        }
        Round round = game.getCurrentRound();

        if(round.trickNum != 0 && !trickEndAlreadyDrawn) {
            trickEndAlreadyDrawn = true;
            showingTrickEnd = true;
            drawTrick(round.tricks.get(round.trickNum - 1));
            showBlock.call();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    Platform.runLater(() -> update());
                    blocking = true;
                    showingTrickEnd = false;
                }
            } , 2500);

        } else if (!showingTrickEnd) {

            Trick trick = round.getCurrentTrick();
            if(trick == null) return;
            drawTrick(trick);
            trickEndAlreadyDrawn = false;

        }
    }

    public void packUpTrick() {
        if(showingTrickEnd == false) return;
        showingTrickEnd = false;
    }

    private void clear() {
        for (Pane p: cardPanes) {
            p.getChildren().clear();
        }
    }

    private void putCard(Card card, Position pos) {

        String image = this.getClass().getResource("/bonken/gui/cards/" + card.getImage()).toExternalForm();
        ImageView imageView = new ImageView(new Image(image));
        imageView.getStyleClass().add("card");

        this.cardPanes[pos.index].getChildren().add(imageView);
    }

    private void drawTrick(Trick trick) {
        System.out.println("---------DRAWING TRICK---------");
        this.clear();
        Card[] cards = trick.getCards();

        Position currPosition = trick.firstToPlay;

        for (int i = 0; i < 4; i++) {
            Card card = cards[i];
            if(card == null) break;

            putCard(card, currPosition);

            currPosition = currPosition.next();
        }


        adjustPanePositions();
        statusPane.update();
    }
}
