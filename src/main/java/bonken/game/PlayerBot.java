package bonken.game;

import bonken.utils.Action;
import bonken.utils.DoubleAction;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Class for a bot player. Bots choose minigames and cards to play on random.
 */
public class PlayerBot extends Player {

    private Random random;
    private static final Logger LOGGER = Logger.getLogger(PlayerBot.class.getName());

    DoubleAction<Trick, Integer> giveServerTrickEnd;

    /**
     * Bot constructor for net game.
     * @param id
     * @param pos
     * @param giveServerTrickEnd
     */
    public PlayerBot(int id, Position pos, DoubleAction<Trick, Integer> giveServerTrickEnd) {
        super(id, pos, giveServerTrickEnd);
        this.username = "Bot" + id;
        chosenPositive = false;
        random = new Random();
    }

    /**
     * Bot constructor for offline game.
     * @param id
     * @param pos
     */
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
        LOGGER.info("Bot played " + tmp.toString());
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
            chosenMiniGameNum = possibleMinigameChoices.get((random.nextInt(num)));
        } else {
            chosenMiniGameNum = -1;
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
