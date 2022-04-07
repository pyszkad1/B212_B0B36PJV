package Bonken.Game;

public class Trick {
    int firstPlayer;
    int firstSuit;
    CardHand[] cardHands;
    int trumps = -1;

    public Trick(int firstPlayer, CardHand[] cardHands, int trumps) {
        this.firstPlayer = firstPlayer;
        this.cardHands = cardHands;
        this.trumps = trumps;
    }

    public Trick(int firstPlayer, CardHand[] cardHands) {
        this.firstPlayer = firstPlayer;
        this.cardHands = cardHands;
    }

    // always in order of players
    public Card[] getTrick(){
        Card[] ret = new Card[4];
        Card lastPlayedCard = new Card(2, -1);
        for (int i = 0; i < 4; i++) {
            ret[i] = cardHands[i].play(lastPlayedCard);
            lastPlayedCard = ret[i];
            if (i == firstPlayer) {
                firstSuit = ret[i].suit;
                System.out.println("setting first suit " + firstSuit);
            }
        }
        return ret;
    }

    public int getTrickWinner(Card[] cards) {
        Card winningCard = cards[0];
        System.out.println("jsem tady!");
        System.out.println(cards[0].toString());
        System.out.println(cards[1].toString());
        System.out.println(cards[2].toString());
        System.out.println(cards[3].toString());

        int player = -1;

        for (Card card : cards) {
            if (card.suit == firstSuit) {
                winningCard = card;
                break;
            }
        }

        if (trumps == -1) {
            for (int i = 0; i < 4; i++) {
                if (cards[i].suit == firstSuit && cards[i].rank > winningCard.rank) {
                    winningCard = cards[i];
                    player = i;
                }
            }
            return player;
        } else {
            for (int i = 0; i < 4; i++) {
                if (cards[i].suit == trumps) {
                    winningCard = cards[i];
                    System.out.println(winningCard.toString());
                    firstSuit = trumps;
                }
                if (cards[i].suit == firstSuit && cards[i].rank >= winningCard.rank) {
                    winningCard = cards[i];
                    System.out.println("winning card" + winningCard.toString());

                    player = i;
                }
            }
            return player;
        }
    }

}
