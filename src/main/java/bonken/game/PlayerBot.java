package bonken.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PlayerBot implements PlayerInterface {
    final String username;
    final int id;
    private CardHand cardHand;
    ArrayList<Card> playableCards;
    private int score;
    private boolean hisTurn;
    private boolean chosenPositive;

    public PlayerBot(String username, int id) {
        this.username = username;
        this.id = id;
        chosenPositive = false;
    }

    @Override
    public Card play(Card card) {
        // TODO play card based on chosen minigame
        playableCards = cardHand.getPlayableCards(card);
        System.out.println(playableCards);
        System.out.println("choosing from " + (playableCards.size()) + " cards");
        Random random = new Random();
        int playedCard = random.nextInt(playableCards.size());
        Card tmp = playableCards.get(playedCard);
        this.cardHand.hand.remove(tmp);
        return tmp;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Integer chooseMinigame(ArrayList<Integer> minigames) {
        ArrayList<Integer> possibleMinigameChoices = new ArrayList<>();
        for (Integer minigame : minigames) {
            if (chosenPositive) {
                if (minigame < 7) {
                    possibleMinigameChoices.add(minigame);
                }
            } else {
                possibleMinigameChoices.add(minigame);
            }
        }
        int num = possibleMinigameChoices.size();
        Random random = new Random();
        Integer chosenMiniGameNum;
        if (possibleMinigameChoices.size() > 0) {
            System.out.println("Bot choosing from: " + possibleMinigameChoices);
            chosenMiniGameNum = possibleMinigameChoices.get((random.nextInt(num)));
            System.out.println("Bot chose minigame " + chosenMiniGameNum);
        } else {
            chosenMiniGameNum = -1;
            System.out.println("Bot can't choose, next player's turn");
        }
        if (chosenMiniGameNum > 6) chosenPositive = true;
        return chosenMiniGameNum;
    }


    @Override
    public boolean isHisTurn() {
        return hisTurn;
    }

    @Override
    public void setHisTurn(boolean hisTurn) {
        this.hisTurn = hisTurn;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void setCardHand(CardHand cardHand) {
        this.cardHand = cardHand;
    }

    @Override
    public CardHand getCardHand() {
        return cardHand;
    }
}
