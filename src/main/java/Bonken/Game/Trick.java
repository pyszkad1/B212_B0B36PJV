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
        //TODO
        Card[] playedCards = new Card[4];
        Card lastPlayedCard = new Card(2, -1);
        for (int i = 0; i < 4; i++) {
            playedCards[i] = cardHands[(firstPlayer+i)%4].play(lastPlayedCard);
            lastPlayedCard = playedCards[i];
        }
        firstSuit = playedCards[0].suit;
        System.out.println("setting first suit " + firstSuit);
        return playedCards;
    }

    public int getTrickWinner(Card[] cards) {
        Card winningCard = cards[0];
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

