package Bonken.Game;

public class ScoreBoard {

    Player[] players;

    public ScoreBoard(Player[] players) {
        this.players = players;
    }

    public void updateScoreBoard(int score, int trickWinner) {
        players[trickWinner].score += score;
    }

}
