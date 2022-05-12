package bonken.game;

import bonken.utils.Callable;

import java.util.ArrayList;


public class Trick {
    public int firstPlayer;
    public Position firstToPlay;
    int firstSuit;
    CardHand[] cardHands;
    int trumps = -1;
    Game game;
    Round round;
    Card[] cards;

    int numPlayedCards;

    private int playerToPlay;

    public Trick(Game game, Round round, Position firstToPlay, CardHand[] cardHands, int trumps) {
        this.game = game;
        this.round = round;
        this.firstPlayer = firstToPlay.index;
        this.firstToPlay = firstToPlay;
        this.cardHands = cardHands;
        this.trumps = trumps;
        this.cards = new Card[4];
        this.numPlayedCards = 0;
    }

    private Callable onTrickFinished;

    public void start(Callable onTrickFinished) {

        this.onTrickFinished = onTrickFinished;

        playerToPlay = firstPlayer;

        game.players[firstPlayer].requestCardToPlay(this);

    }

    // always in order of players
    public Card[] getCards() {
        return cards;
    }

    public void addCard(PlayerInterface player, Card card) {

        // check if correct player is playing

        if(player.getId() != playerToPlay) return;

        cards[numPlayedCards] = card;
        numPlayedCards++;

        // check if trick ended

        if(numPlayedCards == 4) {
            wrapUp();
            return;
        }

        playerToPlay = (playerToPlay + 1) % 4;

        // request new player

        game.players[playerToPlay].requestCardToPlay(this);

    }

    private void wrapUp() {
        firstSuit = cards[0].getSuit();
        System.out.println("setting first suit " + firstSuit);

        onTrickFinished.call();
    }

    public int getTrickWinner(Card[] cards) {
        Card winningCard = cards[0];
        for (int i = 0; i < 4; i++) {
            System.out.println(cards[i].toString());
        }

        int player = -1;

        for (Card card : cards) {
            if (card.getSuit() == firstSuit) {
                winningCard = card;
                break;
            }
        }

        if (trumps == -1 || trumps == 4) {
            for (int i = 0; i < 4; i++) {
                if (cards[i].getSuit() == firstSuit && cards[i].getRank() >= winningCard.getRank()) {
                    winningCard = cards[i];
                    player = (i + firstPlayer) % 4;
                }
            }
        } else {
            int winningSuit = firstSuit;
            for (int i = 0; i < 4; i++) {
                if (winningSuit != trumps && cards[i].getSuit() == trumps) {
                    winningCard = cards[i];
                    System.out.println(winningCard.toString());
                    winningSuit = trumps;
                }
                if (cards[i].getSuit() == winningSuit && cards[i].getRank() >= winningCard.getRank()) {
                    winningCard = cards[i];
                    System.out.print("winning card is " + winningCard.toString());
                    player = (i + firstPlayer) % 4;
                    System.out.println(" of player " + player);
                }
            }

        }
        if (round.chosenMiniGameNum != 5) {
            countTrickScore(cards, player);
        } else {
            //Last trick minigame
            if (round.trickNum == 12) {
                game.scoreBoard.updateScoreBoard(-50, player);
            }
        }

        return player;
    }

    private void countTrickScore(Card[] trick, int trickWinner) {
        if (trumps == -1) {
            ArrayList<Card> playedTrick = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                playedTrick.add(trick[i]);
            }
            System.out.println("played trick: " + playedTrick);
            if (round.chosenMiniGameNum == 4) {
                game.scoreBoard.updateScoreBoard(-10, trickWinner);
            } else if (round.chosenMiniGameNum == 0) {
                for (Card card: playedTrick) {
                    if (round.penaltyCards.contains(card)) {
                        if (card.getRank() == 13) {
                            game.scoreBoard.updateScoreBoard(-20, trickWinner);
                        }
                        if (card.getRank() == 11) {
                            game.scoreBoard.updateScoreBoard(-10, trickWinner);
                        }
                        round.penaltyCards.remove(card);
                    }
                }
            } else if (round.chosenMiniGameNum == 1){
                for (Card card: playedTrick) {
                    if (round.penaltyCards.contains(card)) {
                        game.scoreBoard.updateScoreBoard(-15, trickWinner);
                        round.penaltyCards.remove(card);
                    }
                }
            }
            else if (round.chosenMiniGameNum == 2){
                for (Card card: playedTrick) {
                    if (round.penaltyCards.contains(card)) {
                        game.scoreBoard.updateScoreBoard(-45, trickWinner);
                        round.penaltyCards.remove(card);
                    }
                }
            }
            else if (round.chosenMiniGameNum == 3) {
                for (Card card: playedTrick) {
                    if (round.penaltyCards.contains(card)) {
                        game.scoreBoard.updateScoreBoard(-5, trickWinner);
                        round.penaltyCards.remove(card);
                    }
                }
            }
            else if (round.chosenMiniGameNum == 6) {
                for (Card card: playedTrick) {
                    if (round.penaltyCards.contains(card)) {
                        game.scoreBoard.updateScoreBoard(-50, trickWinner);
                        round.penaltyCards.remove(card);
                        System.out.println("You should buy other players beer! :)");
                    }
                }
            }



        } else if (trumps >= 0 && trumps <= 4) {
            game.scoreBoard.updateScoreBoard(10, trickWinner);
        }

    }
}

