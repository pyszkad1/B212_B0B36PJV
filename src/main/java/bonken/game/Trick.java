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

        game.players[firstPlayer].requestCardToPlay(this, round.chosenMiniGameNum);

    }

    public Card[] getCards() {
        return cards;
    }

    public void addCard(PlayerInterface player, Card card) {

        if(player.getId() != playerToPlay) return;

        cards[numPlayedCards] = card;
        numPlayedCards++;

        if(numPlayedCards == 4) {
            wrapUp();
            return;
        }

        playerToPlay = (playerToPlay + 1) % 4;

        game.players[playerToPlay].requestCardToPlay(this, round.getChosenMiniGameNum());

    }

    /**
     * Ends the trick.
     */
    private void wrapUp() {
        firstSuit = cards[0].getSuit();

        onTrickFinished.call();
    }

    /**
     *
     * @param cards the played trick
     * @return winner of the trick
     */
    public int getTrickWinner(Card[] cards) {
        Card winningCard = cards[0];

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
                    winningSuit = trumps;
                }
                if (cards[i].getSuit() == winningSuit && cards[i].getRank() >= winningCard.getRank()) {
                    winningCard = cards[i];
                    player = (i + firstPlayer) % 4;
                }
            }

        }
        if (round.chosenMiniGameNum != 5) {
            countTrickScore(cards, player);
        } else {
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
                    }
                }
            }

        } else if (trumps >= 0 && trumps <= 4) {
            game.scoreBoard.updateScoreBoard(10, trickWinner);
        }

    }
}

