package Bonken.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Trick {
    int firstPlayer;
    int firstSuit;
    CardHand[] cardHands;
    int trumps = -1;
    Game game;
    Round round;

    public Trick(Game game, Round round, int firstPlayer, CardHand[] cardHands, int trumps) {
        this.game = game;
        this.round = round;
        this.firstPlayer = firstPlayer;
        this.cardHands = cardHands;
        this.trumps = trumps;
    }

    public Trick(Game game, Round round, int firstPlayer, CardHand[] cardHands) {
        this.game = game;
        this.round = round;
        this.firstPlayer = firstPlayer;
        this.cardHands = cardHands;
        trumps = -1;
    }

    // always in order of players
    public Card[] getTrick() {
        Card[] playedCards = new Card[4];
        Card firstPlayedCard = new Card(2, -1);
        for (int i = 0; i < 4; i++) {
            playedCards[i] = cardHands[(firstPlayer + i) % 4].play(firstPlayedCard);
            if (i == 0) {
                firstPlayedCard = playedCards[i];
            }
        }
        firstSuit = playedCards[0].getSuit();
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
            List<Card> playedTrick = Arrays.asList(trick);

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
                        System.out.println("jsem tady a popnul jsem věc " + round.penaltyCards);
                    }
                }
            }



        } else if (trumps >= 0 && trumps <= 4) {
            game.scoreBoard.updateScoreBoard(10, trickWinner);
        }

    }
}

