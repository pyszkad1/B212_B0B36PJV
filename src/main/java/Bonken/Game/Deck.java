package Bonken.Game;

public class Deck {
    Card deck[] = new Card[52];

    public Deck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 15; j++) {
                Card card = new Card(j, i);
                deck[i*13+j-2] = card;
            }
        }

    }

    public void shuffle(Deck deck) {
    }

    public void deal(Deck deck) {
    }

}
