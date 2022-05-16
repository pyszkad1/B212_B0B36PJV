package bonken.game;

import bonken.utils.Action;

import java.util.ArrayList;
import java.util.Scanner;


public class ConsolePlayer extends Player {

    private Scanner scanner;


    public ConsolePlayer( int id, Position pos) {
        super(id, pos);
        this.scanner = new Scanner(System.in);
        chooseUsername();
    }

    private void chooseUsername() {
        System.out.println("Enter your username: ");
        username = scanner.next();
    }

    @Override
    public void chooseMinigame(ArrayList<Integer> minigames, Action<Integer> callback) {
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

        Scanner scanner = new Scanner(System.in);

        Integer chosenMiniGameNum;
        if (possibleMinigameChoices.size() > 0) {
            if (chosenPositive) {
                System.out.println("You have already played your positive minigame, so you can only choose negative");
            }
            System.out.println("Choose from: " + possibleMinigameChoices);
            chosenMiniGameNum = Integer.valueOf(scanner.nextInt());
            while (!possibleMinigameChoices.contains(chosenMiniGameNum)) {
                System.out.println("Choose eligible minigame from " + minigames + " please.");
                chosenMiniGameNum = scanner.nextInt();
            }
        } else {
            chosenMiniGameNum = -1;
        }
        if (chosenMiniGameNum > 6) chosenPositive = true;
        callback.call(chosenMiniGameNum);
    }

    @Override
    public void minigameSelected(Integer minigame){}

    @Override
    public void cardSelected(String card) {

    }


    public void getCardToPlay() {
        System.out.println(playableCards);
        System.out.println("Choosing from " + (playableCards.size()) + " cards.");
        Scanner scanner = new Scanner(System.in);
        int playedCard = scanner.nextInt();
        while (playedCard > playableCards.size() - 1 || playedCard < 0) {
            System.out.println("Not a card, please choose again.");
            playedCard = scanner.nextInt();
        }


       this.putCard( playableCards.get(playedCard));
    }

}
