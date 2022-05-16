package bonken.game;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameSave {

    private ArrayList<Integer> availableMinigames;
    private boolean[] playersChosenPos;
    private int startingPlayer;
    private int[] score;

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public boolean[] getPlayersChosenPos() {
        return playersChosenPos;
    }

    public ArrayList<Integer> getAvailableMinigames() {
        return availableMinigames;
    }

    public int[] getScore() {
        return score;
    }

    public void saveGame(Game game) {
        availableMinigames = game.getMinigames();
        for (int i = 0; i < 4; i++) {
            if (game.getPlayers()[i].getChosenPositive()) {
                playersChosenPos[i] = true;
            } else {
                playersChosenPos[i] = false;
            }
            if (game.getPlayers()[i].isHisTurn()) {
                startingPlayer = i;
            }
            score[i] = game.getPlayers()[i].getScore();
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("gameSave.json"), this);
        } catch (IOException ex) {
            System.out.println("IOException " + ex.getMessage());
        }
    }

    public GameSave readGame() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File("gameSave.json"), GameSave.class);
        } catch (IOException ex) {
            System.out.println("IOException " + ex.getMessage());
        }
        return null;
    }

}
