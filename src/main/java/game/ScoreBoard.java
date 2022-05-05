package game;

public class ScoreBoard {

    Player[] players;

    public ScoreBoard(Player[] players) {
        this.players = players;
    }

    public void updateScoreBoard(int score, int trickWinner) {
        players[trickWinner].score += score;
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < 4; i++) {
            ret += "Player " + i + "has " + (players[i].score) + "\n";
        }
        return ret;
    }
}
