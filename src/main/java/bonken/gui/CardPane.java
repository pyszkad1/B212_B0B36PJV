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

    private Action<Card> onCardClicked;

    public CardPane(Action<Card> onCardClicked) {
        super();

        this.onCardClicked = onCardClicked;
        this.setAlignment(Pos.CENTER);

    }

    private PlayerInterface player;

    public void setPlayer (PlayerInterface player) {

        this.player = player;
        update();
    }

    public void update() {

        this.getChildren().clear();

        ArrayList<Card> cards = player.getCardHand().getHand();
        int cardCount = cards.size();


        double size = this.getWidth();
        double sizeForOverlap = size - cardWidth;
        double spacePerCard = sizeForOverlap / (cardCount - 1);
        double space = spacePerCard - cardWidth;


        this.setSpacing(space > 0 ? 0 : space);

        for (int i = 0; i < cardCount; i++) {

            Card card = cards.get(i);

            String image = this.getClass().getResource("/bonken/gui/cards/" + card.getImage()).toExternalForm();
            ImageView imageView = new ImageView(new Image(image));
            imageView.getStyleClass().add("card");
            VBox pane = new VBox(imageView);
            pane.setAlignment(Pos.BOTTOM_CENTER);
            pane.setMinHeight(cardHeight + cardHoverDistance);

            this.getChildren().add(pane);

            pane.setOnMouseClicked(event -> pressedCard(card));
            pane.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if(newVal && player.canPlay(card)) {
                    imageView.setTranslateY(-cardHoverDistance);

                } else {
                    imageView.setTranslateY(0);
                }
            });
        }
    }


    public void pressedCard(Card card) {

        if(player.canPlay(card) == false) {
            System.out.println("Cannot play this card right now");
            return;
        }

        onCardClicked.call(card);
    }
}
