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
        Card[] playedCards = new Card[4];
        Card firstPlayedCard = new Card(2, -1);
        for (int i = 0; i < 4; i++) {
            playedCards[i] = cardHands[(firstPlayer+i)%4].play(firstPlayedCard);
            if (i == 0){
                firstPlayedCard = playedCards[i];
            }
        }
        firstSuit = playedCards[0].suit;
        System.out.println("setting first suit " + firstSuit);
        return playedCards;
    }

    public int getTrickWinner(Card[] cards) {
        Card winningCard = cards[0];
        for (int i = 0; i < 4; i++) {
            System.out.println(cards[i].toString());
        }


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

        } else {
            int winningSuit = firstSuit;
            for (int i = 0; i < 4; i++) {
                if (winningSuit != trumps && cards[i].suit == trumps) {
                    winningCard = cards[i];
                    System.out.println(winningCard.toString());
                    winningSuit = trumps;
                }
                if (cards[i].suit == winningSuit && cards[i].rank >= winningCard.rank) {
                    winningCard = cards[i];
                    System.out.print("winning card is " + winningCard.toString());
                    player = (i + firstPlayer)%4;
                    System.out.println(" of player " + player);
                }
            }

        }
        return player;
    }

}

