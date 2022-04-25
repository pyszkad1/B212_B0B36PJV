package Bonken.Game;

public class ScoreBoard {

    Player[] players;

    public ScoreBoard(Player[] players) {
        this.players = players;
    }

    public void updateScoreBoard(int miniGameNum, int trickWinner) {
        if (miniGameNum > 6) {
            players[trickWinner].score += 10;
        }
    }

}
