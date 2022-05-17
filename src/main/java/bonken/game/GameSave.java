package bonken.game;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Class for saving and loading game from json file.
 */
public class GameSave {

    private static final Logger LOGGER = Logger.getLogger(GameSave.class.getName());

    private ArrayList<Integer> availableMinigames;
    private ArrayList<Boolean> playersChosenPos;
    private ArrayList<Integer> startingPlayer;
    private ArrayList<Integer> score;
    private ArrayList<String> usernames;

    public GameSave() {
        availableMinigames = new ArrayList<>(11);
        playersChosenPos = new ArrayList<>(4);
        startingPlayer = new ArrayList<>(2);
        score = new ArrayList<>(4);
        usernames = new ArrayList<>(4);
    }

    public ArrayList<Integer> getStartingPlayer() {
        return startingPlayer;
    }

    public ArrayList<Boolean> getPlayersChosenPos() {
        return playersChosenPos;
    }

    public ArrayList<Integer> getAvailableMinigames() {
        return availableMinigames;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    /**
     * Save current game into json file
     * @param game
     */
    public void saveGame(Game game) {
        availableMinigames = game.getMinigames();
        for (int i = 0; i < 4; i++) {
            if (game.getPlayers()[i].getChosenPositive()) {
                playersChosenPos.add(i, true);
            } else {
                playersChosenPos.add(i, false);;
            }
            if (game.getPlayers()[i].isHisTurn()) {
                startingPlayer.add(0, i);
            }
            usernames.add(i, game.getPlayers()[i].getUsername());
            score.add(i, game.getPlayers()[i].getScore());
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("gameSave.json"), this);
        } catch (IOException ex) {
            LOGGER.severe("IOException " + ex.getMessage());
        }
    }

    /**
     *
     * @return mapped class GameSave from json file
     */
    public GameSave readGame() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File("gameSave.json"), GameSave.class);
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "GameSave{" +
                "availableMinigames=" + availableMinigames +
                ", playersChosenPos=" + playersChosenPos +
                ", startingPlayer=" + startingPlayer +
                ", score=" + score +
                ", usernames=" + usernames +
                '}';
    }
}
