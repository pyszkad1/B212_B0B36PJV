package bonken.gui;

import bonken.game.Card;
import bonken.game.Position;
import bonken.game.Round;
import bonken.game.Trick;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.TimerTask;

public class OfflineTrickPane extends TrickPane{
    public OfflineTrickPane(Position bottomPlayer, Callable showBlock, Callable hideBlock) {
        super(bottomPlayer, showBlock, hideBlock);
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

    private void putCard(Card card, Position pos) {

        String image = this.getClass().getResource("/bonken/gui/cards/" + card.getImage()).toExternalForm();
        ImageView imageView = new ImageView(new Image(image));
        imageView.getStyleClass().add("card");

        this.cardPanes[pos.index].getChildren().add(imageView);
    }




}
