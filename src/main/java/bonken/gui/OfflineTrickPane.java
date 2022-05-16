package bonken.gui;

import bonken.game.*;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.TimerTask;
import java.util.logging.Logger;

public class OfflineTrickPane extends TrickPane {

    private static final Logger LOGGER = Logger.getLogger(OfflineTrickPane.class.getName());
    private StatusPane statusPane;

    public OfflineTrickPane(Position bottomPlayer, Callable showBlock, Callable hideBlock) {
        super(bottomPlayer, showBlock, hideBlock);
        statusPane = new StatusPane();
        setupCardPanes();
    }

    public void setGame(Game game) {
        this.game = game;
        statusPane.setGame(game);
    }

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

    private void drawTrick(Trick trick) {
        LOGGER.info("Drawing trick.");
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

    private void setupCardPanes() {
        cardPanes = new Pane[4];

        for (int i = 0; i < 4; i++) {
            Pane p = new Pane();
            cardPanes[i] = p;
            p.setMinHeight(cardHeight);
            p.setMinWidth(cardWidth);
            this.getChildren().add(p);
        }

        this.getChildren().add(statusPane);
        statusPane.setTranslateX(10);
        statusPane.setTranslateY(10);
    }

    private void putCard(Card card, Position pos) {

        String image = this.getClass().getResource("/bonken/gui/cards/" + card.getImage()).toExternalForm();
        ImageView imageView = new ImageView(new Image(image));
        imageView.getStyleClass().add("card");

        this.cardPanes[pos.index].getChildren().add(imageView);
    }

}
