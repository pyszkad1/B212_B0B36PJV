package Bonken.Game;

import java.util.ArrayList;
import java.util.Scanner;

public class Round {
    CardHand[] cardHands;
    Player[] players;
    Deck deck;
    ArrayList<Integer> minigames;
    int startingPlayer;
    int chosenMiniGameNum;
    Game game;
    MiniGameTrumps chosenMiniGameTrump;
    ArrayList<Card> penaltyCards;
    int trickNum = 0;

    public Round(Game game, Deck deck, ArrayList<Integer> minigames, Player[] players) {
        this.game = game;
        this.players = players;
        this.deck = deck;
        this.minigames = minigames;
        createCardHands();
    }

    private void createCardHands() {
        ArrayList<ArrayList<Card>> hands = deck.deal();
        System.out.println(hands.toString());
        cardHands = new CardHand[4];
        for (int i = 0; i < 4; i++) {
            cardHands[i] = new CardHand(hands.get(i));
            cardHands[i].sortHand();
        }
    }

    private void getStartingPlayer() {
        startingPlayer = -1;
        for (Player player : players) {
            if (player.hisTurn) {
                startingPlayer = player.id;
            }
        }

        System.out.println("Starting PLAYER IS " + startingPlayer);
    }

    public void chooseGame() {
        getStartingPlayer();
        Scanner scanner = new Scanner(System.in);
        System.out.println("PLAYER " + startingPlayer + " is choosing a minigame.");
        System.out.println("Choose from: " + minigames);
        chosenMiniGameNum = scanner.nextInt();
        while (!minigames.contains(chosenMiniGameNum)) {
            System.out.println("choose eligible minigame from " + minigames + " pls");
            chosenMiniGameNum = scanner.nextInt();
        }
        minigames.remove(chosenMiniGameNum);
        chosenMiniGameTrump = new MiniGameTrumps(chosenMiniGameNum);

        //TODO negative/positive minigame choosing
        if (chosenMiniGameNum > 6) {
            System.out.println("positive minigame");

            System.out.println("The game will be played with "
                    + chosenMiniGameTrump.trumpNames[chosenMiniGameTrump.trumps]
                    + " as trumps");
        } else {
            MiniGameNegative chosenNegative = new MiniGameNegative(chosenMiniGameNum);
            penaltyCards = chosenNegative.penaltyCards;
            System.out.println("negative minigame");
            System.out.println("The game will be played with no trumps");
        }
    }

    public void playRound() {
        chooseGame();
        int leadingPlayer = (startingPlayer + 3) % 4;
        System.out.println("LEADING PLAYER IS " + leadingPlayer);
        int[] tricksTaken = {0,0,0,0};
        trickNum = 0;
        for (int i = 0; i < 13; i++) {
            if ((chosenMiniGameNum == 2 || chosenMiniGameNum == 6) && penaltyCards.size() == 0){
                System.out.println("the only card was played");
                break;
            }
            leadingPlayer = playTrick(leadingPlayer);
            System.out.println("TO NEXT TRICK LEADS PLAYER " + leadingPlayer);

            tricksTaken[leadingPlayer]++;
            trickNum++;
        }

        //QUICK COUNTER! - deletable
        int maxTricks = 0;
        int playerNumber = -1;
        System.out.println("Tricks of players:");
        for (int i = 0; i < 4; i++) {
            System.out.println("Player " + i + " got " + tricksTaken[i]);
            if (tricksTaken[i] > maxTricks){
                maxTricks = tricksTaken[i];
                playerNumber = i;
            }
        }
        System.out.println("Player " + playerNumber + " won with " + maxTricks + " tricks");
        getNextPlayer();
    }

    private void getNextPlayer() {
        for (int i = 0; i < 4; i++) {
            if (players[i].hisTurn) {
                players[i].hisTurn = false;
                players[(i+1)%4].hisTurn = true;            // TODO should check the rules
                break;
            }
        }
    }

    private int playTrick(int leadingPlayer) {
        Trick trick = new Trick(game, this, leadingPlayer, cardHands, chosenMiniGameTrump.trumps);
        return trick.getTrickWinner(trick.getTrick());
    }
}
