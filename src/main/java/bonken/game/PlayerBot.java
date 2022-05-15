package bonken.game;

import bonken.utils.Action;
import bonken.utils.DoubleAction;

import java.util.ArrayList;
import java.util.Random;

public class PlayerBot extends Player {

    private Random random;

    DoubleAction<Trick, Integer> giveServerTrickEnd;

    public PlayerBot(int id, Position pos, DoubleAction<Trick, Integer> giveServerTrickEnd) {
        super(id, pos, giveServerTrickEnd);
        this.username = "Bot" + id;
        chosenPositive = false;
        random = new Random();
    }

    public PlayerBot(int id, Position pos) {
        super(id, pos);
        this.username = "Bot" + id;
        chosenPositive = false;
        random = new Random();
    }

    @Override
    public void getCardToPlay() {
        Random random = new Random();
        int playedCard = random.nextInt(playableCards.size());
        Card tmp = playableCards.get(playedCard);
        System.out.println(tmp.toString());
        this.putCard(tmp);
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
        int num = possibleMinigameChoices.size();
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

        callback.call(chosenMiniGameNum);
    }

    private Integer selectedMinigame;
    @Override
    public void minigameSelected(Integer minigame) {
        selectedMinigame = minigame;
    }

    @Override
    public void cardSelected(String card) {

    }
}
