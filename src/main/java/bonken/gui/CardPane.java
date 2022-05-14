package bonken.gui;

import bonken.game.Card;
import bonken.game.CardHand;
import bonken.game.Player;
import bonken.game.PlayerInterface;
import bonken.utils.Action;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.Console;
import java.util.ArrayList;

public class CardPane extends HBox {


    private double cardWidth = 132;
    private double cardHeight = 180;
    private double cardHoverDistance = 20;

    private Action<String> onCardClicked;

    public CardPane(Action<String> onCardClicked) {
        super();

        this.onCardClicked = onCardClicked;
        this.setAlignment(Pos.CENTER);

    }

    private PlayerInterface player;

    public void setPlayer (PlayerInterface player) {

        this.player = player;
        update();
    }

    public void updateBefore(String[] cards, String[] playableCards) {

        this.getChildren().clear();


        int cardCount = cards.length;


        double size = this.getWidth();
        double sizeForOverlap = size - cardWidth;
        double spacePerCard = sizeForOverlap / (cardCount - 1);
        double space = spacePerCard - cardWidth;


        this.setSpacing(space > 0 ? 0 : space);

        for (int i = 0; i < cardCount; i++) {
            boolean playable = false;
            String card = cards[i];

            for (String string: playableCards) {
                if (card.equals(string)){
                    playable = true;
                }
            }

            String image = this.getClass().getResource("/bonken/gui/cards/" + card).toExternalForm();
            ImageView imageView = new ImageView(new Image(image));
            imageView.getStyleClass().add("card");
            VBox pane = new VBox(imageView);
            pane.setAlignment(Pos.BOTTOM_CENTER);
            pane.setMinHeight(cardHeight + cardHoverDistance);

            this.getChildren().add(pane);

            if (playable) {
                pane.setOnMouseClicked(event -> pressedCard(card));
                pane.hoverProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        imageView.setTranslateY(-cardHoverDistance);

                    } else {
                        imageView.setTranslateY(0);
                    }
                });
            }
        }
    }
    public void updateAfter(String[] cards) {

        this.getChildren().clear();


        int cardCount = cards.length;


        double size = this.getWidth();
        double sizeForOverlap = size - cardWidth;
        double spacePerCard = sizeForOverlap / (cardCount - 1);
        double space = spacePerCard - cardWidth;


        this.setSpacing(space > 0 ? 0 : space);

        for (int i = 0; i < cardCount; i++) {
            String card = cards[i];

            String image = this.getClass().getResource("/bonken/gui/cards/" + card).toExternalForm();
            ImageView imageView = new ImageView(new Image(image));
            imageView.getStyleClass().add("card");
            VBox pane = new VBox(imageView);
            pane.setAlignment(Pos.BOTTOM_CENTER);
            pane.setMinHeight(cardHeight + cardHoverDistance);

            this.getChildren().add(pane);
        }
    }


    public void pressedCard(String card) {

        onCardClicked.call(card);
    }
}
