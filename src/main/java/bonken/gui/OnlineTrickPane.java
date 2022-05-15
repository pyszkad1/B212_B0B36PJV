package bonken.gui;


import bonken.game.Card;
import bonken.game.Position;
import bonken.game.Round;
import bonken.game.Trick;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.TimerTask;

public class OnlineTrickPane extends TrickPane {
    public OnlineTrickPane(Position bottomPlayer, Callable showBlock, Callable hideBlock) {
        super(bottomPlayer, showBlock, hideBlock);
    }



    public void update(int firstPlayer, String[] trick){
        if (!blocking){
            showBlock.call();
            blocking = true;
        }


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {if (blocking){
                    hideBlock.call();
                }; drawTrick(firstPlayer, trick);});
            }
        } , 3000);
    }

    private void drawTrick(int position, String[] trick) {
        System.out.println("---------DRAWING TRICK---------");
        this.clear();


        Position currPosition = Position.values()[position];
        System.out.println(position + " is position");


        for (int i = 0; i < trick.length; i++) {
            String card = trick[i];
            if(card == null) break;

            putCard(card, currPosition);

            currPosition = currPosition.next();
        }


        adjustPanePositions();
        statusPane.update();

    }

    public void updateOnTrickEnd(int firstPlayer, String[] wholeTrick) {
        showBlock.call();
        blocking = true;
        drawTrick(firstPlayer, wholeTrick);

    }

    private void putCard(String card, Position pos) {
        String image = this.getClass().getResource("/bonken/gui/cards/" + card).toExternalForm();
        ImageView imageView = new ImageView(new Image(image));
        imageView.getStyleClass().add("card");

        this.cardPanes[pos.index].getChildren().add(imageView);
    }




}
